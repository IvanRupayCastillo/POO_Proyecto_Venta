package proyecto_sistema_venta.Datos;

import proyecto_sistema_venta.Conexion.Conexion;
import proyecto_sistema_venta.Datos.Interfaces.IProducto;
import proyecto_sistema_venta.Entidades.Producto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductoDAO implements IProducto {
    
    private final Conexion CNX;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean confirmacion;
    
    public ProductoDAO() {
        this.CNX = Conexion.getInstancia();
    }
    
    @Override
    public List<Producto> listar(String texto) {
        List<Producto> registros = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT id_producto, codigo_producto, nombre_producto, descripcion, " +
                "id_tipo, id_color, id_talla, precio_compra, precio_venta_mayor, " +
                "precio_venta_menor, stock_minimo, activo, created_at, updated_at " +
                "FROM productos " +
                "WHERE nombre_producto LIKE ? OR codigo_producto LIKE ? " +
                "ORDER BY id_producto DESC"
            );
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");
            rs = ps.executeQuery();
            
            while(rs.next()) {
                registros.add(new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("codigo_producto"),
                    rs.getString("nombre_producto"),
                    rs.getString("descripcion"),
                    rs.getInt("id_tipo"),
                    rs.getInt("id_color"),
                    rs.getInt("id_talla"),
                    rs.getDouble("precio_compra"),
                    rs.getDouble("precio_venta_mayor"),
                    rs.getDouble("precio_venta_menor"),
                    rs.getInt("stock_minimo"),
                    rs.getBoolean("activo"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                ));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar productos: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return registros;
    }

    /**
     * Lista productos que tienen inventario en la tienda indicada.
     */
    public List<Producto> listarPorTienda(String texto, int idTienda) {
        List<Producto> registros = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT p.id_producto, p.codigo_producto, p.nombre_producto, p.descripcion, " +
                "p.id_tipo, p.id_color, p.id_talla, p.precio_compra, p.precio_venta_mayor, " +
                "p.precio_venta_menor, p.stock_minimo, p.activo, p.created_at, p.updated_at " +
                "FROM productos p " +
                "JOIN inventario i ON i.id_producto = p.id_producto " +
                "WHERE i.id_tienda = ? AND (p.nombre_producto LIKE ? OR p.codigo_producto LIKE ?) " +
                "ORDER BY p.id_producto DESC"
            );
            ps.setInt(1, idTienda);
            ps.setString(2, "%" + texto + "%");
            ps.setString(3, "%" + texto + "%");
            rs = ps.executeQuery();

            while(rs.next()) {
                registros.add(new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("codigo_producto"),
                    rs.getString("nombre_producto"),
                    rs.getString("descripcion"),
                    rs.getInt("id_tipo"),
                    rs.getInt("id_color"),
                    rs.getInt("id_talla"),
                    rs.getDouble("precio_compra"),
                    rs.getDouble("precio_venta_mayor"),
                    rs.getDouble("precio_venta_menor"),
                    rs.getInt("stock_minimo"),
                    rs.getBoolean("activo"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                ));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar productos por tienda: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return registros;
    }
    
    @Override
    public boolean insertar(Producto producto) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "INSERT INTO productos (codigo_producto, nombre_producto, descripcion, " +
                "id_tipo, id_color, id_talla, precio_compra, precio_venta_mayor, " +
                "precio_venta_menor, stock_minimo, activo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, producto.getCodigoProducto());
            ps.setString(2, producto.getNombreProducto());
            ps.setString(3, producto.getDescripcion());
            ps.setInt(4, producto.getIdTipo());
            ps.setInt(5, producto.getIdColor());
            ps.setInt(6, producto.getIdTalla());
            ps.setDouble(7, producto.getPrecioCompra());
            ps.setDouble(8, producto.getPrecioVentaMayor());
            ps.setDouble(9, producto.getPrecioVentaMenor());
            ps.setInt(10, producto.getStockMinimo());
            ps.setBoolean(11, producto.isActivo());
            
            if (ps.executeUpdate() > 0) {
                confirmacion = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar producto: " + e.getMessage());
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return confirmacion;
    }
    
    @Override
    public boolean actualizar(Producto producto) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "UPDATE productos SET codigo_producto = ?, nombre_producto = ?, " +
                "descripcion = ?, id_tipo = ?, id_color = ?, id_talla = ?, " +
                "precio_compra = ?, precio_venta_mayor = ?, precio_venta_menor = ?, " +
                "stock_minimo = ?, activo = ? " +
                "WHERE id_producto = ?"
            );
            ps.setString(1, producto.getCodigoProducto());
            ps.setString(2, producto.getNombreProducto());
            ps.setString(3, producto.getDescripcion());
            ps.setInt(4, producto.getIdTipo());
            ps.setInt(5, producto.getIdColor());
            ps.setInt(6, producto.getIdTalla());
            ps.setDouble(7, producto.getPrecioCompra());
            ps.setDouble(8, producto.getPrecioVentaMayor());
            ps.setDouble(9, producto.getPrecioVentaMenor());
            ps.setInt(10, producto.getStockMinimo());
            ps.setBoolean(11, producto.isActivo());
            ps.setInt(12, producto.getIdProducto());
            
            if (ps.executeUpdate() > 0) {
                confirmacion = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar producto: " + e.getMessage());
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return confirmacion;
    }
    
    @Override
    public boolean eliminar(int idProducto) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "DELETE FROM productos WHERE id_producto = ?"
            );
            ps.setInt(1, idProducto);
            
            if (ps.executeUpdate() > 0) {
                confirmacion = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar producto: " + e.getMessage());
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return confirmacion;
    }
    
    @Override
    public boolean desactivar(int idProducto) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "UPDATE productos SET activo = 0 WHERE id_producto = ?"
            );
            ps.setInt(1, idProducto);
            
            if (ps.executeUpdate() > 0) {
                confirmacion = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al desactivar producto: " + e.getMessage());
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return confirmacion;
    }
    
    @Override
    public boolean activar(int idProducto) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "UPDATE productos SET activo = 1 WHERE id_producto = ?"
            );
            ps.setInt(1, idProducto);
            
            if (ps.executeUpdate() > 0) {
                confirmacion = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al activar producto: " + e.getMessage());
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return confirmacion;
    }
    
    @Override
    public Producto buscarPorId(int idProducto) {
        Producto producto = null;
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT * FROM productos WHERE id_producto = ?"
            );
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                producto = new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("codigo_producto"),
                    rs.getString("nombre_producto"),
                    rs.getString("descripcion"),
                    rs.getInt("id_tipo"),
                    rs.getInt("id_color"),
                    rs.getInt("id_talla"),
                    rs.getDouble("precio_compra"),
                    rs.getDouble("precio_venta_mayor"),
                    rs.getDouble("precio_venta_menor"),
                    rs.getInt("stock_minimo"),
                    rs.getBoolean("activo"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar producto por ID: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return producto;
    }
    
    @Override
    public List<Producto> buscarPorDescripcion(String descripcion) {
        List<Producto> registros = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT * FROM productos WHERE descripcion LIKE ? ORDER BY nombre_producto"
            );
            ps.setString(1, "%" + descripcion + "%");
            rs = ps.executeQuery();
            
            while(rs.next()) {
                registros.add(new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("codigo_producto"),
                    rs.getString("nombre_producto"),
                    rs.getString("descripcion"),
                    rs.getInt("id_tipo"),
                    rs.getInt("id_color"),
                    rs.getInt("id_talla"),
                    rs.getDouble("precio_compra"),
                    rs.getDouble("precio_venta_mayor"),
                    rs.getDouble("precio_venta_menor"),
                    rs.getInt("stock_minimo"),
                    rs.getBoolean("activo"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                ));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar por descripción: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return registros;
    }
    
    @Override
    public Producto buscarPorCodigo(String codigoProducto) {
        Producto producto = null;
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT * FROM productos WHERE codigo_producto = ?"
            );
            ps.setString(1, codigoProducto);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                producto = new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("codigo_producto"),
                    rs.getString("nombre_producto"),
                    rs.getString("descripcion"),
                    rs.getInt("id_tipo"),
                    rs.getInt("id_color"),
                    rs.getInt("id_talla"),
                    rs.getDouble("precio_compra"),
                    rs.getDouble("precio_venta_mayor"),
                    rs.getDouble("precio_venta_menor"),
                    rs.getInt("stock_minimo"),
                    rs.getBoolean("activo"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar por código: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return producto;
    }
    
    @Override
    public boolean existeCodigo(String codigoProducto) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT codigo_producto FROM productos WHERE codigo_producto = ?"
            );
            ps.setString(1, codigoProducto);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                confirmacion = true;
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar código: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return confirmacion;
    }
    
    @Override
    public int total() {
        int totalRegistros = 0;
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT COUNT(*) as total FROM productos"
            );
            rs = ps.executeQuery();
            
            if (rs.next()) {
                totalRegistros = rs.getInt("total");
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al contar productos: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return totalRegistros;
    }
}










