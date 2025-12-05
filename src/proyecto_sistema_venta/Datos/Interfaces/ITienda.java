package proyecto_sistema_venta.Datos.Interfaces;

import java.util.List;
import proyecto_sistema_venta.Entidades.Tienda;

/**
 * Operaciones de acceso a datos para la entidad Tienda.
 */
public interface ITienda {
    /** Lista tiendas filtrando por texto. */
    public List<Tienda> listar(String texto);
    /** Inserta una nueva tienda. */
    public boolean insertar(Tienda tienda);
    /** Actualiza datos de una tienda. */
    public boolean actualizar(Tienda tienda);
    /** Elimina una tienda por ID. */
    public boolean eliminar(int idTienda);
    /** Busca una tienda por ID. */
    public Tienda buscarPorId(int idTienda);
    /** Verifica existencia de código de tienda. */
    public boolean existeCodigo(String codigoTienda);
}
