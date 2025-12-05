# 🏪 Sistema de Ventas y Almacén

Sistema completo de gestión de ventas con autenticación de usuarios, control de inventario y generación de reportes.

---

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Requisitos](#-requisitos)
- [Instalación](#-instalación)
- [Usuarios por Defecto](#-usuarios-por-defecto)
- [Uso del Sistema](#-uso-del-sistema)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Solución de Problemas](#-solución-de-problemas)

---

## ✨ Características

### 🔐 Sistema de Autenticación
- ✅ Login con email y contraseña
- ✅ Registro de nuevos usuarios
- ✅ Recuperación de contraseña
- ✅ Gestión de sesión
- ✅ Control de acceso por roles

### 👥 Roles de Usuario
- **ADMIN**: Acceso completo al sistema
- **GERENTE**: Gestión de ventas y reportes
- **VENDEDOR**: Registro de ventas
- **ALMACENERO**: Control de inventario
- **SUPERVISOR**: Supervisión de operaciones

### 💼 Funcionalidades de Negocio
- ✅ Gestión de productos
- ✅ Control de inventario
- ✅ Registro de ventas
- ✅ Gestión de clientes
- ✅ Múltiples tiendas
- ✅ Generación de comprobantes (Boleta, Factura, Ticket)
- ✅ Reportes en JSON y XML

---

## 💻 Requisitos

### Software Necesario
- **Java JDK 8 o superior**
- **MySQL 8.0 o superior**
- **MySQL Connector/J 9.3.0** (incluido en el proyecto)

### Sistema Operativo
- Windows 10/11
- Linux
- macOS

---

## 🚀 Instalación

### Paso 1: Configurar la Base de Datos

1. **Crear la base de datos**
   ```sql
   CREATE DATABASE sistema_ventas_almacen;
   ```

2. **Ejecutar el script de estructura**
   ```bash
   # Ejecuta el archivo: Script db.txt
   # Este script crea todas las tablas necesarias
   ```

3. **Insertar usuarios de ejemplo**
   ```bash
   # Ejecuta el archivo: insertar_usuarios_ejemplo.sql
   # Esto crea 5 usuarios de prueba
   ```

### Paso 2: Configurar la Conexión

Edita el archivo `src/proyecto_sistema_venta/Conexion/Conexion.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/sistema_ventas_almacen";
private static final String USER = "tu_usuario";
private static final String PASSWORD = "tu_contraseña";
```

### Paso 3: Compilar el Proyecto

```bash
# Opción 1: Compilar todo el sistema de autenticación
compilar_autenticacion.bat

# Opción 2: Compilar componentes individuales
compilar_venta.bat
compilar_negocio.bat
```

### Paso 4: Ejecutar la Aplicación

```bash
ejecutar.bat
```

---

## 👤 Usuarios por Defecto

Después de ejecutar el script `insertar_usuarios_ejemplo.sql`, tendrás estos usuarios disponibles:

### 🔑 Credenciales de Acceso

| # | Código | Nombre | Email | Contraseña | Rol |
|---|--------|--------|-------|------------|-----|
| 1 | ADMIN001 | Administrador del Sistema | `admin@sistema.com` | `123456` | ADMIN |
| 2 | GER001 | María García López | `gerente@sistema.com` | `123456` | GERENTE |
| 3 | VEND001 | Juan Pérez Martínez | `vendedor@sistema.com` | `123456` | VENDEDOR |
| 4 | ALM001 | Carlos Rodríguez Sánchez | `almacenero@sistema.com` | `123456` | ALMACENERO |
| 5 | SUP001 | Ana Martínez Torres | `supervisor@sistema.com` | `123456` | SUPERVISOR |

### 📝 Notas Importantes

- ⚠️ **Contraseña por defecto**: Todos los usuarios tienen la contraseña `123456`
- 🔒 **Seguridad**: Las contraseñas están hasheadas con SHA-256
- 🏢 **Tienda asignada**: Todos los usuarios están asignados a la Tienda 1
- ✅ **Estado**: Todos los usuarios están activos

### 🔐 Hash de Contraseña

```
Contraseña: 123456
Hash SHA-256: 8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92
```

---

## 📖 Uso del Sistema

### 1. Iniciar Sesión

1. Ejecuta `ejecutar.bat`
2. Se abrirá la pantalla de login
3. Ingresa tu email y contraseña
4. Haz clic en "INICIAR SESIÓN"

**Ejemplo:**
```
Email:      admin@sistema.com
Contraseña: 123456
```

### 2. Crear una Cuenta Nueva

1. En la pantalla de login, haz clic en "Crear cuenta nueva"
2. Completa el formulario:
   - Código de Usuario (único)
   - Nombre Completo
   - Email (único)
   - Contraseña (mínimo 6 caracteres)
   - Confirmar Contraseña
   - Rol
3. Haz clic en "REGISTRAR"

### 3. Recuperar Contraseña

1. En la pantalla de login, haz clic en "¿Olvidaste tu contraseña?"
2. Ingresa tu email
3. Se generará una contraseña temporal
4. Usa la contraseña temporal para iniciar sesión

### 4. Funcionalidades del Sistema

Una vez dentro del sistema, puedes acceder a:

#### Menú Procesos
- Tipo Producto
- Color
- Talla
- Productos
- Tiendas
- Clientes
- Kardex

#### Menú Tienda
- Listar Productos
- Registrar Venta

#### Menú Consultas
- (En desarrollo)

#### Menú Reportes
- (En desarrollo)

#### Menú Salir
- **Cerrar Sesión**: Cierra la sesión actual y vuelve a la pantalla de login

---

## 📁 Estructura del Proyecto

```
POO_Proyecto_Venta-main/
├── src/
│   └── proyecto_sistema_venta/
│       ├── Conexion/
│       │   ├── Conexion.java
│       │   └── DemoCnx.java
│       ├── Datos/
│       │   ├── ClienteDAO.java
│       │   ├── ColorDAO.java
│       │   ├── DetalleVentaDAO.java
│       │   ├── InventarioDAO.java
│       │   ├── ProductoDAO.java
│       │   ├── TallaDAO.java
│       │   ├── TipoProductoDAO.java
│       │   ├── UsuarioDAO.java          ⭐ NUEVO
│       │   └── VentaDAO.java
│       ├── Entidades/
│       │   ├── Cliente.java
│       │   ├── Color.java
│       │   ├── DetalleVenta.java
│       │   ├── Inventario.java
│       │   ├── Producto.java
│       │   ├── Talla.java
│       │   ├── TipoProducto.java
│       │   ├── Usuario.java             ⭐ NUEVO
│       │   └── Venta.java
│       ├── Negocio/
│       │   ├── AutenticacionNegocio.java ⭐ NUEVO
│       │   ├── ClienteNegocio.java
│       │   └── VentaNegocio.java
│       ├── Presentacion/
│       │   ├── Frm_Cliente.java
│       │   ├── Frm_Venta.java
│       │   ├── FrmColor.java
│       │   ├── FrmKardex.java
│       │   ├── FrmListarProductos.java
│       │   ├── FrmLogin.java            ⭐ NUEVO
│       │   ├── FrmMenu.java
│       │   ├── FrmProductos.java
│       │   ├── FrmRegistro.java         ⭐ NUEVO
│       │   ├── FrmTalla.java
│       │   ├── FrmTienda.java
│       │   ├── FrmTipoProducto.java
│       │   └── SessionManager.java      ⭐ ACTUALIZADO
│       └── Main.java                    ⭐ ACTUALIZADO
├── dist/
│   ├── proyecto_sistema_venta.jar
│   └── mysql-connector-j-9.3.0.jar
├── data/
│   └── ventas/                          (Recibos generados)
├── Scripts SQL/
│   ├── Script db.txt
│   ├── insertar_usuarios_ejemplo.sql    ⭐ NUEVO
│   ├── insertar_ventas_ejemplo.sql      ⭐ NUEVO
│   ├── limpiar_usuarios_duplicados.sql  ⭐ NUEVO
│   └── sp_registrar_venta_completa.sql
├── Scripts de Compilación/
│   ├── compilar_autenticacion.bat       ⭐ NUEVO
│   ├── compilar_venta.bat               ⭐ NUEVO
│   ├── compilar_negocio.bat             ⭐ NUEVO
│   └── ejecutar.bat
├── Documentación/
│   ├── README.md                        ⭐ ESTE ARCHIVO
│   ├── SISTEMA_AUTENTICACION.md         ⭐ NUEVO
│   ├── INSTRUCCIONES_RAPIDAS.txt        ⭐ NUEVO
│   └── CAMBIOS_IMPLEMENTADOS.md         ⭐ NUEVO
└── mysql-connector-j-9.3.0.jar
```

---

## 🔧 Solución de Problemas

### ❌ Error: "Email o contraseña incorrectos"

**Causa:** Credenciales inválidas o usuario no existe

**Solución:**
1. Verifica que ejecutaste el script `insertar_usuarios_ejemplo.sql`
2. Usa las credenciales correctas: `admin@sistema.com` / `123456`
3. Verifica que el usuario esté activo en la base de datos

```sql
SELECT * FROM usuarios WHERE email = 'admin@sistema.com';
```

### ❌ Error: "Duplicate entry 'VEND001' for key 'usuarios.uk_codigo_usuario'"

**Causa:** Intentaste ejecutar el script de usuarios dos veces

**Solución:**
1. Ejecuta el script `limpiar_usuarios_duplicados.sql`
2. Luego ejecuta nuevamente `insertar_usuarios_ejemplo.sql`

```bash
# En MySQL Workbench o tu cliente SQL:
# 1. Ejecuta: limpiar_usuarios_duplicados.sql
# 2. Ejecuta: insertar_usuarios_ejemplo.sql
```

### ❌ Error: "Error de conexión a la base de datos"

**Causa:** MySQL no está corriendo o credenciales incorrectas

**Solución:**
1. Verifica que MySQL esté corriendo
2. Verifica las credenciales en `Conexion.java`
3. Verifica que la base de datos exista

```bash
# Verificar si MySQL está corriendo
mysql -u root -p

# Verificar que la base de datos existe
SHOW DATABASES LIKE 'sistema_ventas_almacen';
```

### ❌ Error: "El email ya está registrado"

**Causa:** Intentas registrar un usuario con un email que ya existe

**Solución:**
1. Usa otro email
2. O recupera la contraseña del email existente

### ❌ Error: "ClassNotFoundException: com.mysql.cj.jdbc.Driver"

**Causa:** El driver MySQL no está en el classpath

**Solución:**
1. Verifica que `mysql-connector-j-9.3.0.jar` esté en la carpeta `dist/`
2. Ejecuta `ejecutar.bat` que incluye el driver en el classpath

### ❌ La tabla de ventas está vacía

**Causa:** No hay ventas registradas en la base de datos

**Solución:**
1. Ejecuta el script `insertar_ventas_ejemplo.sql` para crear ventas de prueba
2. O registra una venta nueva desde el sistema

---

## 🔒 Seguridad

### Contraseñas
- ✅ Hasheadas con SHA-256
- ✅ No se almacenan en texto plano
- ✅ Validación de longitud mínima (6 caracteres)

### Validaciones
- ✅ Formato de email válido
- ✅ Unicidad de email y código de usuario
- ✅ Confirmación de contraseña
- ✅ Usuarios activos/inactivos

### Sesión
- ✅ Gestión de sesión con SessionManager
- ✅ Información del usuario encapsulada
- ✅ Control de acceso por rol

---

## 📊 Base de Datos

### Tablas Principales

- `usuarios` - Usuarios del sistema
- `tiendas` - Tiendas/sucursales
- `clientes` - Clientes
- `tipos_producto` - Categorías de productos
- `colores` - Colores disponibles
- `tallas` - Tallas disponibles
- `productos` - Catálogo de productos
- `inventario` - Stock por tienda
- `comprobantes_venta` - Ventas registradas
- `detalle_venta` - Detalle de cada venta
- `movimientos_inventario` - Historial de movimientos

---

## 🎯 Próximas Funcionalidades

- [ ] Envío de emails para recuperación de contraseña
- [ ] Cambio de contraseña desde el perfil
- [ ] Historial de accesos
- [ ] Bloqueo de cuenta tras intentos fallidos
- [ ] Reportes avanzados
- [ ] Dashboard con estadísticas
- [ ] Exportación a Excel
- [ ] Impresión de comprobantes
- [ ] Notificaciones en tiempo real

---

## 📞 Soporte

### Documentación Adicional

- `SISTEMA_AUTENTICACION.md` - Guía completa del sistema de autenticación
- `INSTRUCCIONES_RAPIDAS.txt` - Guía rápida visual
- `CAMBIOS_IMPLEMENTADOS.md` - Registro detallado de cambios

### Logs y Debugging

Los logs del sistema se muestran en la consola. Para ver más detalles:

```bash
# Ejecutar con logs visibles
java -cp "dist\proyecto_sistema_venta.jar;dist\mysql-connector-j-9.3.0.jar" proyecto_sistema_venta.Main
```

---

## 👨‍💻 Desarrollo

### Compilar desde Código Fuente

```bash
# Compilar todo el sistema de autenticación
compilar_autenticacion.bat

# Compilar componentes individuales
javac -encoding UTF-8 -cp "build\classes;mysql-connector-j-9.3.0.jar" -d build\classes src\proyecto_sistema_venta\Presentacion\FrmLogin.java
```

### Actualizar el JAR

```bash
cd build\classes
jar uf ..\..\dist\proyecto_sistema_venta.jar proyecto_sistema_venta\Presentacion\FrmLogin.class
cd ..\..
```

---

## 📄 Licencia

Este proyecto es de uso educativo.

---

## 🙏 Agradecimientos

Desarrollado como parte del curso de Programación Orientada a Objetos.

---

## 📝 Changelog

### Versión 2.0 (Diciembre 2025)
- ✅ Sistema completo de autenticación
- ✅ Login, registro y recuperación de contraseña
- ✅ Gestión de sesión mejorada
- ✅ Listado de ventas funcional
- ✅ Documentación completa

### Versión 1.0 (Inicial)
- ✅ Sistema básico de ventas
- ✅ Gestión de productos e inventario
- ✅ Registro de ventas
- ✅ Generación de comprobantes

---

**¡Sistema listo para usar!** 🚀

Para comenzar, ejecuta `insertar_usuarios_ejemplo.sql` y luego `ejecutar.bat`
