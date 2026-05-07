@echo off
title Pharmacy Stock Tracking System
color 0A

echo ==============================================
echo Pharmacy Stock Tracking System
echo ==============================================

:: Set Java 26 Environment Temporarily
set JAVA_HOME=C:\Program Files\Java\jdk-26.0.1
set PATH=%JAVA_HOME%\bin;%PATH%

echo [INFO] Using Java from: %JAVA_HOME%
echo [INFO] Building and starting the application...
echo.

:: Run the Maven Wrapper to build and run the JavaFX App
call .\mvnw.cmd clean javafx:run

echo.
echo [INFO] Application closed or encountered an error.
pause
