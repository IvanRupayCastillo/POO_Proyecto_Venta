package proyecto_sistema_venta.Negocio;

import proyecto_sistema_venta.Datos.TiendaDAO;
import proyecto_sistema_venta.Entidades.Tienda;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * TiendaNegocio - Capa de lógica de negocio para Tienda
 * Maneja las reglas de negocio y validaciones
 */
public class TiendaNegocio {
    
    private final TiendaDAO DATOS;
    private DefaultTableModel dtm;

    public TiendaNegocio() {
        this.DATOS = new TiendaDAO();
    }

    /**
     * Lista las tiendas y las convierte a formato de tabla
     * @param texto Texto para filtrar
     * @return DefaultTableModel para mostrar en JTable
     */
    public DefaultTableModel listar(String texto) {
        List<Tienda> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));
        
        String[] titulos = {"ID", "Código", "Nombre", "Dirección", "Teléfono", "Estado"};
        this.dtm = new DefaultTableModel(null, titulos);
        
        String[] registro = new String[6];
        
        for (Tienda item : lista) {
            registro[0] = Integer.toString(item.getIdTienda());
            registro[1] = item.getCodigoTienda();
            registro[2] = item.getNombreTienda();
            registro[3] = item.getDireccion();
            registro[4] = item.getTelefono();
            registro[5] = "Activo"; // Por defecto, ya que TiendaDAO no retorna el campo activo
            this.dtm.addRow(registro);
        }
        return this.dtm;
    }

    /**
     * Inserta una nueva tienda con validaciones
     * @param codigo Código de la tienda
     * @param nombre Nombre de la tienda
     * @param direccion Dirección de la tienda
     * @param telefono Teléfono de la tienda
     * @return Mensaje de resultado
     */
    public String insertar(String codigo, String nombre, String direccion, String telefono) {
        // Validaciones
        if (codigo.trim().length() == 0 || codigo.trim().length() > 20) {
            return "El código es obligatorio y debe tener máximo 20 caracteres";
        }
        
        if (nombre.trim().length() == 0 || nombre.trim().length() > 100) {
            return "El nombre es obligatorio y debe tener máximo 100 caracteres";
        }
        
        if (direccion.trim().length() > 200) {
            return "La dirección debe tener máximo 200 caracteres";
        }
        
        if (telefono.trim().length() > 20) {
            return "El teléfono debe tener máximo 20 caracteres";
        }
        
        if (DATOS.existeCodigo(codigo)) {
            return "El código de tienda ya existe en el sistema";
        }
        
        Tienda obj = new Tienda();
        obj.setCodigoTienda(codigo.trim().toUpperCase());
        obj.setNombreTienda(nombre.trim());
        obj.setDireccion(direccion.trim());
        obj.setTelefono(telefono.trim());
        
        if (DATOS.insertar(obj)) {
            return "OK";
        } else {
            return "Error en la inserción de la tienda";
        }
    }

    /**
     * Actualiza una tienda existente
     * @param id ID de la tienda
     * @param codigo Código de la tienda
     * @param nombre Nombre de la tienda
     * @param direccion Dirección de la tienda
     * @param telefono Teléfono de la tienda
     * @param activo Estado de la tienda
     * @return Mensaje de resultado
     */
    public String actualizar(int id, String codigo, String nombre, String direccion, String telefono, boolean activo) {
        // Validaciones
        if (codigo.trim().length() == 0 || codigo.trim().length() > 20) {
            return "El código es obligatorio y debe tener máximo 20 caracteres";
        }
        
        if (nombre.trim().length() == 0 || nombre.trim().length() > 100) {
            return "El nombre es obligatorio y debe tener máximo 100 caracteres";
        }
        
        if (direccion.trim().length() > 200) {
            return "La dirección debe tener máximo 200 caracteres";
        }
        
        if (telefono.trim().length() > 20) {
            return "El teléfono debe tener máximo 20 caracteres";
        }
        
        Tienda obj = new Tienda();
        obj.setIdTienda(id);
        obj.setCodigoTienda(codigo.trim().toUpperCase());
        obj.setNombreTienda(nombre.trim());
        obj.setDireccion(direccion.trim());
        obj.setTelefono(telefono.trim());
        
        if (DATOS.actualizar(obj)) {
            return "OK";
        } else {
            return "Error en la actualización de la tienda";
        }
    }

    /**
     * Desactiva una tienda
     * @param id ID de la tienda
     * @return Mensaje de resultado
     */
    public String desactivar(int id) {
        // Nota: TiendaDAO no tiene método desactivar, se usa eliminar
        if (DATOS.eliminar(id)) {
            return "OK";
        } else {
            return "Error al desactivar la tienda";
        }
    }

    /**
     * Activa una tienda
     * @param id ID de la tienda
     * @return Mensaje de resultado
     */
    public String activar(int id) {
        // Nota: TiendaDAO no tiene método activar
        // Por ahora retornamos OK ya que no hay lógica de activación en el DAO
        return "La funcionalidad de activar no está implementada en el DAO";
    }

    /**
     * Obtiene el total de tiendas
     * @return Número total de tiendas
     */
    public int total() {
        return DATOS.listar("").size();
    }

    /**
     * Verifica si existe un código de tienda
     * @param codigo Código a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existeCodigo(String codigo) {
        return DATOS.existeCodigo(codigo);
    }
}
