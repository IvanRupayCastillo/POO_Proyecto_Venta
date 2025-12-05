package proyecto_sistema_venta.Datos;

import proyecto_sistema_venta.Conexion.Conexion;
import proyecto_sistema_venta.Datos.Interfaces.IDetalleVenta;
import proyecto_sistema_venta.Entidades.DetalleVenta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaDAO implements IDetalleVenta {

    private final Conexion CNX;

    public DetalleVentaDAO() {
        this.CNX = Conexion.getInstancia();
    }

    @Override
    public boolean insertarLote(int idVenta, List<DetalleVenta> detalles) {
        boolean ok = false;
        PreparedStatement ps = null;
        try {
            ps = CNX.conectar().prepareStatement(
                    "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, descuento_item) VALUES (?,?,?,?,?)"
            );
            for (DetalleVenta d : detalles) {
                ps.setInt(1, idVenta);
                ps.setInt(2, d.getIdProducto());
                ps.setInt(3, d.getCantidad());
                ps.setDouble(4, d.getPrecioUnitario());
                ps.setDouble(5, d.getDescuentoItem());
                ps.addBatch();
            }
            int[] res = ps.executeBatch();
            ok = res.length == detalles.size();
            ps.close();
        } catch (SQLException e) {
            ok = false;
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return ok;
    }

    @Override
    public List<DetalleVenta> listarPorVenta(int idVenta) {
        List<DetalleVenta> registros = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = CNX.conectar().prepareStatement(
                    "SELECT * FROM detalle_venta WHERE id_venta = ?"
            );
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();
            while (rs.next()) {
                DetalleVenta d = new DetalleVenta();
                d.setIdDetalleVenta(rs.getInt("id_detalle_venta"));
                d.setIdVenta(rs.getInt("id_venta"));
                d.setIdProducto(rs.getInt("id_producto"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecioUnitario(rs.getDouble("precio_unitario"));
                d.setDescuentoItem(rs.getDouble("descuento_item"));
                registros.add(d);
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

