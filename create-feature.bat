@echo off
:: =========================================
:: Compose Clean Architecture Feature Generator
:: =========================================

:: Set colors
:: 0 = Black, 1 = Blue, 2 = Green, 3 = Aqua, 4 = Red, 5 = Purple, 6 = Yellow, 7 = White, 8 = Gray, 9 = Light Blue
:: Example: color 0A = black background + green text
color 0A

:: Check feature name
if "%1"=="" (
    set /p FEATURE=Enter feature name:
) else (
    set FEATURE=%1
)

:: Base path
set BASE=%CD%\app\src\main\java\com\rxth\multimodule\feature\%FEATURE%

:: Header
echo =========================================
echo      Feature: %FEATURE%
echo      Path: %BASE%
echo =========================================
echo.

:: List of folders to create
set FOLDERS=data\remote data\dto data\repository data\mapper ^
domain\model domain\repository domain\usecase ^
presentation\screen presentation\state presentation\viewmodel

:: Loop through folders with color
for %%F in (%FOLDERS%) do (
    mkdir "%BASE%\%%F" >nul 2>&1
    :: green text for created
    echo [CREATED] %%F
)

:: Footer
echo.
echo =========================================
echo      Feature "%FEATURE%" Successfully Created!
echo =========================================
echo.

:: Pause to see result
pause