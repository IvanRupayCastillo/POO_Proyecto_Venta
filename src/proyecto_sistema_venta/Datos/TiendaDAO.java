package proyecto_sistema_venta.Datos;

import proyecto_sistema_venta.Conexion.Conexion;
import proyecto_sistema_venta.Datos.Interfaces.ITienda;
import proyecto_sistema_venta.Entidades.Tienda;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TiendaDAO implements ITienda {

    private final Conexion CNX;

    public TiendaDAO() {
        this.CNX = Conexion.getInstancia();
    }

    @Override
    public List<Tienda> listar(String texto) {
        List<Tienda> registros = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = CNX.conectar().prepareStatement(
                    "SELECT id_tienda, codigo_tienda, nombre_tienda, direccion, telefono FROM tiendas WHERE nombre_tienda LIKE ? OR codigo_tienda LIKE ? ORDER BY id_tienda DESC"
            );
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Tienda(
                        rs.getInt("id_tienda"),
                        rs.getString("codigo_tienda"),
                        rs.getString("nombre_tienda"),
                        rs.getString("direccion"),
                        rs.getString("telefono")
                ));
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

    @Override
    public boolean insertar(Tienda tienda) {
        boolean ok = false;
        PreparedStatement ps = null;
        try {
            ps = CNX.conectar().prepareStatement(
                    "INSERT INTO tiendas (codigo_tienda, nombre_tienda, direccion, telefono, tipo_tienda, es_punto_venta, activo) VALUES (?, ?, ?, ?, 'TIENDA', 1, 1)"
            );
            ps.setString(1, tienda.getCodigoTienda());
            ps.setString(2, tienda.getNombreTienda());
            ps.setString(3, tienda.getDireccion());
            ps.setString(4, tienda.getTelefono());
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

    @Override
    public boolean actualizar(Tienda tienda) {
        boolean ok = false;
        PreparedStatement ps = null;
        try {
            ps = CNX.conectar().prepareStatement(
                    "UPDATE tiendas SET codigo_tienda = ?, nombre_tienda = ?, direccion = ?, telefono = ? WHERE id_tienda = ?"
            );
            ps.setString(1, tienda.getCodigoTienda());
            ps.setString(2, tienda.getNombreTienda());
            ps.setString(3, tienda.getDireccion());
            ps.setString(4, tienda.getTelefono());
            ps.setInt(5, tienda.getIdTienda());
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

    @Override
    public boolean eliminar(int idTienda) {
        boolean ok = false;
        PreparedStatement ps = null;
        try {
            ps = CNX.conectar().prepareStatement(
                    "DELETE FROM tiendas WHERE id_tienda = ?"
            );
            ps.setInt(1, idTienda);
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

    @Override
    public Tienda buscarPorId(int idTienda) {
        Tienda tienda = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = CNX.conectar().prepareStatement(
                    "SELECT id_tienda, codigo_tienda, nombre_tienda, direccion, telefono FROM tiendas WHERE id_tienda = ?"
            );
            ps.setInt(1, idTienda);
            rs = ps.executeQuery();
            if (rs.next()) {
                tienda = new Tienda(
                        rs.getInt("id_tienda"),
                        rs.getString("codigo_tienda"),
                        rs.getString("nombre_tienda"),
                        rs.getString("direccion"),
                        rs.getString("telefono")
                );
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return tienda;
    }

    @Override
    public boolean existeCodigo(String codigoTienda) {
        boolean existe = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = CNX.conectar().prepareStatement(
                    "SELECT codigo_tienda FROM tiendas WHERE codigo_tienda = ?"
            );
            ps.setString(1, codigoTienda);
            rs = ps.executeQuery();
            existe = rs.next();
            ps.close();
            rs.close();
        } catch (SQLException e) {
            existe = false;
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return existe;
    }
}

