package proyecto_sistema_venta.Datos;

import proyecto_sistema_venta.Conexion.Conexion;
import proyecto_sistema_venta.Datos.Interfaces.IVenta;
import proyecto_sistema_venta.Entidades.Venta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
}

