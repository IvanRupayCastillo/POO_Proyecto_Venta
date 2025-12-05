package proyecto_sistema_venta.Entidades;

import java.sql.Timestamp;

/**
 * Entidad Talla - Representa una talla en el sistema
 * Mapea a la tabla 'tallas' en la base de datos
 */
public class Talla {
    
    // Atributos
    private int idTalla;
    private String tipoTalla;  // ENUM: 'NUMERICA', 'LETRAS', 'ESPECIAL'
    private String nombreTalla;
    private int ordenVisualizacion;
    private Integer idTienda;
    private boolean activo;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructor vacío
    public Talla() {
    }

    // Constructor sin timestamps (para inserción)
    public Talla(int idTalla, String tipoTalla, String nombreTalla, int ordenVisualizacion, Integer idTienda, boolean activo) {
        this.idTalla = idTalla;
        this.tipoTalla = tipoTalla;
        this.nombreTalla = nombreTalla;
        this.ordenVisualizacion = ordenVisualizacion;
        this.idTienda = idTienda;
        this.activo = activo;
    }

    // Constructor completo con timestamps
    public Talla(int idTalla, String tipoTalla, String nombreTalla, int ordenVisualizacion, 
                Integer idTienda, boolean activo, Timestamp createdAt, Timestamp updatedAt) {
        this.idTalla = idTalla;
        this.tipoTalla = tipoTalla;
        this.nombreTalla = nombreTalla;
        this.ordenVisualizacion = ordenVisualizacion;
        this.idTienda = idTienda;
        this.activo = activo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y Setters
    public int getIdTalla() {
        return idTalla;
    }

    public void setIdTalla(int idTalla) {
        this.idTalla = idTalla;
    }

    public String getTipoTalla() {
        return tipoTalla;
    }

    public void setTipoTalla(String tipoTalla) {
        this.tipoTalla = tipoTalla;
    }

    public String getNombreTalla() {
        return nombreTalla;
    }

    public void setNombreTalla(String nombreTalla) {
        this.nombreTalla = nombreTalla;
    }

    public int getOrdenVisualizacion() {
        return ordenVisualizacion;
    }

    public void setOrdenVisualizacion(int ordenVisualizacion) {
        this.ordenVisualizacion = ordenVisualizacion;
    }

    public Integer getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(Integer idTienda) {
        this.idTienda = idTienda;
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
        return nombreTalla;
    }
}
