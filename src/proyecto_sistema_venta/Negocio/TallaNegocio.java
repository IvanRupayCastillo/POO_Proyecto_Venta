package proyecto_sistema_venta.Negocio;

import proyecto_sistema_venta.Datos.TallaDAO;
import proyecto_sistema_venta.Entidades.Talla;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * TallaNegocio - Capa de lógica de negocio para Talla
 * Maneja las reglas de negocio y validaciones
 */
public class TallaNegocio {
    
    private final TallaDAO DATOS;
    private DefaultTableModel dtm;

    public TallaNegocio() {
        this.DATOS = new TallaDAO();
    }

    /**
     * Lista las tallas y las convierte a formato de tabla
     * @param texto Texto para filtrar
     * @return DefaultTableModel para mostrar en JTable
     */
    public DefaultTableModel listar(String texto) {
        List<Talla> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));
        
        String[] titulos = {"ID", "Tipo", "Nombre", "Orden", "Estado"};
        this.dtm = new DefaultTableModel(null, titulos);
        
        String[] registro = new String[5];
        
        for (Talla item : lista) {
            registro[0] = Integer.toString(item.getIdTalla());
            registro[1] = item.getTipoTalla();
            registro[2] = item.getNombreTalla();
            registro[3] = Integer.toString(item.getOrdenVisualizacion());
            registro[4] = item.isActivo() ? "Activo" : "Inactivo";
            this.dtm.addRow(registro);
        }
        return this.dtm;
    }

    /**
     * Inserta una nueva talla con validaciones
     * @param tipoTalla Tipo de talla (NUMERICA, LETRAS, ESPECIAL)
     * @param nombreTalla Nombre de la talla
     * @param ordenVisualizacion Orden de visualización
     * @return Mensaje de resultado
     */
    public String insertar(String tipoTalla, String nombreTalla, int ordenVisualizacion) {
        // Validaciones
        if (tipoTalla == null || tipoTalla.trim().length() == 0) {
            return "Debe seleccionar un tipo de talla";
        }
        
        if (!tipoTalla.equals("NUMERICA") && !tipoTalla.equals("LETRAS") && !tipoTalla.equals("ESPECIAL")) {
            return "El tipo de talla debe ser: NUMERICA, LETRAS o ESPECIAL";
        }
        
        if (nombreTalla.trim().length() == 0 || nombreTalla.trim().length() > 20) {
            return "El nombre es obligatorio y debe tener máximo 20 caracteres";
        }
        
        if (ordenVisualizacion < 0 || ordenVisualizacion > 999) {
            return "El orden de visualización debe estar entre 0 y 999";
        }
        
        if (DATOS.existe(nombreTalla.trim())) {
            return "El nombre de talla ya existe en el sistema";
        }
        
        Talla obj = new Talla();
        obj.setTipoTalla(tipoTalla);
        obj.setNombreTalla(nombreTalla.trim().toUpperCase());
        obj.setOrdenVisualizacion(ordenVisualizacion);
        obj.setActivo(true);
        
        if (DATOS.insertar(obj)) {
            return "OK";
        } else {
            return "Error en la inserción de la talla";
        }
    }

    /**
     * Actualiza una talla existente
     * @param id ID de la talla
     * @param tipoTalla Tipo de talla
     * @param nombreTalla Nombre de la talla
     * @param ordenVisualizacion Orden de visualización
     * @param activo Estado de la talla
     * @param nombreAnt Nombre anterior para validar cambios
     * @return Mensaje de resultado
     */
    public String actualizar(int id, String tipoTalla, String nombreTalla, int ordenVisualizacion, boolean activo, String nombreAnt) {
        // Validaciones
        if (tipoTalla == null || tipoTalla.trim().length() == 0) {
            return "Debe seleccionar un tipo de talla";
        }

        if (!tipoTalla.equals("NUMERICA") && !tipoTalla.equals("LETRAS") && !tipoTalla.equals("ESPECIAL")) {
            return "El tipo de talla debe ser: NUMERICA, LETRAS o ESPECIAL";
        }

        if (nombreTalla.trim().length() == 0 || nombreTalla.trim().length() > 20) {
            return "El nombre es obligatorio y debe tener máximo 20 caracteres";
        }

        if (ordenVisualizacion < 0 || ordenVisualizacion > 999) {
            return "El orden de visualización debe estar entre 0 y 999";
        }

        // Verificar si el nombre cambió y si ya existe
        if (!nombreTalla.trim().toUpperCase().equals(nombreAnt.trim().toUpperCase()) && DATOS.existe(nombreTalla.trim())) {
            return "El nombre de talla ya existe en el sistema";
        }

        Talla obj = new Talla();
        obj.setIdTalla(id);
        obj.setTipoTalla(tipoTalla);
        obj.setNombreTalla(nombreTalla.trim().toUpperCase());
        obj.setOrdenVisualizacion(ordenVisualizacion);
        obj.setActivo(activo);

        if (DATOS.actualizar(obj)) {
            return "OK";
        } else {
            return "Error en la actualización de la talla";
        }
    }

    /**
     * Desactiva una talla
     * @param id ID de la talla
     * @return Mensaje de resultado
     */
    public String desactivar(int id) {
        if (DATOS.desactivar(id)) {
            return "OK";
        } else {
            return "Error al desactivar la talla";
        }
    }

    /**
     * Activa una talla
     * @param id ID de la talla
     * @return Mensaje de resultado
     */
    public String activar(int id) {
        if (DATOS.activar(id)) {
            return "OK";
        } else {
            return "Error al activar la talla";
        }
    }

    /**
     * Obtiene el total de tallas
     * @return Número total de tallas
     */
    public int total() {
        return DATOS.total();
    }

    /**
     * Verifica si existe una talla
     * @param texto Nombre de talla a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existe(String texto) {
        return DATOS.existe(texto);
    }
    
    /**
     * Obtiene lista de tallas activas para ComboBox
     * @return Lista de tallas activas
     */
    public List<Talla> seleccionar() {
        return DATOS.seleccionar();
    }
    
    /**
     * Obtiene lista de tallas por tipo
     * @param tipoTalla Tipo de talla
     * @return Lista de tallas del tipo especificado
     */
    public List<Talla> listarPorTipo(String tipoTalla) {
        return DATOS.listarPorTipo(tipoTalla);
    }
}
