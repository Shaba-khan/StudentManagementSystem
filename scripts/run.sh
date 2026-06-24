#!/usr/bin/env bash
set -euo pipefail
CATALINA_HOME="${CATALINA_HOME:-$HOME/tomcat}"

echo "--- Starting Tomcat ---"
"$CATALINA_HOME/bin/catalina.sh" run
