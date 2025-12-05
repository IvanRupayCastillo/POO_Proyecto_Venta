package proyecto_sistema_venta.Datos;

import proyecto_sistema_venta.Conexion.Conexion;
import proyecto_sistema_venta.Entidades.Usuario;
import java.sql.*;
import java.time.LocalDateTime;

public class UsuarioDAO {
    
    private final Conexion CNX;
    
    public UsuarioDAO() {
        this.CNX = Conexion.getInstancia();
    }
    
    /**
     * Busca un usuario por email
     */
    public Usuario buscarPorEmail(String email) {
        Usuario usuario = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = "SELECT * FROM usuarios WHERE email = ? AND activo = 1";
            ps = CNX.conectar().prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                usuario = mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por email: " + e.getMessage());
        } finally {
            cerrarRecursos(ps, rs);
        }
        
        return usuario;
    }
    
    /**
     * Busca un usuario por código de usuario
     */
    public Usuario buscarPorCodigo(String codigoUsuario) {
        Usuario usuario = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = "SELECT * FROM usuarios WHERE codigo_usuario = ? AND activo = 1";
            ps = CNX.conectar().prepareStatement(sql);
            ps.setString(1, codigoUsuario);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                usuario = mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por código: " + e.getMessage());
        } finally {
            cerrarRecursos(ps, rs);
        }
        
        return usuario;
    }
    
    /**
     * Inserta un nuevo usuario
     */
    public int insertar(Usuario usuario) {
        int idGenerado = 0;
        PreparedStatement ps = null;
        
        try {
            String sql = "INSERT INTO usuarios (codigo_usuario, nombre_completo, email, password_hash, rol, id_tienda_asignada, activo) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = CNX.conectar().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, usuario.getCodigoUsuario());
            ps.setString(2, usuario.getNombreCompleto());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getPasswordHash());
            ps.setString(5, usuario.getRol());
            
            if (usuario.getIdTiendaAsignada() != null) {
                ps.setInt(6, usuario.getIdTiendaAsignada());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            
            ps.setBoolean(7, usuario.isActivo());
            
            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
        } finally {
            cerrarRecursos(ps, null);
        }
        
        return idGenerado;
    }
    
    /**
     * Actualiza el último acceso del usuario
     */
    public boolean actualizarUltimoAcceso(int idUsuario) {
        PreparedStatement ps = null;
        boolean resultado = false;
        
        try {
            String sql = "UPDATE usuarios SET ultimo_acceso = NOW() WHERE id_usuario = ?";
            ps = CNX.conectar().prepareStatement(sql);
            ps.setInt(1, idUsuario);
            resultado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar último acceso: " + e.getMessage());
        } finally {
            cerrarRecursos(ps, null);
        }
        
        return resultado;
    }
    
    /**
     * Actualiza la contraseña del usuario
     */
    public boolean actualizarPassword(int idUsuario, String nuevoPasswordHash) {
        PreparedStatement ps = null;
        boolean resultado = false;
        
        try {
            String sql = "UPDATE usuarios SET password_hash = ? WHERE id_usuario = ?";
            ps = CNX.conectar().prepareStatement(sql);
            ps.setString(1, nuevoPasswordHash);
            ps.setInt(2, idUsuario);
            resultado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar contraseña: " + e.getMessage());
        } finally {
            cerrarRecursos(ps, null);
        }
        
        return resultado;
    }
    
    /**
     * Verifica si un email ya está registrado
     */
    public boolean existeEmail(String email) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean existe = false;
        
        try {
            String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
            ps = CNX.conectar().prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar email: " + e.getMessage());
        } finally {
            cerrarRecursos(ps, rs);
        }
        
        return existe;
    }
    
    /**
     * Verifica si un código de usuario ya está registrado
     */
    public boolean existeCodigoUsuario(String codigoUsuario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean existe = false;
        
        try {
            String sql = "SELECT COUNT(*) FROM usuarios WHERE codigo_usuario = ?";
            ps = CNX.conectar().prepareStatement(sql);
            ps.setString(1, codigoUsuario);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar código de usuario: " + e.getMessage());
        } finally {
            cerrarRecursos(ps, rs);
        }
        
        return existe;
    }
    
    /**
     * Mapea un ResultSet a un objeto Usuario
     */
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setCodigoUsuario(rs.getString("codigo_usuario"));
        usuario.setNombreCompleto(rs.getString("nombre_completo"));
        usuario.setEmail(rs.getString("email"));
        usuario.setPasswordHash(rs.getString("password_hash"));
        usuario.setRol(rs.getString("rol"));
        
        int idTienda = rs.getInt("id_tienda_asignada");
        usuario.setIdTiendaAsignada(rs.wasNull() ? null : idTienda);
        
        usuario.setActivo(rs.getBoolean("activo"));
        
        Timestamp ultimoAcceso = rs.getTimestamp("ultimo_acceso");
        if (ultimoAcceso != null) {
            usuario.setUltimoAcceso(ultimoAcceso.toLocalDateTime());
        }
        
        return usuario;
    }
    
    /**
     * Cierra recursos de base de datos
     */
    private void cerrarRecursos(PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        } finally {
            CNX.desconectar();
        }
    }
}
