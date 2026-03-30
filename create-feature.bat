@echo off
:: =========================================
:: Compose Clean Architecture Feature Generator
:: =========================================

:: Enable ANSI escape codes (Windows 10+)
:: Green = \x1b[32m, Reset = \x1b[0m
:: Check feature name
if "%1"=="" (
    set /p FEATURE=Enter feature name:
) else (
    set FEATURE=%1
)

:: Base path
set BASE=%CD%\app\src\main\java\com\rxth\multimodule\feature\%FEATURE%

:: Header
echo.
echo ------------------------------------------
echo ==========================================
echo Generating Feature %FEATURE% ...
echo ==========================================
echo.

:: List of folders to create
set FOLDERS=data\remote data\dto data\repository data\mapper ^
domain\model domain\repository domain\usecase ^
di\ ^
presentation\screen presentation\state presentation\viewmodel

:: Loop through folders with color
for %%F in (%FOLDERS%) do (
    mkdir "%BASE%\%%F" >nul 2>&1
    echo [CREATED] %%F
)

:: Footer
echo.
echo ==========================================
echo Feature "%FEATURE%" Created!
echo ==========================================
echo.

:: Pause to see result
pause