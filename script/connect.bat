@echo off
setlocal

set "ADDR=%~1"
if "%ADDR%"=="" set "ADDR=192.168.31.134:45393"

echo Connecting to %ADDR% ...
adb connect "%ADDR%"
if errorlevel 1 (
    echo Connect failed.
    exit /b 1
)

adb devices | findstr /C:"%ADDR%" >nul
if errorlevel 1 (
    echo Device %ADDR% not found.
    exit /b 1
)

> "%~dp0device.addr" echo %ADDR%
echo Saved address to %~dp0device.addr
endlocal