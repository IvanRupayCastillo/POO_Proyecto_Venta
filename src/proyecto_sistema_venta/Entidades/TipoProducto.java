package proyecto_sistema_venta.Entidades;

import java.sql.Timestamp;

/**
 * Entidad TipoProducto - Representa un tipo de producto en el sistema
 * Mapea a la tabla 'tipos_producto' en la base de datos
 */
public class TipoProducto {
    
    // Atributos
    private int idTipo;
    private String codigoTipo;
    private String nombreTipo;
    private String descripcion;
    private boolean activo;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructor vacío
    public TipoProducto() {
    }

    // Constructor sin timestamps (para inserción)
    public TipoProducto(int idTipo, String codigoTipo, String nombreTipo, String descripcion, boolean activo) {
        this.idTipo = idTipo;
        this.codigoTipo = codigoTipo;
        this.nombreTipo = nombreTipo;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    // Constructor completo con timestamps
    public TipoProducto(int idTipo, String codigoTipo, String nombreTipo, String descripcion, 
                       boolean activo, Timestamp createdAt, Timestamp updatedAt) {
        this.idTipo = idTipo;
        this.codigoTipo = codigoTipo;
        this.nombreTipo = nombreTipo;
        this.descripcion = descripcion;
        this.activo = activo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y Setters
    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getCodigoTipo() {
        return codigoTipo;
    }

    public void setCodigoTipo(String codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return nombreTipo;
    }
}
