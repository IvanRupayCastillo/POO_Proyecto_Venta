@echo off
title Sistema de Ventas - Compilar y Ejecutar
cd /d "%~dp0"

echo ========================================
echo   SISTEMA DE VENTAS
echo ========================================
echo.

REM Verificar si ya existe build/classes con archivos compilados
if exist "build\classes\proyecto_sistema_venta\Main.class" (
    echo El proyecto ya esta compilado.
    echo.
    choice /C SE /M "Deseas [S]olo ejecutar o [E]liminar y recompilar"
    if errorlevel 2 goto COMPILAR
    if errorlevel 1 goto EJECUTAR
) else (
    goto COMPILAR
)

:COMPILAR
echo.
echo ========================================
echo   LIMPIEZA Y COMPILACION
echo ========================================
echo.

REM Eliminar completamente las carpetas build y dist
echo Eliminando carpetas build y dist...
if exist build rmdir /s /q build
if exist dist rmdir /s /q dist

REM Crear nuevas carpetas
echo Creando carpetas nuevas...
mkdir build\classes
mkdir dist

echo.
echo Compilando proyecto...
echo [1/7] Compilando Entidades...
javac -d build/classes -cp "mysql-connector-j-9.3.0.jar" src/proyecto_sistema_venta/Entidades/*.java

echo [2/7] Compilando Conexion...
javac -d build/classes -cp "mysql-connector-j-9.3.0.jar;build/classes" src/proyecto_sistema_venta/Conexion/*.java

echo [3/7] Compilando Datos/Interfaces...
javac -d build/classes -cp "mysql-connector-j-9.3.0.jar;build/classes" src/proyecto_sistema_venta/Datos/Interfaces/*.java

echo [4/7] Compilando Datos...
javac -d build/classes -cp "mysql-connector-j-9.3.0.jar;build/classes" src/proyecto_sistema_venta/Datos/*.java

echo [5/7] Compilando Negocio...
javac -d build/classes -cp "mysql-connector-j-9.3.0.jar;build/classes" src/proyecto_sistema_venta/Negocio/*.java

echo [6/7] Compilando Presentacion...
javac -d build/classes -cp "mysql-connector-j-9.3.0.jar;build/classes" src/proyecto_sistema_venta/Presentacion/*.java

echo [7/7] Compilando Main y ExecuteSP...
javac -d build/classes -cp "mysql-connector-j-9.3.0.jar;build/classes" src/proyecto_sistema_venta/*.java

echo.
echo Copiando archivos necesarios...
xcopy /Y /S src\proyecto_sistema_venta\Presentacion\*.form build\classes\proyecto_sistema_venta\Presentacion\ >nul 2>&1
xcopy /Y /S src\proyecto_sistema_venta\Presentacion\Imagenes build\classes\proyecto_sistema_venta\Presentacion\Imagenes\ >nul 2>&1
copy "mysql-connector-j-9.3.0.jar" "build\classes\" >nul

echo.
echo ========================================
echo   COMPILACION COMPLETADA
echo ========================================
echo.

:EJECUTAR
REM Verificar si existe el driver MySQL en build/classes
if not exist "build\classes\mysql-connector-j-9.3.0.jar" (
    echo Copiando driver MySQL...
    copy "mysql-connector-j-9.3.0.jar" "build\classes\" >nul
)

echo ========================================
echo   INICIANDO APLICACION
echo ========================================
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
