package proyecto_sistema_venta.Negocio;

import proyecto_sistema_venta.Datos.ClienteDAO;
import proyecto_sistema_venta.Entidades.Cliente;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * ClienteNegocio - Capa de lógica de negocio para Cliente
 * Maneja las reglas de negocio y validaciones
 */
public class ClienteNegocio {
    
    private final ClienteDAO DATOS;
    private DefaultTableModel dtm;

    public ClienteNegocio() {
        this.DATOS = new ClienteDAO();
    }

    /**
     * Lista los clientes y los convierte a formato de tabla
     * @param texto Texto para filtrar
     * @return DefaultTableModel para mostrar en JTable
     */
    public DefaultTableModel listar(String texto) {
        List<Cliente> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));
        
        String[] titulos = {"ID", "Tipo Doc.", "Nro. Documento", "Razón Social", "Dirección", "Teléfono", "Email", "Estado"};
        this.dtm = new DefaultTableModel(null, titulos);
        
        String[] registro = new String[8];
        
        for (Cliente item : lista) {
            registro[0] = Integer.toString(item.getId_cliente());
            registro[1] = item.getTipo_documento();
            registro[2] = item.getNumero_documento();
            registro[3] = item.getRazon_social();
            registro[4] = item.getDireccion() != null ? item.getDireccion() : "";
            registro[5] = item.getTelefono() != null ? item.getTelefono() : "";
            registro[6] = item.getEmail() != null ? item.getEmail() : "";
            registro[7] = item.isActivo() ? "Activo" : "Inactivo";
            this.dtm.addRow(registro);
        }
        return this.dtm;
    }

    /**
     * Inserta un nuevo cliente con validaciones
     */
    public String insertar(String tipoDocumento, String numeroDocumento, String razonSocial, 
                          String direccion, String telefono, String email) {
        // Validaciones
        if (tipoDocumento == null || tipoDocumento.trim().isEmpty()) {
            return "El tipo de documento es obligatorio";
        }
        
        if (numeroDocumento.trim().length() == 0 || numeroDocumento.trim().length() > 20) {
            return "El número de documento es obligatorio y debe tener máximo 20 caracteres";
        }
        
        if (razonSocial.trim().length() == 0 || razonSocial.trim().length() > 200) {
            return "La razón social es obligatoria y debe tener máximo 200 caracteres";
        }
        
        // Validar formato según tipo de documento
        if (tipoDocumento.equals("DNI") && numeroDocumento.trim().length() != 8) {
            return "El DNI debe tener 8 dígitos";
        }
        
        if (tipoDocumento.equals("RUC") && numeroDocumento.trim().length() != 11) {
            return "El RUC debe tener 11 dígitos";
        }
        
        if (DATOS.existe(numeroDocumento)) {
            return "El número de documento ya existe en el sistema";
        }
        
        Cliente obj = new Cliente();
        obj.setTipo_documento(tipoDocumento);
        obj.setNumero_documento(numeroDocumento.trim());
        obj.setRazon_social(razonSocial.trim());
        obj.setDireccion(direccion != null ? direccion.trim() : null);
        obj.setTelefono(telefono != null ? telefono.trim() : null);
        obj.setEmail(email != null ? email.trim() : null);
        obj.setActivo(true);
        
        if (DATOS.insertar(obj)) {
            return "OK";
        } else {
            return "Error en la inserción del cliente";
        }
    }

    /**
     * Actualiza un cliente existente
     */
    public String actualizar(int id, String tipoDocumento, String numeroDocumento, String razonSocial,
                            String direccion, String telefono, String email, boolean activo) {
        // Validaciones
        if (tipoDocumento == null || tipoDocumento.trim().isEmpty()) {
            return "El tipo de documento es obligatorio";
        }
        
        if (numeroDocumento.trim().length() == 0 || numeroDocumento.trim().length() > 20) {
            return "El número de documento es obligatorio y debe tener máximo 20 caracteres";
        }
        
        if (razonSocial.trim().length() == 0 || razonSocial.trim().length() > 200) {
            return "La razón social es obligatoria y debe tener máximo 200 caracteres";
        }
        
        Cliente obj = new Cliente();
        obj.setId_cliente(id);
        obj.setTipo_documento(tipoDocumento);
        obj.setNumero_documento(numeroDocumento.trim());
        obj.setRazon_social(razonSocial.trim());
        obj.setDireccion(direccion != null ? direccion.trim() : null);
        obj.setTelefono(telefono != null ? telefono.trim() : null);
        obj.setEmail(email != null ? email.trim() : null);
        obj.setActivo(activo);
        
        if (DATOS.actualizar(obj)) {
            return "OK";
        } else {
            return "Error en la actualización del cliente";
        }
    }

    /**
     * Desactiva un cliente
     */
    public String desactivar(int id) {
        if (DATOS.desactivar(id)) {
            return "OK";
        } else {
            return "Error al desactivar el cliente";
        }
    }

    /**
     * Activa un cliente
     */
    public String activar(int id) {
        if (DATOS.activar(id)) {
            return "OK";
        } else {
            return "Error al activar el cliente";
        }
    }

    /**
     * Obtiene el total de clientes
     */
    public int total() {
        return DATOS.total();
    }

    /**
     * Verifica si existe un número de documento
     */
    public boolean existe(String numeroDocumento) {
        return DATOS.existe(numeroDocumento);
    }
    
    /**
     * Busca un cliente por número de documento
     */
    public Cliente buscarPorDocumento(String numeroDocumento) {
        return DATOS.buscarPorDocumento(numeroDocumento);
    }
    
    /**
     * Obtiene lista de clientes activos para ComboBox
     */
    public List<Cliente> seleccionar() {
        return DATOS.seleccionar();
    }
    
    /**
     * Lista los tipos de documento disponibles
     */
    public List<String> listarTiposDocumento() {
        List<String> tipos = new ArrayList<>();
        tipos.add("DNI");
        tipos.add("RUC");
        tipos.add("CE");
        tipos.add("PASAPORTE");
        return tipos;
    }
}
