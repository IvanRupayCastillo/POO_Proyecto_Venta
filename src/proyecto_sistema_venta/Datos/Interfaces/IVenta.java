package proyecto_sistema_venta.Datos.Interfaces;

import java.util.List;
import proyecto_sistema_venta.Entidades.Venta;

/**
 * Operaciones de acceso a datos para comprobantes de venta.
 */
public interface IVenta {
    /** Inserta una venta y devuelve su ID generado. */
    public int insertar(Venta venta);
    /** Busca una venta por ID. */
    public Venta buscarPorId(int idVenta);
    /** Lista ventas de una tienda. */
    public List<Venta> listarPorTienda(int idTienda);
}
