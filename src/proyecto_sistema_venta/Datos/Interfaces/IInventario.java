package proyecto_sistema_venta.Datos.Interfaces;

/**
 * Operaciones sobre el inventario por tienda y producto.
 */
public interface IInventario {
    /** Obtiene el stock disponible para un producto en una tienda. */
    public Integer obtenerStockDisponible(int idTienda, int idProducto);
    /** Disminuye el stock si hay disponibilidad suficiente. */
    public boolean disminuirStock(int idTienda, int idProducto, int cantidad);
}
