package proyecto_sistema_venta.Datos;

import proyecto_sistema_venta.Datos.Interfaces.ICliente;
import proyecto_sistema_venta.Entidades.Cliente;
import proyecto_sistema_venta.Conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * ClienteDAO - Implementa las operaciones de acceso a datos para Cliente
 * Patrón DAO (Data Access Object)
 */
public class ClienteDAO implements ICliente {
    
    private final Conexion CNX;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public ClienteDAO() {
        this.CNX = Conexion.getInstancia();
    }

    @Override
    public List<Cliente> listar(String texto) {
        List<Cliente> lista = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT * FROM clientes WHERE razon_social LIKE ? OR numero_documento LIKE ? ORDER BY razon_social ASC"
            );
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId_cliente(rs.getInt("id_cliente"));
                cliente.setTipo_documento(rs.getString("tipo_documento"));
                cliente.setNumero_documento(rs.getString("numero_documento"));
                cliente.setRazon_social(rs.getString("razon_social"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
                cliente.setActivo(rs.getBoolean("activo"));
                lista.add(cliente);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar clientes: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return lista;
    }

    @Override
    public boolean insertar(Cliente obj) {
        resp = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "INSERT INTO clientes (tipo_documento, numero_documento, razon_social, direccion, telefono, email, activo) VALUES (?,?,?,?,?,?,?)"
            );
            ps.setString(1, obj.getTipo_documento());
            ps.setString(2, obj.getNumero_documento());
            ps.setString(3, obj.getRazon_social());
            ps.setString(4, obj.getDireccion());
            ps.setString(5, obj.getTelefono());
            ps.setString(6, obj.getEmail());
            ps.setBoolean(7, obj.isActivo());
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar cliente: " + e.getMessage());
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return resp;
    }

    @Override
    public boolean actualizar(Cliente obj) {
        resp = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "UPDATE clientes SET tipo_documento=?, numero_documento=?, razon_social=?, direccion=?, telefono=?, email=?, activo=? WHERE id_cliente=?"
            );
            ps.setString(1, obj.getTipo_documento());
            ps.setString(2, obj.getNumero_documento());
            ps.setString(3, obj.getRazon_social());
            ps.setString(4, obj.getDireccion());
            ps.setString(5, obj.getTelefono());
            ps.setString(6, obj.getEmail());
            ps.setBoolean(7, obj.isActivo());
            ps.setInt(8, obj.getId_cliente());
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar cliente: " + e.getMessage());
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
                "UPDATE clientes SET activo=0 WHERE id_cliente=?"
            );
            ps.setInt(1, id);
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al desactivar cliente: " + e.getMessage());
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
                "UPDATE clientes SET activo=1 WHERE id_cliente=?"
            );
            ps.setInt(1, id);
            
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al activar cliente: " + e.getMessage());
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
                "SELECT COUNT(id_cliente) FROM clientes"
            );
            rs = ps.executeQuery();
            
            if (rs.next()) {
                totalRegistros = rs.getInt(1);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al contar clientes: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return totalRegistros;
    }

    @Override
    public boolean existe(String numeroDocumento) {
        resp = false;
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT id_cliente FROM clientes WHERE numero_documento=?"
            );
            ps.setString(1, numeroDocumento);
            rs = ps.executeQuery();
            resp = rs.next();
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar cliente: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return resp;
    }

    @Override
    public Cliente buscarPorDocumento(String numeroDocumento) {
        Cliente cliente = null;
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT * FROM clientes WHERE numero_documento=? AND activo=1"
            );
            ps.setString(1, numeroDocumento);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId_cliente(rs.getInt("id_cliente"));
                cliente.setTipo_documento(rs.getString("tipo_documento"));
                cliente.setNumero_documento(rs.getString("numero_documento"));
                cliente.setRazon_social(rs.getString("razon_social"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
                cliente.setActivo(rs.getBoolean("activo"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar cliente: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return cliente;
    }

    /**
     * Método adicional para seleccionar clientes activos (útil para ComboBox)
     * @return Lista de clientes activos
     */
    public List<Cliente> seleccionar() {
        List<Cliente> lista = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement(
                "SELECT id_cliente, numero_documento, razon_social FROM clientes WHERE activo=1 ORDER BY razon_social ASC"
            );
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId_cliente(rs.getInt("id_cliente"));
                cliente.setNumero_documento(rs.getString("numero_documento"));
                cliente.setRazon_social(rs.getString("razon_social"));
                lista.add(cliente);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar clientes: " + e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return lista;
    }
}
