package proyecto_sistema_venta.Datos;

import proyecto_sistema_venta.Datos.Interfaces.ITalla;
import proyecto_sistema_venta.Entidades.Talla;
import proyecto_sistema_venta.Conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * TallaDAO - Implementa las operaciones de acceso a datos para Talla
 * Patrón DAO (Data Access Object)
 */
public class TallaDAO implements ITalla {
    
    private final Conexion CNX;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public TallaDAO() {
        this.CNX = Conexion.getInstancia();
    }

    @Override
    public List<Talla> listar(String texto) {
        List<Talla> lista = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT * FROM tallas WHERE nombre_talla LIKE ? ORDER BY tipo_talla, orden_visualizacion ASC"
            );
            ps.setString(1, "%" + texto + "%");
            rs = ps.executeQuery();
            
            while (rs.next()) {
                lista.add(new Talla(
                    rs.getInt("id_talla"),
                    rs.getString("tipo_talla"),
                    rs.getString("nombre_talla"),
                    rs.getInt("orden_visualizacion"),
                    rs.getBoolean("activo")
                ));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar tallas: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return lista;
    }

    @Override
    public boolean insertar(Talla obj) {
        resp = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "INSERT INTO tallas (tipo_talla, nombre_talla, orden_visualizacion, activo) VALUES (?,?,?,?)"
            );
            ps.setString(1, obj.getTipoTalla());
            ps.setString(2, obj.getNombreTalla());
            ps.setInt(3, obj.getOrdenVisualizacion());
            ps.setBoolean(4, obj.isActivo());
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar talla: " + e.getMessage());
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return resp;
    }

    @Override
    public boolean actualizar(Talla obj) {
        resp = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "UPDATE tallas SET tipo_talla=?, nombre_talla=?, orden_visualizacion=?, activo=? WHERE id_talla=?"
            );
            ps.setString(1, obj.getTipoTalla());
            ps.setString(2, obj.getNombreTalla());
            ps.setInt(3, obj.getOrdenVisualizacion());
            ps.setBoolean(4, obj.isActivo());
            ps.setInt(5, obj.getIdTalla());
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar talla: " + e.getMessage());
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
                "UPDATE tallas SET activo=0 WHERE id_talla=?"
            );
            ps.setInt(1, id);
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al desactivar talla: " + e.getMessage());
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
                "UPDATE tallas SET activo=1 WHERE id_talla=?"
            );
            ps.setInt(1, id);
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al activar talla: " + e.getMessage());
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
                "SELECT COUNT(id_talla) FROM tallas"
            );
            rs = ps.executeQuery();
            
            if (rs.next()) {
                totalRegistros = rs.getInt(1);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al contar tallas: " + e.getMessage());
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
                "SELECT id_talla FROM tallas WHERE nombre_talla=?"
            );
            ps.setString(1, texto);
            rs = ps.executeQuery();
            resp = rs.next();
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar talla: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return resp;
    }

    /**
     * Método adicional para seleccionar tallas activas (útil para ComboBox)
     * @return Lista de tallas activas ordenadas por tipo y orden de visualización
     */
    public List<Talla> seleccionar() {
        List<Talla> lista = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT id_talla, tipo_talla, nombre_talla, orden_visualizacion FROM tallas WHERE activo=1 ORDER BY tipo_talla, orden_visualizacion ASC"
            );
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Talla talla = new Talla();
                talla.setIdTalla(rs.getInt("id_talla"));
                talla.setTipoTalla(rs.getString("tipo_talla"));
                talla.setNombreTalla(rs.getString("nombre_talla"));
                talla.setOrdenVisualizacion(rs.getInt("orden_visualizacion"));
                lista.add(talla);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar tallas: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return lista;
    }
    
    /**
     * Método adicional para listar tallas por tipo
     * @param tipoTalla Tipo de talla (NUMERICA, LETRAS, ESPECIAL)
     * @return Lista de tallas del tipo especificado
     */
    public List<Talla> listarPorTipo(String tipoTalla) {
        List<Talla> lista = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT * FROM tallas WHERE tipo_talla=? AND activo=1 ORDER BY orden_visualizacion ASC"
            );
            ps.setString(1, tipoTalla);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                lista.add(new Talla(
                    rs.getInt("id_talla"),
                    rs.getString("tipo_talla"),
                    rs.getString("nombre_talla"),
                    rs.getInt("orden_visualizacion"),
                    rs.getBoolean("activo")
                ));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar tallas por tipo: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return lista;
    }
}
