/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_sistema_venta.Entidades;

import java.sql.Timestamp;

public class Producto {
    
    private int idProducto;
    private String codigoProducto;
    private String nombreProducto;
    private String descripcion;
    private int idTipo;
    private int idColor;
    private int idTalla;
    private double precioCompra;
    private double precioVentaMayor;
    private double precioVentaMenor;
    private int stockMinimo;
    private boolean activo;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructor vacío
    public Producto() {
    }

    // Constructor con parámetros principales (sin timestamps)
    public Producto(int idProducto, String codigoProducto, String nombreProducto, 
                   String descripcion, int idTipo, int idColor, int idTalla, 
                   double precioCompra, double precioVentaMayor, double precioVentaMenor, 
                   int stockMinimo, boolean activo) {
        this.idProducto = idProducto;
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.idTipo = idTipo;
        this.idColor = idColor;
        this.idTalla = idTalla;
        this.precioCompra = precioCompra;
        this.precioVentaMayor = precioVentaMayor;
        this.precioVentaMenor = precioVentaMenor;
        this.stockMinimo = stockMinimo;
        this.activo = activo;
    }

    // Constructor completo con timestamps
    public Producto(int idProducto, String codigoProducto, String nombreProducto, 
                   String descripcion, int idTipo, int idColor, int idTalla, 
                   double precioCompra, double precioVentaMayor, double precioVentaMenor, 
                   int stockMinimo, boolean activo, Timestamp createdAt, Timestamp updatedAt) {
        this.idProducto = idProducto;
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.idTipo = idTipo;
        this.idColor = idColor;
        this.idTalla = idTalla;
        this.precioCompra = precioCompra;
        this.precioVentaMayor = precioVentaMayor;
        this.precioVentaMenor = precioVentaMenor;
        this.stockMinimo = stockMinimo;
        this.activo = activo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y Setters
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public int getIdTalla() {
        return idTalla;
    }

    public void setIdTalla(int idTalla) {
        this.idTalla = idTalla;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVentaMayor() {
        return precioVentaMayor;
    }

    public void setPrecioVentaMayor(double precioVentaMayor) {
        this.precioVentaMayor = precioVentaMayor;
    }

    public double getPrecioVentaMenor() {
        return precioVentaMenor;
    }

    public void setPrecioVentaMenor(double precioVentaMenor) {
        this.precioVentaMenor = precioVentaMenor;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
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
        return "Producto{" + 
                "idProducto=" + idProducto + 
                ", codigoProducto='" + codigoProducto + '\'' +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", precioVentaMenor=" + precioVentaMenor +
                '}';
    }
}











