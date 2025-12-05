@echo off
REM Script para ejecutar la aplicación con el driver MySQL incluido
cd /d "%~dp0"

REM Crear la carpeta dist si no existe
if not exist "dist" mkdir dist

REM Copiar el driver a la carpeta dist si no está
if not exist "dist\mysql-connector-j-9.3.0.jar" (
    copy "mysql-connector-j-9.3.0.jar" "dist\"
    echo Driver MySQL copiado a dist/
)

REM Ejecutar el JAR con el classpath correcto
java -cp "dist\proyecto_sistema_venta.jar;dist\mysql-connector-j-9.3.0.jar" proyecto_sistema_venta.Main

pause
