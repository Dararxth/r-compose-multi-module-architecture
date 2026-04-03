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


set FEATURE_CAP=%FEATURE%
set FIRST_LETTER=%FEATURE_CAP:~0,1%
set REST=%FEATURE_CAP:~1%

:: force uppercase for first letter (manual mapping)
if "%FIRST_LETTER%"=="a" set FIRST_LETTER=A
if "%FIRST_LETTER%"=="b" set FIRST_LETTER=B
if "%FIRST_LETTER%"=="c" set FIRST_LETTER=C
if "%FIRST_LETTER%"=="d" set FIRST_LETTER=D
if "%FIRST_LETTER%"=="e" set FIRST_LETTER=E
if "%FIRST_LETTER%"=="f" set FIRST_LETTER=F
if "%FIRST_LETTER%"=="g" set FIRST_LETTER=G
if "%FIRST_LETTER%"=="h" set FIRST_LETTER=H
if "%FIRST_LETTER%"=="i" set FIRST_LETTER=I
if "%FIRST_LETTER%"=="j" set FIRST_LETTER=J
if "%FIRST_LETTER%"=="k" set FIRST_LETTER=K
if "%FIRST_LETTER%"=="l" set FIRST_LETTER=L
if "%FIRST_LETTER%"=="m" set FIRST_LETTER=M
if "%FIRST_LETTER%"=="n" set FIRST_LETTER=N
if "%FIRST_LETTER%"=="o" set FIRST_LETTER=O
if "%FIRST_LETTER%"=="p" set FIRST_LETTER=P
if "%FIRST_LETTER%"=="q" set FIRST_LETTER=Q
if "%FIRST_LETTER%"=="r" set FIRST_LETTER=R
if "%FIRST_LETTER%"=="s" set FIRST_LETTER=S
if "%FIRST_LETTER%"=="t" set FIRST_LETTER=T
if "%FIRST_LETTER%"=="u" set FIRST_LETTER=U
if "%FIRST_LETTER%"=="v" set FIRST_LETTER=V
if "%FIRST_LETTER%"=="w" set FIRST_LETTER=W
if "%FIRST_LETTER%"=="x" set FIRST_LETTER=X
if "%FIRST_LETTER%"=="y" set FIRST_LETTER=Y
if "%FIRST_LETTER%"=="z" set FIRST_LETTER=Z

set FEATURE_CAP=%FIRST_LETTER%%REST%


:: API Interface
echo package com.rxth.multimodule.feature.%FEATURE%.data.remote;> "%BASE%\data\remote\%FEATURE_CAP%Api.kt"
echo.>> "%BASE%\data\remote\%FEATURE_CAP%Api.kt"
echo interface %FEATURE_CAP%Api {>> "%BASE%\data\remote\%FEATURE_CAP%Api.kt"
echo     // TODO: define endpoints>> "%BASE%\data\remote\%FEATURE_CAP%Api.kt"
echo }>> "%BASE%\data\remote\%FEATURE_CAP%Api.kt"

:: Repository Interface
echo package com.rxth.multimodule.feature.%FEATURE%.domain.repository;> "%BASE%\domain\repository\%FEATURE_CAP%Repository.kt"
echo.>> "%BASE%\domain\repository\%FEATURE_CAP%Repository.kt"
echo interface %FEATURE_CAP%Repository {>> "%BASE%\domain\repository\%FEATURE_CAP%Repository.kt"
echo     suspend fun getData()>> "%BASE%\domain\repository\%FEATURE_CAP%Repository.kt"
echo }>> "%BASE%\domain\repository\%FEATURE_CAP%Repository.kt"

:: Repository Impl
echo package com.rxth.multimodule.feature.%FEATURE%.data.repository;> "%BASE%\data\repository\%FEATURE_CAP%RepositoryImpl.kt"
echo.>> "%BASE%\data\repository\%FEATURE_CAP%RepositoryImpl.kt"
echo class %FEATURE_CAP%RepositoryImpl : %FEATURE_CAP%Repository {>> "%BASE%\data\repository\%FEATURE_CAP%RepositoryImpl.kt"
echo     override suspend fun getData() {>> "%BASE%\data\repository\%FEATURE_CAP%RepositoryImpl.kt"
echo         // TODO>> "%BASE%\data\repository\%FEATURE_CAP%RepositoryImpl.kt"
echo     }>> "%BASE%\data\repository\%FEATURE_CAP%RepositoryImpl.kt"
echo }>> "%BASE%\data\repository\%FEATURE_CAP%RepositoryImpl.kt"

:: UseCase
echo package com.rxth.multimodule.feature.%FEATURE%.domain.usecase;> "%BASE%\domain\usecase\Get%FEATURE_CAP%UseCase.kt"
echo.>> "%BASE%\domain\usecase\Get%FEATURE_CAP%UseCase.kt"
echo class Get%FEATURE_CAP%UseCase(private val repo: %FEATURE_CAP%Repository) {>> "%BASE%\domain\usecase\Get%FEATURE_CAP%UseCase.kt"
echo     suspend operator fun invoke() = repo.getData()>> "%BASE%\domain\usecase\Get%FEATURE_CAP%UseCase.kt"
echo }>> "%BASE%\domain\usecase\Get%FEATURE_CAP%UseCase.kt"

:: ViewModel
echo package com.rxth.multimodule.feature.%FEATURE%.presentation.viewmodel;> "%BASE%\presentation\viewmodel\%FEATURE_CAP%ViewModel.kt"
echo.>> "%BASE%\presentation\viewmodel\%FEATURE_CAP%ViewModel.kt"
echo class %FEATURE_CAP%ViewModel {>> "%BASE%\presentation\viewmodel\%FEATURE_CAP%ViewModel.kt"
echo     // TODO>> "%BASE%\presentation\viewmodel\%FEATURE_CAP%ViewModel.kt"
echo }>> "%BASE%\presentation\viewmodel\%FEATURE_CAP%ViewModel.kt"

:: State
echo package com.rxth.multimodule.feature.%FEATURE%.presentation.state;> "%BASE%\presentation\state\%FEATURE_CAP%State.kt"
echo.>> "%BASE%\presentation\state\%FEATURE_CAP%State.kt"
echo data class %FEATURE_CAP%State(>> "%BASE%\presentation\state\%FEATURE_CAP%State.kt"
echo     val isLoading: Boolean = false>> "%BASE%\presentation\state\%FEATURE_CAP%State.kt"
echo )>> "%BASE%\presentation\state\%FEATURE_CAP%State.kt"

:: DI Module (Minimal Koin)
echo package com.rxth.multimodule.feature.%FEATURE%.di;> "%BASE%\di\%FEATURE_CAP%Module.kt"
echo.>> "%BASE%\di\%FEATURE_CAP%Module.kt"
echo import org.koin.dsl.module>> "%BASE%\di\%FEATURE_CAP%Module.kt"
echo.>> "%BASE%\di\%FEATURE_CAP%Module.kt"
echo val %FEATURE%Module = module {>> "%BASE%\di\%FEATURE_CAP%Module.kt"
echo.>> "%BASE%\di\%FEATURE_CAP%Module.kt"
echo }>> "%BASE%\di\%FEATURE_CAP%Module.kt"

:: Footer
echo.
echo ==========================================
echo Feature "%FEATURE%" Created!
echo ==========================================
echo.

:: Pause to see result
pause