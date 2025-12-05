-- Script para insertar ventas de ejemplo
-- Ejecutar este script en la base de datos sistema_ventas_almacen

-- Insertar algunas ventas de ejemplo
INSERT INTO comprobantes_venta 
(tipo_comprobante, serie_comprobante, numero_comprobante, fecha_venta, hora_venta, 
 id_tienda, id_cliente, id_usuario_vendedor, subtotal, descuento, igv, total, 
 metodo_pago, estado, observaciones)
VALUES
('BOLETA', 'B001', '00000001', '2025-12-01', '10:30:00', 1, 1, 1, 100.00, 0.00, 18.00, 118.00, 'EFECTIVO', 'EMITIDA', 'Venta de prueba 1'),
('FACTURA', 'F001', '00000001', '2025-12-02', '14:15:00', 1, 2, 1, 250.00, 10.00, 43.20, 283.20, 'TARJETA', 'EMITIDA', 'Venta de prueba 2'),
('TICKET', 'T001', '00000001', '2025-12-03', '16:45:00', 1, NULL, 1, 50.00, 0.00, 9.00, 59.00, 'EFECTIVO', 'EMITIDA', 'Venta de prueba 3'),
('BOLETA', 'B001', '00000002', '2025-12-04', '11:20:00', 1, 3, 1, 180.00, 5.00, 31.50, 206.50, 'TRANSFERENCIA', 'EMITIDA', 'Venta de prueba 4'),
('FACTURA', 'F001', '00000002', '2025-12-05', '09:00:00', 1, 1, 1, 320.00, 0.00, 57.60, 377.60, 'EFECTIVO', 'EMITIDA', 'Venta de prueba 5');

-- Verificar las ventas insertadas
SELECT 
    id_venta,
    tipo_comprobante,
    CONCAT(serie_comprobante, '-', numero_comprobante) AS comprobante,
    fecha_venta,
    total,
    metodo_pago,
    estado
FROM comprobantes_venta
ORDER BY fecha_venta DESC, hora_venta DESC;
