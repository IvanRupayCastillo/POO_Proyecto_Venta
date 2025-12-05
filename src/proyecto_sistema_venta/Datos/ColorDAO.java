package proyecto_sistema_venta.Datos;

import proyecto_sistema_venta.Datos.Interfaces.IColor;
import proyecto_sistema_venta.Entidades.Color;
import proyecto_sistema_venta.Conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * ColorDAO - Implementa las operaciones de acceso a datos para Color
 * Patrón DAO (Data Access Object)
 */
public class ColorDAO implements IColor {
    
    private final Conexion CNX;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public ColorDAO() {
        this.CNX = Conexion.getInstancia();
    }

    @Override
    public List<Color> listar(String texto) {
        List<Color> lista = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT * FROM colores WHERE nombre_color LIKE ? ORDER BY nombre_color ASC"
            );
            ps.setString(1, "%" + texto + "%");
            rs = ps.executeQuery();
            
            while (rs.next()) {
                lista.add(new Color(
                    rs.getInt("id_color"),
                    rs.getString("codigo_color"),
                    rs.getString("nombre_color"),
                    (Integer) rs.getObject("id_tienda"),
                    rs.getBoolean("activo")
                ));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar colores: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return lista;
    }
    
    public List<Color> listarPorTienda(String texto, int idTienda) {
        List<Color> lista = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT * FROM colores WHERE nombre_color LIKE ? AND id_tienda = ? ORDER BY nombre_color ASC"
            );
            ps.setString(1, "%" + texto + "%");
            ps.setInt(2, idTienda);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                lista.add(new Color(
                    rs.getInt("id_color"),
                    rs.getString("codigo_color"),
                    rs.getString("nombre_color"),
                    rs.getInt("id_tienda"),
                    rs.getBoolean("activo")
                ));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar colores por tienda: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return lista;
    }

    @Override
    public boolean insertar(Color obj) {
        resp = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "INSERT INTO colores (codigo_color, nombre_color, id_tienda, activo) VALUES (?,?,?,?)"
            );
            ps.setString(1, obj.getCodigoColor());
            ps.setString(2, obj.getNombreColor());
            ps.setObject(3, obj.getIdTienda());
            ps.setBoolean(4, obj.isActivo());
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar color: " + e.getMessage());
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return resp;
    }

    @Override
    public boolean actualizar(Color obj) {
        resp = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "UPDATE colores SET codigo_color=?, nombre_color=?, id_tienda=?, activo=? WHERE id_color=?"
            );
            ps.setString(1, obj.getCodigoColor());
            ps.setString(2, obj.getNombreColor());
            ps.setObject(3, obj.getIdTienda());
            ps.setBoolean(4, obj.isActivo());
            ps.setInt(5, obj.getIdColor());
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar color: " + e.getMessage());
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
                "UPDATE colores SET activo=0 WHERE id_color=?"
            );
            ps.setInt(1, id);
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al desactivar color: " + e.getMessage());
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
                "UPDATE colores SET activo=1 WHERE id_color=?"
            );
            ps.setInt(1, id);
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al activar color: " + e.getMessage());
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
                "SELECT COUNT(id_color) FROM colores"
            );
            rs = ps.executeQuery();
            
            if (rs.next()) {
                totalRegistros = rs.getInt(1);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al contar colores: " + e.getMessage());
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
                "SELECT id_color FROM colores WHERE codigo_color=?"
            );
            ps.setString(1, texto);
            rs = ps.executeQuery();
            resp = rs.next();
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar color: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return resp;
    }

    /**
     * Método adicional para seleccionar colores activos (útil para ComboBox)
     * @return Lista de colores activos
     */
    public List<Color> seleccionar() {
        List<Color> lista = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT id_color, codigo_color, nombre_color FROM colores WHERE activo=1 ORDER BY nombre_color ASC"
            );
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Color color = new Color();
                color.setIdColor(rs.getInt("id_color"));
                color.setCodigoColor(rs.getString("codigo_color"));
                color.setNombreColor(rs.getString("nombre_color"));
                lista.add(color);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar colores: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return lista;
    }
}
