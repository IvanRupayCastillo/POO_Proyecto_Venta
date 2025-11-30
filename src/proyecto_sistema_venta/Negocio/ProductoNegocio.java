
package proyecto_sistema_venta.Negocio;


import proyecto_sistema_venta.Datos.ProductoDAO;
import proyecto_sistema_venta.Entidades.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;




public class ProductoNegocio {
    
    private final ProductoDAO DATOS;
    private Producto productoSeleccionado;
    private DefaultTableModel dtm;
    private int totalRegistros;
    private int totalMostrados;
    
    public ProductoNegocio() {
        this.DATOS = new ProductoDAO();
        this.productoSeleccionado = null;
        this.totalRegistros = 0;
        this.totalMostrados = 0;
    }
    
    // Método para listar productos en un DefaultTableModel para JTable
    public DefaultTableModel listar(String texto) {
        List<Producto> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));
        
        String[] columnas = {
            "ID", "Código", "Nombre", "Descripción", 
            "Tipo", "Color", "Talla", "P. Compra", 
            "P. Venta Mayor", "P. Venta Menor", 
            "Stock Mínimo", "Estado"
        };
        
        this.dtm = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer las celdas no editables
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Definir tipos de datos para ordenamiento correcto
                switch(columnIndex) {
                    case 0: // ID
                    case 4: // Tipo
                    case 5: // Color
                    case 6: // Talla
                    case 10: // Stock Mínimo
                        return Integer.class;
                    case 7: // P. Compra
                    case 8: // P. Venta Mayor
                    case 9: // P. Venta Menor
                        return Double.class;
                    default:
                        return String.class;
                }
            }
        };
        
        String estado;
        Object[] registro = new Object[12];
        
        this.totalRegistros = 0;
        for (Producto item : lista) {
            if (item.isActivo()) {
                estado = "Activo";
            } else {
                estado = "Inactivo";
            }
            
            registro[0] = item.getIdProducto();
            registro[1] = item.getCodigoProducto();
            registro[2] = item.getNombreProducto();
            registro[3] = item.getDescripcion();
            registro[4] = item.getIdTipo();
            registro[5] = item.getIdColor();
            registro[6] = item.getIdTalla();
            registro[7] = item.getPrecioCompra();
            registro[8] = item.getPrecioVentaMayor();
            registro[9] = item.getPrecioVentaMenor();
            registro[10] = item.getStockMinimo();
            registro[11] = estado;
            
            this.dtm.addRow(registro);
            this.totalRegistros++;
        }
        this.totalMostrados = this.totalRegistros;
        return this.dtm;
    }
    
    // Método para insertar un producto con validaciones
    public String insertar(String codigoProducto, String nombreProducto, String descripcion,
                          int idTipo, int idColor, int idTalla, double precioCompra,
                          double precioVentaMayor, double precioVentaMenor, 
                          int stockMinimo, boolean activo) {
        
        // Validaciones
        if (codigoProducto.isEmpty() || codigoProducto.length() > 20) {
            return "El código del producto es obligatorio y no debe superar los 20 caracteres";
        }
        
        if (nombreProducto.isEmpty() || nombreProducto.length() > 100) {
            return "El nombre del producto es obligatorio y no debe superar los 100 caracteres";
        }
        
        if (precioCompra < 0) {
            return "El precio de compra no puede ser negativo";
        }
        
        if (precioVentaMayor < precioCompra) {
            return "El precio de venta al por mayor no puede ser menor al precio de compra";
        }
        
        if (precioVentaMenor < precioCompra) {
            return "El precio de venta al por menor no puede ser menor al precio de compra";
        }
        
        if (stockMinimo < 0) {
            return "El stock mínimo no puede ser negativo";
        }
        
        // Verificar si el código ya existe
        if (DATOS.existeCodigo(codigoProducto)) {
            return "El código del producto ya existe en la base de datos";
        }
        
        // Crear objeto Producto
        Producto producto = new Producto();
        producto.setCodigoProducto(codigoProducto.toUpperCase());
        producto.setNombreProducto(nombreProducto);
        producto.setDescripcion(descripcion);
        producto.setIdTipo(idTipo);
        producto.setIdColor(idColor);
        producto.setIdTalla(idTalla);
        producto.setPrecioCompra(precioCompra);
        producto.setPrecioVentaMayor(precioVentaMayor);
        producto.setPrecioVentaMenor(precioVentaMenor);
        producto.setStockMinimo(stockMinimo);
        producto.setActivo(activo);
        
        // Insertar en la base de datos
        if (DATOS.insertar(producto)) {
            return "OK";
        } else {
            return "Error al insertar el producto";
        }
    }
    
    // Método para actualizar un producto
    public String actualizar(int idProducto, String codigoProducto, String nombreProducto, 
                            String descripcion, int idTipo, int idColor, int idTalla, 
                            double precioCompra, double precioVentaMayor, 
                            double precioVentaMenor, int stockMinimo, boolean activo,
                            String codigoAnterior) {
        
        // Validaciones
        if (codigoProducto.isEmpty() || codigoProducto.length() > 20) {
            return "El código del producto es obligatorio y no debe superar los 20 caracteres";
        }
        
        if (nombreProducto.isEmpty() || nombreProducto.length() > 100) {
            return "El nombre del producto es obligatorio y no debe superar los 100 caracteres";
        }
        
        if (precioCompra < 0) {
            return "El precio de compra no puede ser negativo";
        }
        
        if (precioVentaMayor < precioCompra) {
            return "El precio de venta al por mayor no puede ser menor al precio de compra";
        }
        
        if (precioVentaMenor < precioCompra) {
            return "El precio de venta al por menor no puede ser menor al precio de compra";
        }
        
        if (stockMinimo < 0) {
            return "El stock mínimo no puede ser negativo";
        }
        
        // Verificar si el código ya existe (si se cambió)
        if (!codigoProducto.equalsIgnoreCase(codigoAnterior)) {
            if (DATOS.existeCodigo(codigoProducto)) {
                return "El código del producto ya existe en la base de datos";
            }
        }
        
        // Crear objeto Producto
        Producto producto = new Producto();
        producto.setIdProducto(idProducto);
        producto.setCodigoProducto(codigoProducto.toUpperCase());
        producto.setNombreProducto(nombreProducto);
        producto.setDescripcion(descripcion);
        producto.setIdTipo(idTipo);
        producto.setIdColor(idColor);
        producto.setIdTalla(idTalla);
        producto.setPrecioCompra(precioCompra);
        producto.setPrecioVentaMayor(precioVentaMayor);
        producto.setPrecioVentaMenor(precioVentaMenor);
        producto.setStockMinimo(stockMinimo);
        producto.setActivo(activo);
        
        // Actualizar en la base de datos
        if (DATOS.actualizar(producto)) {
            return "OK";
        } else {
            return "Error al actualizar el producto";
        }
    }
    
    // Método para eliminar un producto (eliminación física)
    public String eliminar(int idProducto) {
        if (DATOS.eliminar(idProducto)) {
            return "OK";
        } else {
            return "Error al eliminar el producto";
        }
    }
    
    // Método para desactivar un producto (eliminación lógica)
    public String desactivar(int idProducto) {
        if (DATOS.desactivar(idProducto)) {
            return "OK";
        } else {
            return "Error al desactivar el producto";
        }
    }
    
    // Método para activar un producto
    public String activar(int idProducto) {
        if (DATOS.activar(idProducto)) {
            return "OK";
        } else {
            return "Error al activar el producto";
        }
    }
    
    // Método para buscar un producto por ID
    public Producto buscarPorId(int idProducto) {
        productoSeleccionado = DATOS.buscarPorId(idProducto);
        return productoSeleccionado;
    }
    
    // Método para buscar productos por descripción
    public DefaultTableModel buscarPorDescripcion(String descripcion) {
        List<Producto> lista = DATOS.buscarPorDescripcion(descripcion);
        
        String[] columnas = {
            "ID", "Código", "Nombre", "Descripción", 
            "Tipo", "Color", "Talla", "P. Compra", 
            "P. Venta Mayor", "P. Venta Menor", 
            "Stock Mínimo", "Estado"
        };
        
        this.dtm = new DefaultTableModel(null, columnas);
        String estado;
        Object[] registro = new Object[12];
        
        this.totalRegistros = 0;
        for (Producto item : lista) {
            if (item.isActivo()) {
                estado = "Activo";
            } else {
                estado = "Inactivo";
            }
            
            registro[0] = item.getIdProducto();
            registro[1] = item.getCodigoProducto();
            registro[2] = item.getNombreProducto();
            registro[3] = item.getDescripcion();
            registro[4] = item.getIdTipo();
            registro[5] = item.getIdColor();
            registro[6] = item.getIdTalla();
            registro[7] = item.getPrecioCompra();
            registro[8] = item.getPrecioVentaMayor();
            registro[9] = item.getPrecioVentaMenor();
            registro[10] = item.getStockMinimo();
            registro[11] = estado;
            
            this.dtm.addRow(registro);
            this.totalRegistros++;
        }
        this.totalMostrados = this.totalRegistros;
        return this.dtm;
    }
    
    // Método para buscar un producto por código
    public Producto buscarPorCodigo(String codigoProducto) {
        productoSeleccionado = DATOS.buscarPorCodigo(codigoProducto);
        return productoSeleccionado;
    }
    
    // Método para verificar si existe un código
    public boolean existeCodigo(String codigoProducto) {
        return DATOS.existeCodigo(codigoProducto);
    }
    
    // Método para obtener el total de productos
    public int total() {
        return DATOS.total();
    }
    
    // Getters para información adicional
    public int getTotalRegistros() {
        return totalRegistros;
    }
    
    public int getTotalMostrados() {
        return totalMostrados;
    }
    
    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }
    
    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }
}










