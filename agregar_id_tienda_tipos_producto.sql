-- Script para agregar columna id_tienda a las tablas tipos_producto, colores y tallas
-- Ejecutar este script en la base de datos sistema_ventas_almacen

-- 1. Agregar la columna id_tienda a tipos_producto
ALTER TABLE tipos_producto 
ADD COLUMN id_tienda INT DEFAULT NULL AFTER descripcion,
ADD CONSTRAINT fk_tipos_producto_tienda 
    FOREIGN KEY (id_tienda) REFERENCES tiendas(id_tienda)
    ON DELETE SET NULL 
    ON UPDATE CASCADE;

-- 2. Agregar la columna id_tienda a colores
ALTER TABLE colores 
ADD COLUMN id_tienda INT DEFAULT NULL AFTER nombre_color,
ADD CONSTRAINT fk_colores_tienda 
    FOREIGN KEY (id_tienda) REFERENCES tiendas(id_tienda)
    ON DELETE SET NULL 
    ON UPDATE CASCADE;

-- 3. Agregar la columna id_tienda a tallas
ALTER TABLE tallas 
ADD COLUMN id_tienda INT DEFAULT NULL AFTER orden_visualizacion,
ADD CONSTRAINT fk_tallas_tienda 
    FOREIGN KEY (id_tienda) REFERENCES tiendas(id_tienda)
    ON DELETE SET NULL 
    ON UPDATE CASCADE;

-- Opcional: Asignar una tienda por defecto a los registros existentes
-- Descomenta y modifica el ID de la tienda según sea necesario
-- UPDATE tipos_producto SET id_tienda = 1 WHERE id_tienda IS NULL;
-- UPDATE colores SET id_tienda = 1 WHERE id_tienda IS NULL;
-- UPDATE tallas SET id_tienda = 1 WHERE id_tienda IS NULL;

-- Verificar los cambios
SELECT 'Columnas id_tienda agregadas exitosamente a tipos_producto, colores y tallas' AS resultado;
