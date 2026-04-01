#!/bin/sh
#
# Gradle start up script for UN*X
#
APP_HOME="$(cd "$(dirname "$0")" && pwd)"
CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
JAVA_EXE="${JAVA_HOME}/bin/java"
[ -z "$JAVA_HOME" ] && JAVA_EXE="java"
exec "$JAVA_EXE" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
