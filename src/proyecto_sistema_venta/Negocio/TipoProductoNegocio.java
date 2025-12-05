package proyecto_sistema_venta.Negocio;

import proyecto_sistema_venta.Datos.TipoProductoDAO;
import proyecto_sistema_venta.Entidades.TipoProducto;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * TipoProductoNegocio - Capa de lógica de negocio para TipoProducto
 * Maneja las reglas de negocio y validaciones
 */
public class TipoProductoNegocio {
    
    private final TipoProductoDAO DATOS;
    private DefaultTableModel dtm;

    public TipoProductoNegocio() {
        this.DATOS = new TipoProductoDAO();
    }

    /**
     * Lista los tipos de producto y los convierte a formato de tabla
     * @param texto Texto para filtrar
     * @return DefaultTableModel para mostrar en JTable
     */
    public DefaultTableModel listar(String texto) {
        List<TipoProducto> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));
        
        String[] titulos = {"ID", "Código", "Nombre", "Descripción", "Estado"};
        this.dtm = new DefaultTableModel(null, titulos);
        
        String[] registro = new String[5];
        
        for (TipoProducto item : lista) {
            registro[0] = Integer.toString(item.getIdTipo());
            registro[1] = item.getCodigoTipo();
            registro[2] = item.getNombreTipo();
            registro[3] = item.getDescripcion();
            registro[4] = item.isActivo() ? "Activo" : "Inactivo";
            this.dtm.addRow(registro);
        }
        return this.dtm;
    }
    
    /**
     * Lista los tipos de producto por tienda y los convierte a formato de tabla
     * @param texto Texto para filtrar
     * @param idTienda ID de la tienda
     * @return DefaultTableModel para mostrar en JTable
     */
    public DefaultTableModel listarPorTienda(String texto, int idTienda) {
        List<TipoProducto> lista = new ArrayList<>();
        lista.addAll(DATOS.listarPorTienda(texto, idTienda));
        
        String[] titulos = {"ID", "Código", "Nombre", "Descripción", "Estado"};
        this.dtm = new DefaultTableModel(null, titulos);
        
        String[] registro = new String[5];
        
        for (TipoProducto item : lista) {
            registro[0] = Integer.toString(item.getIdTipo());
            registro[1] = item.getCodigoTipo();
            registro[2] = item.getNombreTipo();
            registro[3] = item.getDescripcion();
            registro[4] = item.isActivo() ? "Activo" : "Inactivo";
            this.dtm.addRow(registro);
        }
        return this.dtm;
    }

    /**
     * Inserta un nuevo tipo de producto con validaciones
     * @param codigo Código del tipo de producto
     * @param nombre Nombre del tipo de producto
     * @param descripcion Descripción del tipo de producto
     * @param idTienda ID de la tienda
     * @return Mensaje de resultado
     */
    public String insertar(String codigo, String nombre, String descripcion, Integer idTienda) {
        // Validaciones
        if (codigo.trim().length() == 0 || codigo.trim().length() > 20) {
            return "El código es obligatorio y debe tener máximo 20 caracteres";
        }
        
        if (nombre.trim().length() == 0 || nombre.trim().length() > 100) {
            return "El nombre es obligatorio y debe tener máximo 100 caracteres";
        }
        
        if (descripcion.trim().length() > 255) {
            return "La descripción debe tener máximo 255 caracteres";
        }
        
        if (DATOS.existe(codigo)) {
            return "El código de tipo de producto ya existe en el sistema";
        }
        
        TipoProducto obj = new TipoProducto();
        obj.setCodigoTipo(codigo.trim().toUpperCase());
        obj.setNombreTipo(nombre.trim());
        obj.setDescripcion(descripcion.trim());
        obj.setIdTienda(idTienda);
        obj.setActivo(true);
        
        if (DATOS.insertar(obj)) {
            return "OK";
        } else {
            return "Error en la inserción del tipo de producto";
        }
    }

    /**
     * Actualiza un tipo de producto existente
     * @param id ID del tipo de producto
     * @param codigo Código del tipo de producto
     * @param nombre Nombre del tipo de producto
     * @param descripcion Descripción del tipo de producto
     * @param idTienda ID de la tienda
     * @param activo Estado del tipo de producto
     * @return Mensaje de resultado
     */
    public String actualizar(int id, String codigo, String nombre, String descripcion, Integer idTienda, boolean activo) {
        // Validaciones
        if (codigo.trim().length() == 0 || codigo.trim().length() > 20) {
            return "El código es obligatorio y debe tener máximo 20 caracteres";
        }
        
        if (nombre.trim().length() == 0 || nombre.trim().length() > 100) {
            return "El nombre es obligatorio y debe tener máximo 100 caracteres";
        }
        
        if (descripcion.trim().length() > 255) {
            return "La descripción debe tener máximo 255 caracteres";
        }
        
        TipoProducto obj = new TipoProducto();
        obj.setIdTipo(id);
        obj.setCodigoTipo(codigo.trim().toUpperCase());
        obj.setNombreTipo(nombre.trim());
        obj.setDescripcion(descripcion.trim());
        obj.setIdTienda(idTienda);
        obj.setActivo(activo);
        
        if (DATOS.actualizar(obj)) {
            return "OK";
        } else {
            return "Error en la actualización del tipo de producto";
        }
    }

    /**
     * Desactiva un tipo de producto
     * @param id ID del tipo de producto
     * @return Mensaje de resultado
     */
    public String desactivar(int id) {
        if (DATOS.desactivar(id)) {
            return "OK";
        } else {
            return "Error al desactivar el tipo de producto";
        }
    }

    /**
     * Activa un tipo de producto
     * @param id ID del tipo de producto
     * @return Mensaje de resultado
     */
    public String activar(int id) {
        if (DATOS.activar(id)) {
            return "OK";
        } else {
            return "Error al activar el tipo de producto";
        }
    }

    /**
     * Obtiene el total de tipos de producto
     * @return Número total de tipos de producto
     */
    public int total() {
        return DATOS.total();
    }

    /**
     * Verifica si existe un código de tipo de producto
     * @param texto Código a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existe(String texto) {
        return DATOS.existe(texto);
    }
    
    /**
     * Obtiene lista de tipos de producto activos para ComboBox
     * @return Lista de tipos de producto activos
     */
    public List<TipoProducto> seleccionar() {
        return DATOS.seleccionar();
    }
}
