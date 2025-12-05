-- Stored Procedure para registrar una venta completa con movimiento de inventario

DELIMITER //

DROP PROCEDURE IF EXISTS sp_registrar_venta_completa //

CREATE PROCEDURE sp_registrar_venta_completa(
    IN p_tipo_comprobante VARCHAR(20),      
    IN p_serie VARCHAR(4),                   
    IN p_numero VARCHAR(10),                 
    IN p_fecha DATE,                         
    IN p_hora TIME,                          
    IN p_id_tienda INT,                      
    IN p_id_cliente INT,                     
    IN p_id_usuario_vendedor INT,            
    IN p_subtotal DECIMAL(12,2),             
    IN p_descuento DECIMAL(12,2),            
    IN p_igv DECIMAL(12,2),                  
    IN p_total DECIMAL(12,2),                
    IN p_metodo_pago VARCHAR(20),            
    IN p_observaciones TEXT,                 
    IN p_id_tipo_movimiento INT,             
    IN p_numero_documento_mov VARCHAR(20),   
    IN p_json_detalle JSON,                  
    OUT p_id_venta_out INT,                  
    OUT p_id_movimiento_out INT,             
    OUT p_mensaje VARCHAR(255),              
    OUT p_codigo_error INT                   
)
MODIFIES SQL DATA
BEGIN
    DECLARE v_error_message VARCHAR(255) DEFAULT '';
    DECLARE v_id_venta INT DEFAULT 0;
    DECLARE v_id_movimiento INT DEFAULT 0;
    DECLARE v_idx INT DEFAULT 0;
    DECLARE v_id_producto INT;
    DECLARE v_cantidad INT;
    DECLARE v_precio_unitario DECIMAL(10,2);
    DECLARE v_descuento_item DECIMAL(10,2);
    DECLARE v_json_array_size INT DEFAULT 0;
    DECLARE v_rows_affected INT DEFAULT 0;
    
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        GET DIAGNOSTICS CONDITION 1 v_error_message = MESSAGE_TEXT;
        SET p_codigo_error = 1;
        SET p_mensaje = v_error_message;
        SET p_id_venta_out = 0;
        SET p_id_movimiento_out = 0;
        ROLLBACK;
    END;
    
    SET p_codigo_error = 0;
    SET p_mensaje = 'Iniciando...';
    SET p_id_venta_out = 0;
    SET p_id_movimiento_out = 0;
    
    IF p_tipo_comprobante NOT IN ('BOLETA', 'FACTURA', 'TICKET') THEN
        SET p_codigo_error = 1;
        SET p_mensaje = 'Tipo de comprobante inválido';
    ELSE
        IF p_metodo_pago NOT IN ('EFECTIVO', 'TARJETA', 'TRANSFERENCIA', 'MIXTO') THEN
            SET p_codigo_error = 1;
            SET p_mensaje = 'Método de pago inválido';
        ELSE
            START TRANSACTION;
            
            INSERT INTO comprobantes_venta (
                tipo_comprobante, serie_comprobante, numero_comprobante,
                fecha_venta, hora_venta, id_tienda, id_cliente, id_usuario_vendedor, 
                subtotal, descuento, igv, total, metodo_pago, estado, observaciones
            ) VALUES (
                p_tipo_comprobante, p_serie, p_numero, p_fecha, p_hora, 
                p_id_tienda, p_id_cliente, p_id_usuario_vendedor, 
                p_subtotal, p_descuento, p_igv, p_total, p_metodo_pago, 'EMITIDA', p_observaciones
            );
            
            SET v_id_venta = LAST_INSERT_ID();
            
            INSERT INTO movimientos_almacen_cabecera (
                numero_documento, id_tipo_movimiento, id_tienda_origen, 
                fecha_movimiento, hora_movimiento, id_usuario_registro, estado, monto_total
            ) VALUES (
                p_numero_documento_mov, p_id_tipo_movimiento, p_id_tienda, 
                p_fecha, p_hora, p_id_usuario_vendedor, 'CONFIRMADO', p_total
            );
            
            SET v_id_movimiento = LAST_INSERT_ID();
            SET v_json_array_size = JSON_LENGTH(p_json_detalle);
            SET v_idx = 0;
            
            WHILE v_idx < v_json_array_size DO
                SET v_id_producto = JSON_UNQUOTE(JSON_EXTRACT(p_json_detalle, CONCAT('$[', v_idx, '].id_producto')));
                SET v_cantidad = JSON_UNQUOTE(JSON_EXTRACT(p_json_detalle, CONCAT('$[', v_idx, '].cantidad')));
                SET v_precio_unitario = JSON_UNQUOTE(JSON_EXTRACT(p_json_detalle, CONCAT('$[', v_idx, '].precio_unitario')));
                SET v_descuento_item = JSON_UNQUOTE(JSON_EXTRACT(p_json_detalle, CONCAT('$[', v_idx, '].descuento_item')));
                
                INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, descuento_item)
                VALUES (v_id_venta, v_id_producto, v_cantidad, v_precio_unitario, v_descuento_item);
                
                INSERT INTO movimientos_almacen_detalle (id_movimiento, id_producto, cantidad, precio_unitario)
                VALUES (v_id_movimiento, v_id_producto, v_cantidad, v_precio_unitario);
                
                UPDATE inventario SET stock_actual = stock_actual - v_cantidad
                WHERE id_tienda = p_id_tienda AND id_producto = v_id_producto;
                
                SET v_rows_affected = ROW_COUNT();
                
                IF v_rows_affected = 0 THEN
                    INSERT INTO inventario (id_tienda, id_producto, stock_actual, stock_reservado)
                    VALUES (p_id_tienda, v_id_producto, -v_cantidad, 0);
                END IF;
                
                SET v_idx = v_idx + 1;
            END WHILE;
            
            COMMIT;
            
            SET p_id_venta_out = v_id_venta;
            SET p_id_movimiento_out = v_id_movimiento;
            SET p_codigo_error = 0;
            SET p_mensaje = 'Venta registrada exitosamente';
        END IF;
    END IF;
    
END //

DELIMITER ;
