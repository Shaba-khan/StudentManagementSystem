#!/usr/bin/env bash
set -euo pipefail
CATALINA_HOME="${CATALINA_HOME:-$HOME/tomcat}"

echo "--- Building WAR ---"
mvn clean package

WAR=$(ls target/*.war | head -n1)
echo "Built: $WAR"

echo "--- Deploying as /StudentManagementSystem ---"
rm -rf "$CATALINA_HOME/webapps/StudentManagementSystem" \
       "$CATALINA_HOME/webapps/StudentManagementSystem.war"
cp "$WAR" "$CATALINA_HOME/webapps/StudentManagementSystem.war"
echo "Deployed. Now run: bash scripts/run.sh"
