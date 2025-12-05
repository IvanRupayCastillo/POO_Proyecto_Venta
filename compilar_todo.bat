@echo off
echo ========================================
echo Compilando TODO el proyecto desde cero
echo ========================================

echo.
echo Limpiando directorio de clases...
if exist "build\classes" rmdir /s /q "build\classes"
mkdir "build\classes"

echo.
echo Compilando todas las clases...
javac -encoding UTF-8 -cp "mysql-connector-j-9.3.0.jar" -d build\classes -sourcepath src src\proyecto_sistema_venta\*.java src\proyecto_sistema_venta\Conexion\*.java src\proyecto_sistema_venta\Datos\*.java src\proyecto_sistema_venta\Entidades\*.java src\proyecto_sistema_venta\Negocio\*.java src\proyecto_sistema_venta\Presentacion\*.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR en la compilacion!
    pause
    exit /b 1
)

echo.
echo Compilacion exitosa!
echo.
echo Creando nuevo JAR...
cd build\classes
jar cvfm ..\..\dist\proyecto_sistema_venta.jar ..\..\manifest.mf proyecto_sistema_venta\*.class proyecto_sistema_venta\Conexion\*.class proyecto_sistema_venta\Datos\*.class proyecto_sistema_venta\Entidades\*.class proyecto_sistema_venta\Negocio\*.class proyecto_sistema_venta\Presentacion\*.class
cd ..\..

echo.
echo ========================================
echo Proyecto compilado completamente!
echo ========================================
echo.
echo Ahora puedes ejecutar: ejecutar.bat
pause
