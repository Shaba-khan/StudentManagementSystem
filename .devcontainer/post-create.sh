#!/usr/bin/env bash
set -euo pipefail

TOMCAT_VERSION="10.1.56"
TOMCAT_HOME="$HOME/tomcat"

echo "=================================================="
echo " Student Management System - Codespace bootstrap"
echo "=================================================="

echo "--- Java ---"
java -version
echo "--- Maven ---"
mvn -version

if [ ! -d "$TOMCAT_HOME" ]; then
  echo "--- Downloading Tomcat ${TOMCAT_VERSION} ---"
  cd /tmp
  curl -fSL -o tomcat.tar.gz \
    "https://archive.apache.org/dist/tomcat/tomcat-10/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz"
  mkdir -p "$TOMCAT_HOME"
  tar -xzf tomcat.tar.gz -C "$TOMCAT_HOME" --strip-components=1
  rm -f tomcat.tar.gz
  chmod +x "$TOMCAT_HOME"/bin/*.sh
  echo "Tomcat installed at $TOMCAT_HOME"
else
  echo "Tomcat already present at $TOMCAT_HOME"
fi

if ! grep -q "CATALINA_HOME" "$HOME/.bashrc"; then
  {
    echo ""
    echo "export CATALINA_HOME=$TOMCAT_HOME"
    echo 'export PATH=$PATH:$CATALINA_HOME/bin'
  } >> "$HOME/.bashrc"
fi

echo "--- Waiting for MySQL (host: db) ---"
for i in $(seq 1 30); do
  if mysqladmin ping -h db -uroot -proot1234 --silent 2>/dev/null; then
    echo "MySQL is up."
    break
  fi
  echo "  ...waiting ($i/30)"
  sleep 2
done

echo "--- Verifying database ---"
mysql -h db -uroot -proot1234 -e \
  "SELECT COUNT(*) AS students FROM student_management_system.students;" \
  2>/dev/null || echo "NOTE: tables not loaded yet — they load once sql/ contains the Phase 4 script."

echo "=================================================="
echo " Bootstrap complete."
echo "   Build:   mvn clean package"
echo "   Deploy:  bash scripts/deploy.sh"
echo "   Run:     bash scripts/run.sh   (then open port 8080)"
echo "=================================================="
