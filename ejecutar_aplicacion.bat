@echo off
title Sistema de Ventas
cd /d "%~dp0"

echo ========================================
echo   SISTEMA DE VENTAS - INICIANDO
echo ========================================
echo.

REM Verificar si existe build/classes
if not exist "build\classes" (
    echo ERROR: El proyecto no esta compilado.
    echo Por favor ejecuta primero: limpiar_y_compilar.bat
    echo.
    pause
    exit /b 1
)

REM Verificar si existe el driver MySQL
if not exist "build\classes\mysql-connector-j-9.3.0.jar" (
    echo Copiando driver MySQL...
    copy "mysql-connector-j-9.3.0.jar" "build\classes\" >nul
)

REM Ejecutar la aplicación
echo Iniciando aplicacion...
echo.
cd build\classes
java -cp ".;mysql-connector-j-9.3.0.jar" proyecto_sistema_venta.Main

REM Si hay error al ejecutar
if errorlevel 1 (
    echo.
    echo ERROR: No se pudo iniciar la aplicacion.
    echo Verifica que Java este instalado correctamente.
    cd ..\..
    pause
    exit /b 1
)

cd ..\..
