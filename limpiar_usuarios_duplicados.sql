-- ============================================================================
-- Script para limpiar usuarios duplicados
-- Base de datos: sistema_ventas_almacen
-- ============================================================================

-- Este script elimina los usuarios de ejemplo duplicados
-- Ejecutar si ves el error: "Duplicate entry 'VEND001' for key 'usuarios.uk_codigo_usuario'"

-- Ver usuarios duplicados antes de eliminar
SELECT codigo_usuario, COUNT(*) as cantidad
FROM usuarios
WHERE codigo_usuario IN ('ADMIN001', 'GER001', 'VEND001', 'ALM001', 'SUP001')
GROUP BY codigo_usuario
HAVING COUNT(*) > 1;

-- Eliminar todos los usuarios de ejemplo
DELETE FROM usuarios WHERE codigo_usuario IN ('ADMIN001', 'GER001', 'VEND001', 'ALM001', 'SUP001');

-- Verificar que se eliminaron
SELECT COUNT(*) as usuarios_ejemplo_restantes
FROM usuarios
WHERE codigo_usuario IN ('ADMIN001', 'GER001', 'VEND001', 'ALM001', 'SUP001');

-- Ahora puedes ejecutar nuevamente: insertar_usuarios_ejemplo.sql
