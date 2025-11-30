package proyecto_sistema_venta.Entidades;

import java.sql.Timestamp;

/**
 * Entidad Color - Representa un color en el sistema
 * Mapea a la tabla 'colores' en la base de datos
 */
public class Color {
    
    // Atributos
    private int idColor;
    private String codigoColor;
    private String nombreColor;
    private boolean activo;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructor vacío
    public Color() {
    }

    // Constructor sin timestamps (para inserción)
    public Color(int idColor, String codigoColor, String nombreColor, boolean activo) {
        this.idColor = idColor;
        this.codigoColor = codigoColor;
        this.nombreColor = nombreColor;
        this.activo = activo;
    }

    // Constructor completo con timestamps
    public Color(int idColor, String codigoColor, String nombreColor, boolean activo, 
                Timestamp createdAt, Timestamp updatedAt) {
        this.idColor = idColor;
        this.codigoColor = codigoColor;
        this.nombreColor = nombreColor;
        this.activo = activo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y Setters
    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public String getCodigoColor() {
        return codigoColor;
    }

    public void setCodigoColor(String codigoColor) {
        this.codigoColor = codigoColor;
    }

    public String getNombreColor() {
        return nombreColor;
    }

    public void setNombreColor(String nombreColor) {
        this.nombreColor = nombreColor;
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
        return nombreColor;
    }
}
