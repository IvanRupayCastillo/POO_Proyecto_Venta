package proyecto_sistema_venta.Datos.Interfaces;

import proyecto_sistema_venta.Entidades.TipoProducto;
import java.util.List;

/**
 * Interfaz ITipoProducto - Define las operaciones CRUD para la entidad TipoProducto
 */
public interface ITipoProducto {
    
    /**
     * Lista todos los tipos de producto que coincidan con el texto de búsqueda
     * @param texto Texto para filtrar por nombre o código de tipo
     * @return Lista de tipos de producto encontrados
     */
    public List<TipoProducto> listar(String texto);
    
    /**
     * Inserta un nuevo tipo de producto en la base de datos
     * @param obj TipoProducto a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insertar(TipoProducto obj);
    
    /**
     * Actualiza un tipo de producto existente en la base de datos
     * @param obj TipoProducto con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(TipoProducto obj);
    
    /**
     * Desactiva un tipo de producto (soft delete)
     * @param id ID del tipo de producto a desactivar
     * @return true si se desactivó correctamente, false en caso contrario
     */
    public boolean desactivar(int id);
    
    /**
     * Activa un tipo de producto previamente desactivado
     * @param id ID del tipo de producto a activar
     * @return true si se activó correctamente, false en caso contrario
     */
    public boolean activar(int id);
    
    /**
     * Obtiene el total de tipos de producto registrados
     * @return Número total de tipos de producto
     */
    public int total();
    
    /**
     * Verifica si existe un tipo de producto con el código especificado
     * @param texto Código del tipo de producto a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existe(String texto);
}
