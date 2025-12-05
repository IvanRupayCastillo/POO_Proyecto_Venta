package proyecto_sistema_venta.Datos;

import proyecto_sistema_venta.Conexion.Conexion;
import proyecto_sistema_venta.Datos.Interfaces.IVenta;
import proyecto_sistema_venta.Entidades.Venta;
import proyecto_sistema_venta.Entidades.DetalleVenta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.Time;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class VentaDAO implements IVenta {

    private final Conexion CNX;

    public VentaDAO() {
        this.CNX = Conexion.getInstancia();
    }

    @Override
    public int insertar(Venta venta) {
        int idGenerado = 0;
        PreparedStatement ps = null;
        try {
            ps = CNX.conectar().prepareStatement(
                    "INSERT INTO comprobantes_venta (tipo_comprobante, serie_comprobante, numero_comprobante, fecha_venta, hora_venta, id_tienda, id_cliente, id_usuario_vendedor, subtotal, descuento, igv, total, metodo_pago, estado, observaciones) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, venta.getTipoComprobante());
            ps.setString(2, venta.getSerieComprobante());
            ps.setString(3, venta.getNumeroComprobante());
            ps.setDate(4, Date.valueOf(venta.getFechaVenta()));
            ps.setTime(5, Time.valueOf(venta.getHoraVenta()));
            ps.setInt(6, venta.getIdTienda());
            if (venta.getIdCliente() == null) {
                ps.setNull(7, java.sql.Types.INTEGER);
            } else {
                ps.setInt(7, venta.getIdCliente());
            }
            ps.setInt(8, venta.getIdUsuarioVendedor());
            ps.setDouble(9, venta.getSubtotal());
            ps.setDouble(10, venta.getDescuento());
            ps.setDouble(11, venta.getIgv());
            ps.setDouble(12, venta.getTotal());
            ps.setString(13, venta.getMetodoPago());
            ps.setString(14, venta.getEstado());
            ps.setString(15, venta.getObservaciones());
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
                rs.close();
            }
            ps.close();
        } catch (SQLException e) {
            idGenerado = 0;
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return idGenerado;
    }

    @Override
    public Venta buscarPorId(int idVenta) {
        Venta venta = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = CNX.conectar().prepareStatement(
                    "SELECT * FROM comprobantes_venta WHERE id_venta = ?"
            );
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();
            if (rs.next()) {
                venta = new Venta();
                venta.setIdVenta(rs.getInt("id_venta"));
                venta.setTipoComprobante(rs.getString("tipo_comprobante"));
                venta.setSerieComprobante(rs.getString("serie_comprobante"));
                venta.setNumeroComprobante(rs.getString("numero_comprobante"));
                venta.setFechaVenta(rs.getDate("fecha_venta").toLocalDate());
                venta.setHoraVenta(rs.getTime("hora_venta").toLocalTime());
                venta.setIdTienda(rs.getInt("id_tienda"));
                int idCli = rs.getInt("id_cliente");
                venta.setIdCliente(rs.wasNull() ? null : idCli);
                venta.setIdUsuarioVendedor(rs.getInt("id_usuario_vendedor"));
                venta.setSubtotal(rs.getDouble("subtotal"));
                venta.setDescuento(rs.getDouble("descuento"));
                venta.setIgv(rs.getDouble("igv"));
                venta.setTotal(rs.getDouble("total"));
                venta.setMetodoPago(rs.getString("metodo_pago"));
                venta.setEstado(rs.getString("estado"));
                venta.setObservaciones(rs.getString("observaciones"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return venta;
    }

    @Override
    public List<Venta> listarPorTienda(int idTienda) {
        List<Venta> registros = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = CNX.conectar().prepareStatement(
                    "SELECT * FROM comprobantes_venta WHERE id_tienda = ? ORDER BY fecha_venta DESC, hora_venta DESC"
            );
            ps.setInt(1, idTienda);
            rs = ps.executeQuery();
            while (rs.next()) {
                Venta v = new Venta();
                v.setIdVenta(rs.getInt("id_venta"));
                v.setTipoComprobante(rs.getString("tipo_comprobante"));
                v.setSerieComprobante(rs.getString("serie_comprobante"));
                v.setNumeroComprobante(rs.getString("numero_comprobante"));
                v.setFechaVenta(rs.getDate("fecha_venta").toLocalDate());
                v.setHoraVenta(rs.getTime("hora_venta").toLocalTime());
                v.setIdTienda(rs.getInt("id_tienda"));
                int idCli = rs.getInt("id_cliente");
                v.setIdCliente(rs.wasNull() ? null : idCli);
                v.setIdUsuarioVendedor(rs.getInt("id_usuario_vendedor"));
                v.setSubtotal(rs.getDouble("subtotal"));
                v.setDescuento(rs.getDouble("descuento"));
                v.setIgv(rs.getDouble("igv"));
                v.setTotal(rs.getDouble("total"));
                v.setMetodoPago(rs.getString("metodo_pago"));
                v.setEstado(rs.getString("estado"));
                v.setObservaciones(rs.getString("observaciones"));
                registros.add(v);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return registros;
    }

    /**
     * Registra una venta completa usando el stored procedure sp_registrar_venta_completa
     * @param venta Objeto Venta con los datos de la cabecera
     * @param detalles Lista de DetalleVenta con los productos
     * @param idTipoMovimiento ID del tipo de movimiento (debe ser SALIDA POR VENTA)
     * @param numeroDocumentoMov Número de documento del movimiento
     * @return Array con [idVenta, idMovimiento, codigoError, mensaje]
     */
    public Object[] registrarVentaCompleta(Venta venta, List<DetalleVenta> detalles, int idTipoMovimiento, String numeroDocumentoMov) {
        System.out.println("VentaDAO.registrarVentaCompleta - INICIO");
        CallableStatement cs = null;
        Object[] resultado = new Object[4]; // [idVenta, idMovimiento, codigoError, mensaje]
        
        try {
            System.out.println("LOG DAO: Construyendo JSON del detalle...");
            // Construir JSON del detalle
            StringBuilder jsonDetalle = new StringBuilder("[");
            for (int i = 0; i < detalles.size(); i++) {
                DetalleVenta det = detalles.get(i);
                if (i > 0) jsonDetalle.append(",");
                jsonDetalle.append("{");
                jsonDetalle.append("\"id_producto\":").append(det.getIdProducto()).append(",");
                jsonDetalle.append("\"cantidad\":").append(det.getCantidad()).append(",");
                jsonDetalle.append("\"precio_unitario\":").append(det.getPrecioUnitario()).append(",");
                jsonDetalle.append("\"descuento_item\":").append(det.getDescuentoItem());
                jsonDetalle.append("}");
            }
            jsonDetalle.append("]");
            
            System.out.println("LOG DAO: JSON Detalle: " + jsonDetalle.toString());
            
            // Preparar llamada al stored procedure (21 parámetros: 17 IN + 4 OUT)
            String sql = "{CALL sp_registrar_venta_completa(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            System.out.println("LOG DAO: Conectando a BD...");
            cs = CNX.conectar().prepareCall(sql);
            System.out.println("LOG DAO: Conexión establecida");
            
            // Parámetros de entrada (1-17)
            System.out.println("LOG DAO: Configurando parámetros...");
            System.out.println("LOG DAO: P1 tipo_comprobante: " + venta.getTipoComprobante().toUpperCase());
            cs.setString(1, venta.getTipoComprobante().toUpperCase()); // BOLETA, FACTURA, TICKET
            System.out.println("LOG DAO: P2 serie: " + venta.getSerieComprobante());
            cs.setString(2, venta.getSerieComprobante());
            System.out.println("LOG DAO: P3 numero: " + venta.getNumeroComprobante());
            cs.setString(3, venta.getNumeroComprobante());
            System.out.println("LOG DAO: P4 fecha: " + venta.getFechaVenta());
            cs.setDate(4, Date.valueOf(venta.getFechaVenta()));
            System.out.println("LOG DAO: P5 hora: " + venta.getHoraVenta());
            cs.setTime(5, Time.valueOf(venta.getHoraVenta()));
            System.out.println("LOG DAO: P6 idTienda: " + venta.getIdTienda());
            cs.setInt(6, venta.getIdTienda());
            
            System.out.println("LOG DAO: P7 idCliente: " + venta.getIdCliente());
            if (venta.getIdCliente() == null) {
                cs.setNull(7, Types.INTEGER);
            } else {
                cs.setInt(7, venta.getIdCliente());
            }
            
            System.out.println("LOG DAO: P8 idUsuarioVendedor: " + venta.getIdUsuarioVendedor());
            cs.setInt(8, venta.getIdUsuarioVendedor());
            System.out.println("LOG DAO: P9 subtotal: " + venta.getSubtotal());
            cs.setDouble(9, venta.getSubtotal());
            System.out.println("LOG DAO: P10 descuento: " + venta.getDescuento());
            cs.setDouble(10, venta.getDescuento());
            System.out.println("LOG DAO: P11 igv: " + venta.getIgv());
            cs.setDouble(11, venta.getIgv());
            System.out.println("LOG DAO: P12 total: " + venta.getTotal());
            cs.setDouble(12, venta.getTotal());
            System.out.println("LOG DAO: P13 metodoPago: " + venta.getMetodoPago().toUpperCase());
            cs.setString(13, venta.getMetodoPago().toUpperCase()); // EFECTIVO, TARJETA, TRANSFERENCIA, MIXTO
            
            // Observaciones puede ser null
            System.out.println("LOG DAO: P14 observaciones: " + venta.getObservaciones());
            if (venta.getObservaciones() == null || venta.getObservaciones().isEmpty()) {
                cs.setNull(14, Types.VARCHAR);
            } else {
                cs.setString(14, venta.getObservaciones());
            }
            
            System.out.println("LOG DAO: P15 idTipoMovimiento: " + idTipoMovimiento);
            cs.setInt(15, idTipoMovimiento);
            System.out.println("LOG DAO: P16 numeroDocMov: " + numeroDocumentoMov);
            cs.setString(16, numeroDocumentoMov);
            System.out.println("LOG DAO: P17 jsonDetalle: " + jsonDetalle.toString());
            cs.setString(17, jsonDetalle.toString());
            
            // Parámetros de salida (18-21)
            cs.registerOutParameter(18, Types.INTEGER); // p_id_venta_out
            cs.registerOutParameter(19, Types.INTEGER); // p_id_movimiento_out
            cs.registerOutParameter(20, Types.VARCHAR); // p_mensaje
            cs.registerOutParameter(21, Types.INTEGER); // p_codigo_error
            
            // Ejecutar
            System.out.println("LOG DAO: Ejecutando stored procedure...");
            cs.execute();
            System.out.println("LOG DAO: Stored procedure ejecutado");
            
            // Obtener resultados
            System.out.println("LOG DAO: Obteniendo resultados...");
            
            // Leer en el orden correcto
            int idVentaOut = cs.getInt(18);
            boolean wasNull1 = cs.wasNull();
            System.out.println("LOG DAO: idVenta OUT: " + idVentaOut + " (wasNull: " + wasNull1 + ")");
            
            int idMovimientoOut = cs.getInt(19);
            boolean wasNull2 = cs.wasNull();
            System.out.println("LOG DAO: idMovimiento OUT: " + idMovimientoOut + " (wasNull: " + wasNull2 + ")");
            
            String mensajeOut = cs.getString(20);
            boolean wasNull3 = cs.wasNull();
            System.out.println("LOG DAO: mensaje OUT: '" + mensajeOut + "' (wasNull: " + wasNull3 + ")");
            
            int codigoErrorOut = cs.getInt(21);
            boolean wasNull4 = cs.wasNull();
            System.out.println("LOG DAO: codigoError OUT: " + codigoErrorOut + " (wasNull: " + wasNull4 + ")");
            
            resultado[0] = idVentaOut;
            resultado[1] = idMovimientoOut;
            resultado[2] = codigoErrorOut;
            resultado[3] = mensajeOut;
            
            cs.close();
            System.out.println("VentaDAO.registrarVentaCompleta - FIN EXITOSO");
            
        } catch (SQLException e) {
            System.out.println("ERROR SQLException en VentaDAO: " + e.getMessage());
            System.out.println("ERROR SQLState: " + e.getSQLState());
            System.out.println("ERROR ErrorCode: " + e.getErrorCode());
            e.printStackTrace();
            resultado[0] = null;
            resultado[1] = null;
            resultado[2] = -1;
            resultado[3] = "Error al registrar venta: " + e.getMessage();
            JOptionPane.showMessageDialog(null, resultado[3]);
        } finally {
            cs = null;
            CNX.desconectar();
        }
        
        return resultado;
    }
}
