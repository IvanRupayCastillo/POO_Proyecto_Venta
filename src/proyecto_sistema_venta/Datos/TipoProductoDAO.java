package proyecto_sistema_venta.Datos;

import proyecto_sistema_venta.Datos.Interfaces.ITipoProducto;
import proyecto_sistema_venta.Entidades.TipoProducto;
import proyecto_sistema_venta.Conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * TipoProductoDAO - Implementa las operaciones de acceso a datos para TipoProducto
 * Patrón DAO (Data Access Object)
 */
public class TipoProductoDAO implements ITipoProducto {
    
    private final Conexion CNX;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public TipoProductoDAO() {
        this.CNX = Conexion.getInstancia();
    }

    @Override
    public List<TipoProducto> listar(String texto) {
        List<TipoProducto> lista = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT * FROM tipos_producto WHERE nombre_tipo LIKE ? OR codigo_tipo LIKE ? ORDER BY nombre_tipo ASC"
            );
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");
            rs = ps.executeQuery();
            
            while (rs.next()) {
                lista.add(new TipoProducto(
                    rs.getInt("id_tipo"),
                    rs.getString("codigo_tipo"),
                    rs.getString("nombre_tipo"),
                    rs.getString("descripcion"),
                    rs.getBoolean("activo")
                ));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar tipos de producto: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return lista;
    }

    @Override
    public boolean insertar(TipoProducto obj) {
        resp = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "INSERT INTO tipos_producto (codigo_tipo, nombre_tipo, descripcion, activo) VALUES (?,?,?,?)"
            );
            ps.setString(1, obj.getCodigoTipo());
            ps.setString(2, obj.getNombreTipo());
            ps.setString(3, obj.getDescripcion());
            ps.setBoolean(4, obj.isActivo());
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar tipo de producto: " + e.getMessage());
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return resp;
    }

    @Override
    public boolean actualizar(TipoProducto obj) {
        resp = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "UPDATE tipos_producto SET codigo_tipo=?, nombre_tipo=?, descripcion=?, activo=? WHERE id_tipo=?"
            );
            ps.setString(1, obj.getCodigoTipo());
            ps.setString(2, obj.getNombreTipo());
            ps.setString(3, obj.getDescripcion());
            ps.setBoolean(4, obj.isActivo());
            ps.setInt(5, obj.getIdTipo());
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar tipo de producto: " + e.getMessage());
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return resp;
    }

    @Override
    public boolean desactivar(int id) {
        resp = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "UPDATE tipos_producto SET activo=0 WHERE id_tipo=?"
            );
            ps.setInt(1, id);
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al desactivar tipo de producto: " + e.getMessage());
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return resp;
    }

    @Override
    public boolean activar(int id) {
        resp = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "UPDATE tipos_producto SET activo=1 WHERE id_tipo=?"
            );
            ps.setInt(1, id);
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al activar tipo de producto: " + e.getMessage());
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return resp;
    }

    @Override
    public int total() {
        int totalRegistros = 0;
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT COUNT(id_tipo) FROM tipos_producto"
            );
            rs = ps.executeQuery();
            
            if (rs.next()) {
                totalRegistros = rs.getInt(1);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al contar tipos de producto: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return totalRegistros;
    }

    @Override
    public boolean existe(String texto) {
        resp = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT id_tipo FROM tipos_producto WHERE codigo_tipo=?"
            );
            ps.setString(1, texto);
            rs = ps.executeQuery();
            resp = rs.next();
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar tipo de producto: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return resp;
    }

    /**
     * Método adicional para seleccionar tipos de producto activos (útil para ComboBox)
     * @return Lista de tipos de producto activos
     */
    public List<TipoProducto> seleccionar() {
        List<TipoProducto> lista = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT id_tipo, codigo_tipo, nombre_tipo, descripcion FROM tipos_producto WHERE activo=1 ORDER BY nombre_tipo ASC"
            );
            rs = ps.executeQuery();
            
            while (rs.next()) {
                TipoProducto tipo = new TipoProducto();
                tipo.setIdTipo(rs.getInt("id_tipo"));
                tipo.setCodigoTipo(rs.getString("codigo_tipo"));
                tipo.setNombreTipo(rs.getString("nombre_tipo"));
                tipo.setDescripcion(rs.getString("descripcion"));
                lista.add(tipo);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar tipos de producto: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return lista;
    }
}
