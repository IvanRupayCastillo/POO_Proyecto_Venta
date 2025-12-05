-- Script para insertar datos de prueba en la base de datos

USE sistema_ventas_almacen;

-- Insertar clientes de prueba si no existen
INSERT IGNORE INTO clientes (id_cliente, tipo_documento, numero_documento, razon_social, direccion, telefono, email, activo)
VALUES 
(1, 'DNI', '12345678', 'Cliente Genérico', 'Calle Principal 123', '999999999', 'cliente1@email.com', 1),
(2, 'DNI', '87654321', 'Segundo Cliente', 'Av. Secundaria 456', '988888888', 'cliente2@email.com', 1),
(3, 'RUC', '20123456789', 'Empresa Comercial SAC', 'Calle Tercera 789', '977777777', 'empresa@email.com', 1);

-- Insertar colores si no existen
INSERT IGNORE INTO colores (id_color, codigo_color, nombre_color, activo)
VALUES
(1, 'ROJO', 'Rojo', 1),
(2, 'AZUL', 'Azul', 1),
(3, 'NEGRO', 'Negro', 1),
(4, 'BLANCO', 'Blanco', 1),
(5, 'VERDE', 'Verde', 1);

-- Insertar tallas si no existen
INSERT IGNORE INTO tallas (id_talla, tipo_talla, nombre_talla, orden_visualizacion, activo)
VALUES
(1, 'NUMERICA', 'XS', 1, 1),
(2, 'NUMERICA', 'S', 2, 1),
(3, 'NUMERICA', 'M', 3, 1),
(4, 'NUMERICA', 'L', 4, 1),
(5, 'NUMERICA', 'XL', 5, 1),
(6, 'NUMERICA', 'XXL', 6, 1);

-- Insertar tiendas si no existen
INSERT IGNORE INTO tiendas (id_tienda, codigo_tienda, nombre_tienda, tipo_tienda, direccion, telefono, email, es_punto_venta, activo)
VALUES
(1, 'T001', 'Tienda Principal', 'TIENDA', 'Calle Principal 100', '966666666', 'tienda1@email.com', 1, 1),
(2, 'T002', 'Tienda Secundaria', 'TIENDA', 'Av. Central 200', 'tienda2@email.com', 955555555, 1, 1),
(3, 'ALMACEN1', 'Almacén Principal', 'ALMACEN_PRINCIPAL', 'Calle Almacén 300', '944444444', 'almacen@email.com', 0, 1);

-- Insertar tipos de movimiento si no existen
INSERT IGNORE INTO tipos_movimiento (id_tipo_movimiento, codigo_tipo, nombre_tipo, afecta_stock, requiere_autorizacion, descripcion, activo)
VALUES
(1, 'ENTRADA', 'Entrada de Stock', 'ENTRADA', 0, 'Entrada de productos al almacén', 1),
(2, 'SALIDA_VENTA', 'Salida por Venta', 'SALIDA', 0, 'Salida de productos por venta', 1),
(3, 'AJUSTE', 'Ajuste de Inventario', 'NEUTRO', 1, 'Ajuste de inventario físico', 1),
(4, 'DEVOLUCIÓN', 'Devolución de Compra', 'ENTRADA', 0, 'Devolución de productos comprados', 1),
(5, 'TRANSFERENCIA', 'Transferencia entre tiendas', 'NEUTRO', 0, 'Transferencia de productos entre tiendas', 1),
(6, 'PÉRDIDA', 'Pérdida/Daño', 'SALIDA', 1, 'Pérdida o daño de productos', 1),
(7, 'DEVOLUCIÓN_VENTA', 'Devolución por Venta', 'ENTRADA', 0, 'Devolución de productos vendidos', 1),
(8, 'CONSIGNACIÓN', 'Consignación', 'NEUTRO', 0, 'Productos en consignación', 1);

-- Insertar tipos de producto si no existen
INSERT IGNORE INTO tipos_producto (id_tipo, codigo_tipo, nombre_tipo, descripcion, activo)
VALUES
(1, 'POLO', 'Polos', 'Camisetas tipo polo', 1),
(2, 'CAMISA', 'Camisas', 'Camisas formales', 1),
(3, 'PANTALÓN', 'Pantalones', 'Pantalones diversos', 1),
(4, 'CHAQUETA', 'Chaquetas', 'Chaquetas y abrigos', 1);

-- Insertar usuarios/vendedores si no existen
INSERT IGNORE INTO usuarios (id_usuario, codigo_usuario, nombre_completo, email, password_hash, rol, id_tienda_asignada, activo)
VALUES
(1, 'ADMIN001', 'Administrador del Sistema', 'admin@email.com', SHA2('admin123', 256), 'ADMIN', 1, 1),
(2, 'VEND001', 'Vendedor Prueba', 'vendedor1@email.com', SHA2('vend123', 256), 'VENDEDOR', 1, 1),
(3, 'ALMAC001', 'Almacenero Prueba', 'almacen1@email.com', SHA2('almac123', 256), 'ALMACENERO', 1, 1);

-- Insertar productos si no existen
INSERT IGNORE INTO productos (id_producto, codigo_producto, nombre_producto, descripcion, id_tipo, id_color, id_talla, precio_compra, precio_venta_mayor, precio_venta_menor, stock_minimo, activo)
VALUES
(1, 'POLO-AZUL-M', 'Polo Azul Talla M', 'Polo azul de algodón talla M', 1, 2, 3, 50.00, 150.00, 180.00, 5, 1),
(2, 'POLO-ROJO-L', 'Polo Rojo Talla L', 'Polo rojo de algodón talla L', 1, 1, 4, 50.00, 150.00, 180.00, 5, 1),
(3, 'CAMISA-BLANCA-M', 'Camisa Blanca Talla M', 'Camisa blanca formal talla M', 2, 4, 3, 80.00, 200.00, 250.00, 3, 1),
(4, 'PANTALÓN-NEGRO-L', 'Pantalón Negro Talla L', 'Pantalón negro talla L', 3, 3, 4, 100.00, 250.00, 300.00, 5, 1);

-- Insertar inventario inicial
INSERT IGNORE INTO inventario (id_tienda, id_producto, stock_actual, stock_reservado)
VALUES
(1, 1, 50, 0),
(1, 2, 30, 0),
(1, 3, 20, 0),
(1, 4, 15, 0);

COMMIT;
