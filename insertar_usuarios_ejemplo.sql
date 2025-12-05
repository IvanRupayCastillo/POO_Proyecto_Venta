-- ============================================================================
-- Script para insertar usuarios de ejemplo
-- Base de datos: sistema_ventas_almacen
-- ============================================================================

-- IMPORTANTE: Ejecutar este script solo UNA VEZ
-- Si ya ejecutaste este script, usa el script: limpiar_usuarios_duplicados.sql

-- Nota: Las contraseñas están hasheadas con SHA-256
-- Contraseña para todos los usuarios: "123456"
-- Hash SHA-256: 8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92

-- Limpiar usuarios de ejemplo si existen (para evitar duplicados)
DELETE FROM usuarios WHERE codigo_usuario IN ('ADMIN001', 'GER001', 'VEND001', 'ALM001', 'SUP001');

-- Usuario 1: Administrador
INSERT INTO usuarios 
(codigo_usuario, nombre_completo, email, password_hash, rol, id_tienda_asignada, activo)
VALUES
('ADMIN001', 'Administrador del Sistema', 'admin@sistema.com', 
 '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 
 'ADMIN', 1, 1);

-- Usuario 2: Gerente
INSERT INTO usuarios 
(codigo_usuario, nombre_completo, email, password_hash, rol, id_tienda_asignada, activo)
VALUES
('GER001', 'María García López', 'gerente@sistema.com', 
 '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 
 'GERENTE', 1, 1);

-- Usuario 3: Vendedor
INSERT INTO usuarios 
(codigo_usuario, nombre_completo, email, password_hash, rol, id_tienda_asignada, activo)
VALUES
('VEND001', 'Juan Pérez Martínez', 'vendedor@sistema.com', 
 '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 
 'VENDEDOR', 1, 1);

-- Usuario 4: Almacenero
INSERT INTO usuarios 
(codigo_usuario, nombre_completo, email, password_hash, rol, id_tienda_asignada, activo)
VALUES
('ALM001', 'Carlos Rodríguez Sánchez', 'almacenero@sistema.com', 
 '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 
 'ALMACENERO', 1, 1);

-- Usuario 5: Supervisor
INSERT INTO usuarios 
(codigo_usuario, nombre_completo, email, password_hash, rol, id_tienda_asignada, activo)
VALUES
('SUP001', 'Ana Martínez Torres', 'supervisor@sistema.com', 
 '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 
 'SUPERVISOR', 1, 1);

-- Verificar los usuarios insertados
SELECT 
    id_usuario,
    codigo_usuario,
    nombre_completo,
    email,
    rol,
    activo
FROM usuarios
ORDER BY id_usuario;

-- Información de acceso:
-- =====================
-- Email: admin@sistema.com
-- Contraseña: 123456
-- Rol: ADMIN
--
-- Email: gerente@sistema.com
-- Contraseña: 123456
-- Rol: GERENTE
--
-- Email: vendedor@sistema.com
-- Contraseña: 123456
-- Rol: VENDEDOR
--
-- Email: almacenero@sistema.com
-- Contraseña: 123456
-- Rol: ALMACENERO
--
-- Email: supervisor@sistema.com
-- Contraseña: 123456
-- Rol: SUPERVISOR
