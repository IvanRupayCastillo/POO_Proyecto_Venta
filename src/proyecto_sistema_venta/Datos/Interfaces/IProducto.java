
package proyecto_sistema_venta.Datos.Interfaces;

    import proyecto_sistema_venta.Entidades.Producto;
	import java.util.List;
	
public interface IProducto {
	// Método para listar productos con búsqueda por texto
    public List<Producto> listar(String texto);
    
    // Método para insertar un nuevo producto
    public boolean insertar(Producto producto);
    
    // Método para actualizar un producto existente
    public boolean actualizar(Producto producto);
    
    // Método para eliminar un producto
    public boolean eliminar(int idProducto);
    
    // Método para desactivar un producto (eliminación lógica)
    public boolean desactivar(int idProducto);
    
    // Método para activar un producto
    public boolean activar(int idProducto);
    
    // Método para buscar un producto por ID
    public Producto buscarPorId(int idProducto);
    
    // Método para buscar productos por descripción
    public List<Producto> buscarPorDescripcion(String descripcion);
    
    // Método para buscar un producto por código
    public Producto buscarPorCodigo(String codigoProducto);
    
    // Método para verificar si existe un código de producto
    public boolean existeCodigo(String codigoProducto);
    
    // Método para obtener el total de registros
    public int total();
}
