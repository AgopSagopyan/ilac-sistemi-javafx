@echo off
title Eczane Stok Takip Sistemi
color 0B

echo ========================================================
echo         Eczane / Ilac Stok Takip Sistemi Baslatiliyor...
echo ========================================================
echo.

set JAVA_HOME=C:\Program Files\Java\jdk-26.0.1
set PATH=%JAVA_HOME%\bin;%PATH%

echo [1/2] Proje Derleniyor...
call .\mvnw.cmd clean compile >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    color 0C
    echo Hata: Proje derlenirken bir sorun olustu. Lutfen kodlarinizi kontrol edin.
    pause
    exit /b %ERRORLEVEL%
)
echo [1/2] Basarili!
echo.

echo [2/2] Uygulama Baslatiliyor...
call .\mvnw.cmd javafx:run
if %ERRORLEVEL% NEQ 0 (
    color 0C
    echo Hata: Uygulama baslatilamadi.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo Gorusmek uzere!
pause
