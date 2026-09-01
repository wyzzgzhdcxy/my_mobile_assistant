@echo off
setlocal

cd /d "%~dp0.."

set "ADDR_FILE=%~dp0device.addr"
if not exist "%ADDR_FILE%" (
    echo %ADDR_FILE% not found. Run connect.bat first.
    exit /b 1
)
set /p DEVICE=<"%ADDR_FILE%"
if "%DEVICE%"=="" (
    echo %ADDR_FILE% is empty. Run connect.bat first.
    exit /b 1
)

set "GRADLE=E:\application\java\gradle-9.4.1\bin\gradle.bat"

echo [1/2] Building release APK...
call "%GRADLE%" :app:assembleRelease
if errorlevel 1 (
    echo Build failed.
    exit /b 1
)

echo.
echo [2/2] Installing APK to %DEVICE% ...
for %%F in ("app\build\outputs\apk\release\*.apk") do (
    adb -s "%DEVICE%" install -r "%%F"
    if errorlevel 1 (
        echo Install failed.
        exit /b 1
    )
)

endlocal