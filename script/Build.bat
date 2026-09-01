@echo off
setlocal EnableDelayedExpansion

set "SDK=%LOCALAPPDATA%\Android\Sdk"
set "ADB=%SDK%\platform-tools\adb.exe"
set "EMULATOR=%SDK%\emulator\emulator.exe"
set "GRADLE=E:\application\java\gradle-9.4.1\bin\gradle.bat"
set "PKG=com.example.testan"
set "ACTIVITY=%PKG%/.MainActivity"
set "TMP=%TEMP%\avd_list_%RANDOM%.txt"

echo [1/4] Checking connected devices...

"%ADB%" devices > "%TMP%" 2>&1
set "READY="
for /f "tokens=1,2" %%A in ('findstr /R /V "^List of" "%TMP%"') do (
    if "%%B"=="device" set "READY=1"
)

if not defined READY (
    echo     No device online.

    "%EMULATOR%" -list-avds > "%TMP%" 2>&1
    set "AVD="
    for /f "delims=" %%A in ('type "%TMP%"') do (
        if not defined AVD set "AVD=%%A"
    )
    if not defined AVD (
        echo     No AVD found. Create one in Android Studio first.
        del "%TMP%" >nul 2>&1
        exit /b 1
    )
    echo     Starting emulator: %AVD%
    start "" "%EMULATOR%" -avd %AVD% -no-snapshot-load

    echo     Waiting for emulator to come online ...
    for /L %%i in (1,1,120) do (
        timeout /t 3 /nobreak >nul
        "%ADB%" devices > "%TMP%" 2>&1
        set "READY="
        for /f "tokens=1,2" %%A in ('findstr /R /V "^List of" "%TMP%"') do (
            if "%%B"=="device" set "READY=1"
        )
        if defined READY goto :emu_ready
    )
    echo     Emulator failed to come online within timeout.
    del "%TMP%" >nul 2>&1
    exit /b 1
    :emu_ready
    echo     Emulator online.
)
del "%TMP%" >nul 2>&1

echo [2/4] Building and installing debug APK...
call "%GRADLE%" :app:installDebug
if errorlevel 1 (
    echo Build failed.
    exit /b 1
)

echo [3/4] Stopping previous instance...
"%ADB%" shell am force-stop %PKG%

echo [4/4] Launching %ACTIVITY% ...
"%ADB%" shell am start -n %ACTIVITY%

endlocal