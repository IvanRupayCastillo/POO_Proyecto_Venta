package proyecto_sistema_venta.Datos.Interfaces;

import proyecto_sistema_venta.Entidades.Talla;
import java.util.List;

/**
 * Interfaz ITalla - Define las operaciones CRUD para la entidad Talla
 */
public interface ITalla {
    
    /**
     * Lista todas las tallas que coincidan con el texto de búsqueda
     * @param texto Texto para filtrar por nombre de talla
     * @return Lista de tallas encontradas
     */
    public List<Talla> listar(String texto);
    
    /**
     * Inserta una nueva talla en la base de datos
     * @param obj Talla a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insertar(Talla obj);
    
    /**
     * Actualiza una talla existente en la base de datos
     * @param obj Talla con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Talla obj);
    
    /**
     * Desactiva una talla (soft delete)
     * @param id ID de la talla a desactivar
     * @return true si se desactivó correctamente, false en caso contrario
     */
    public boolean desactivar(int id);
    
    /**
     * Activa una talla previamente desactivada
     * @param id ID de la talla a activar
     * @return true si se activó correctamente, false en caso contrario
     */
    public boolean activar(int id);
    
    /**
     * Obtiene el total de tallas registradas
     * @return Número total de tallas
     */
    public int total();
    
    /**
     * Verifica si existe una talla con el nombre especificado
     * @param texto Nombre de la talla a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existe(String texto);
}
