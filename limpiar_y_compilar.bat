@echo off
echo ========================================
echo LIMPIEZA COMPLETA Y RECOMPILACION
echo ========================================

REM Eliminar completamente las carpetas build y dist
echo Eliminando carpetas build y dist...
if exist build rmdir /s /q build
if exist dist rmdir /s /q dist

REM Crear nuevas carpetas
echo Creando carpetas nuevas...
mkdir build\classes
mkdir dist

echo.
echo ========================================
echo COMPILANDO TODO EL PROYECTO
echo ========================================

REM Compilar todas las clases en orden de dependencias
echo [1/9] Compilando Entidades...
javac -d build/classes -cp "mysql-connector-j-9.3.0.jar" src/proyecto_sistema_venta/Entidades/*.java

echo [2/9] Compilando Conexion...
javac -d build/classes -cp "mysql-connector-j-9.3.0.jar;build/classes" src/proyecto_sistema_venta/Conexion/*.java

echo [3/9] Compilando Datos/Interfaces (PRIMERO)...
javac -d build/classes -cp "mysql-connector-j-9.3.0.jar;build/classes" src/proyecto_sistema_venta/Datos/Interfaces/*.java

echo [4/9] Compilando Datos...
javac -d build/classes -cp "mysql-connector-j-9.3.0.jar;build/classes" src/proyecto_sistema_venta/Datos/*.java

echo [5/9] Compilando Negocio...
javac -d build/classes -cp "mysql-connector-j-9.3.0.jar;build/classes" src/proyecto_sistema_venta/Negocio/*.java

echo [6/9] Compilando Presentacion...
javac -d build/classes -cp "mysql-connector-j-9.3.0.jar;build/classes" src/proyecto_sistema_venta/Presentacion/*.java

echo [7/9] Compilando Main y ExecuteSP...
javac -d build/classes -cp "mysql-connector-j-9.3.0.jar;build/classes" src/proyecto_sistema_venta/*.java

echo [8/9] Compilando Pruebas...
javac -d build/classes -cp "mysql-connector-j-9.3.0.jar;build/classes" src/proyecto_sistema_venta/Pruebas/*.java 2>nul

echo [9/9] Copiando archivos .form...
xcopy /Y /S src\proyecto_sistema_venta\Presentacion\*.form build\classes\proyecto_sistema_venta\Presentacion\ >nul 2>&1

echo.
echo ========================================
echo CREANDO JAR
echo ========================================

REM Crear el JAR con todas las clases
cd build\classes
jar cfm ..\..\dist\proyecto_sistema_venta.jar ..\..\manifest.mf proyecto_sistema_venta\Entidades\*.class proyecto_sistema_venta\Conexion\*.class proyecto_sistema_venta\Datos\Interfaces\*.class proyecto_sistema_venta\Datos\*.class proyecto_sistema_venta\Negocio\*.class proyecto_sistema_venta\Presentacion\*.class proyecto_sistema_venta\*.class proyecto_sistema_venta\Pruebas\*.class 2>nul
cd ..\..

echo.
echo ========================================
echo COMPILACION COMPLETADA
echo ========================================
echo JAR creado en: dist\proyecto_sistema_venta.jar
echo.
echo IMPORTANTE: 
echo - Cierra NetBeans completamente
echo - Vuelve a abrir el proyecto
echo - Ejecuta desde NetBeans con F6 (no uses ejecutar.bat)
echo.
pause
