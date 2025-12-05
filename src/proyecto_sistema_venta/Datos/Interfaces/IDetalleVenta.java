package proyecto_sistema_venta.Datos.Interfaces;

import java.util.List;
import proyecto_sistema_venta.Entidades.DetalleVenta;

/**
 * Operaciones sobre los detalles de venta.
 */
public interface IDetalleVenta {
    /** Inserta un lote de detalles para una venta. */
    public boolean insertarLote(int idVenta, List<DetalleVenta> detalles);
    /** Lista los detalles asociados a una venta. */
    public List<DetalleVenta> listarPorVenta(int idVenta);
}
