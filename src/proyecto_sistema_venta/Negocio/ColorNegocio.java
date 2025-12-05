package proyecto_sistema_venta.Negocio;

import proyecto_sistema_venta.Datos.ColorDAO;
import proyecto_sistema_venta.Entidades.Color;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * ColorNegocio - Capa de lógica de negocio para Color
 * Maneja las reglas de negocio y validaciones
 */
public class ColorNegocio {
    
    private final ColorDAO DATOS;
    private DefaultTableModel dtm;

    public ColorNegocio() {
        this.DATOS = new ColorDAO();
    }

    /**
     * Lista los colores y los convierte a formato de tabla
     * @param texto Texto para filtrar
     * @return DefaultTableModel para mostrar en JTable
     */
    public DefaultTableModel listar(String texto) {
        List<Color> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));
        
        String[] titulos = {"ID", "Código", "Nombre", "Estado"};
        this.dtm = new DefaultTableModel(null, titulos);
        
        String[] registro = new String[4];
        
        for (Color item : lista) {
            registro[0] = Integer.toString(item.getIdColor());
            registro[1] = item.getCodigoColor();
            registro[2] = item.getNombreColor();
            registro[3] = item.isActivo() ? "Activo" : "Inactivo";
            this.dtm.addRow(registro);
        }
        return this.dtm;
    }
    
    /**
     * Lista los colores por tienda y los convierte a formato de tabla
     * @param texto Texto para filtrar
     * @param idTienda ID de la tienda
     * @return DefaultTableModel para mostrar en JTable
     */
    public DefaultTableModel listarPorTienda(String texto, int idTienda) {
        List<Color> lista = new ArrayList<>();
        lista.addAll(DATOS.listarPorTienda(texto, idTienda));
        
        String[] titulos = {"ID", "Código", "Nombre", "Estado"};
        this.dtm = new DefaultTableModel(null, titulos);
        
        String[] registro = new String[4];
        
        for (Color item : lista) {
            registro[0] = Integer.toString(item.getIdColor());
            registro[1] = item.getCodigoColor();
            registro[2] = item.getNombreColor();
            registro[3] = item.isActivo() ? "Activo" : "Inactivo";
            this.dtm.addRow(registro);
        }
        return this.dtm;
    }

    /**
     * Inserta un nuevo color con validaciones
     * @param codigo Código del color
     * @param nombre Nombre del color
     * @param idTienda ID de la tienda
     * @return Mensaje de resultado
     */
    public String insertar(String codigo, String nombre, Integer idTienda) {
        // Validaciones
        if (codigo.trim().length() == 0 || codigo.trim().length() > 20) {
            return "El código es obligatorio y debe tener máximo 20 caracteres";
        }
        
        if (nombre.trim().length() == 0 || nombre.trim().length() > 50) {
            return "El nombre es obligatorio y debe tener máximo 50 caracteres";
        }
        
        if (DATOS.existe(codigo)) {
            return "El código de color ya existe en el sistema";
        }
        
        Color obj = new Color();
        obj.setCodigoColor(codigo.trim().toUpperCase());
        obj.setNombreColor(nombre.trim());
        obj.setIdTienda(idTienda);
        obj.setActivo(true);
        
        if (DATOS.insertar(obj)) {
            return "OK";
        } else {
            return "Error en la inserción del color";
        }
    }

    /**
     * Actualiza un color existente
     * @param id ID del color
     * @param codigo Código del color
     * @param nombre Nombre del color
     * @param idTienda ID de la tienda
     * @param activo Estado del color
     * @return Mensaje de resultado
     */
    public String actualizar(int id, String codigo, String nombre, Integer idTienda, boolean activo) {
        // Validaciones
        if (codigo.trim().length() == 0 || codigo.trim().length() > 20) {
            return "El código es obligatorio y debe tener máximo 20 caracteres";
        }
        
        if (nombre.trim().length() == 0 || nombre.trim().length() > 50) {
            return "El nombre es obligatorio y debe tener máximo 50 caracteres";
        }
        
        Color obj = new Color();
        obj.setIdColor(id);
        obj.setCodigoColor(codigo.trim().toUpperCase());
        obj.setNombreColor(nombre.trim());
        obj.setIdTienda(idTienda);
        obj.setActivo(activo);
        
        if (DATOS.actualizar(obj)) {
            return "OK";
        } else {
            return "Error en la actualización del color";
        }
    }

    /**
     * Desactiva un color
     * @param id ID del color
     * @return Mensaje de resultado
     */
    public String desactivar(int id) {
        if (DATOS.desactivar(id)) {
            return "OK";
        } else {
            return "Error al desactivar el color";
        }
    }

    /**
     * Activa un color
     * @param id ID del color
     * @return Mensaje de resultado
     */
    public String activar(int id) {
        if (DATOS.activar(id)) {
            return "OK";
        } else {
            return "Error al activar el color";
        }
    }

    /**
     * Obtiene el total de colores
     * @return Número total de colores
     */
    public int total() {
        return DATOS.total();
    }

    /**
     * Verifica si existe un código de color
     * @param texto Código a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existe(String texto) {
        return DATOS.existe(texto);
    }
    
    /**
     * Obtiene lista de colores activos para ComboBox
     * @return Lista de colores activos
     */
    public List<Color> seleccionar() {
        return DATOS.seleccionar();
    }
}
