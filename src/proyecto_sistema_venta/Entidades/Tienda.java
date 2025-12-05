package proyecto_sistema_venta.Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa una tienda con datos básicos y un inventario local de productos.
 */
public class Tienda implements Serializable {

    private int idTienda;
    private String codigoTienda;
    private String nombreTienda;
    private String direccion;
    private String telefono;
    private final List<Producto> inventario;

    /**
     * Crea una tienda vacía con inventario inicial vacío.
     */
    public Tienda() {
        this.inventario = new ArrayList<>();
    }

    /**
     * Crea una tienda con datos básicos.
     */
    public Tienda(int idTienda, String codigoTienda, String nombreTienda, String direccion, String telefono) {
        this.idTienda = idTienda;
        this.codigoTienda = codigoTienda;
        this.nombreTienda = nombreTienda;
        this.direccion = direccion;
        this.telefono = telefono;
        this.inventario = new ArrayList<>();
    }

    public int getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    public String getCodigoTienda() {
        return codigoTienda;
    }

    public void setCodigoTienda(String codigoTienda) {
        this.codigoTienda = codigoTienda;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve una vista inmutable del inventario local.
     */
    public List<Producto> getInventario() {
        return Collections.unmodifiableList(inventario);
    }

    /**
     * Agrega un producto al inventario local.
     */
    public void agregarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("Producto no puede ser nulo");
        }
        inventario.add(producto);
    }

    /**
     * Actualiza los datos de un producto del inventario local por su ID.
     */
    public boolean actualizarProducto(Producto actualizado) {
        if (actualizado == null) {
            return false;
        }
        for (int i = 0; i < inventario.size(); i++) {
            if (inventario.get(i).getIdProducto() == actualizado.getIdProducto()) {
                inventario.set(i, actualizado);
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina un producto del inventario local por su ID.
     */
    public boolean eliminarProducto(int idProducto) {
        return inventario.removeIf(p -> p.getIdProducto() == idProducto);
    }

    /**
     * Busca un producto en el inventario local por su código.
     */
    public Producto buscarPorCodigo(String codigo) {
        for (Producto p : inventario) {
            if (p.getCodigoProducto() != null && p.getCodigoProducto().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Tienda{" +
                "idTienda=" + idTienda +
                ", codigoTienda='" + codigoTienda + '\'' +
                ", nombreTienda='" + nombreTienda + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", inventarioItems=" + inventario.size() +
                '}';
    }
}

