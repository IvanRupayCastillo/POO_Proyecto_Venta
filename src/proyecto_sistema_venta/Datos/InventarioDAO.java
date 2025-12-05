package proyecto_sistema_venta.Datos;

import proyecto_sistema_venta.Conexion.Conexion;
import proyecto_sistema_venta.Datos.Interfaces.IInventario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventarioDAO implements IInventario {

    private final Conexion CNX;

    public InventarioDAO() {
        this.CNX = Conexion.getInstancia();
    }

    @Override
    public Integer obtenerStockDisponible(int idTienda, int idProducto) {
        Integer stock = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = CNX.conectar().prepareStatement(
                    "SELECT stock_disponible FROM inventario WHERE id_tienda = ? AND id_producto = ?"
            );
            ps.setInt(1, idTienda);
            ps.setInt(2, idProducto);
            rs = ps.executeQuery();
            if (rs.next()) {
                stock = rs.getInt("stock_disponible");
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            stock = null;
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return stock;
    }

    @Override
    public boolean disminuirStock(int idTienda, int idProducto, int cantidad) {
        boolean ok = false;
        PreparedStatement ps = null;
        try {
            ps = CNX.conectar().prepareStatement(
                    "UPDATE inventario SET stock_reservado = 0, stock_actual = stock_actual - ? WHERE id_tienda = ? AND id_producto = ? AND stock_disponible >= ?"
            );
            ps.setInt(1, cantidad);
            ps.setInt(2, idTienda);
            ps.setInt(3, idProducto);
            ps.setInt(4, cantidad);
            ok = ps.executeUpdate() > 0;
            ps.close();
        } catch (SQLException e) {
            ok = false;
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return ok;
    }

    /**
     * Crea un registro de inventario para una tienda-producto si no existe.
     */
    public boolean crearInventarioSiNoExiste(int idTienda, int idProducto, int stockInicial) {
        boolean ok = false;
        PreparedStatement ps = null;
        try {
            ps = CNX.conectar().prepareStatement(
                "INSERT INTO inventario (id_tienda, id_producto, stock_actual, stock_reservado) " +
                "VALUES (?,?,?,0) ON DUPLICATE KEY UPDATE stock_actual = stock_actual"
            );
            ps.setInt(1, idTienda);
            ps.setInt(2, idProducto);
            ps.setInt(3, stockInicial);
            ok = ps.executeUpdate() > 0;
            ps.close();
        } catch (SQLException e) {
            ok = false;
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return ok;
    }
}

