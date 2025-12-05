package proyecto_sistema_venta.Entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa un comprobante de venta con sus detalles.
 */
public class Venta implements Serializable {

    private int idVenta;
    private String tipoComprobante;
    private String serieComprobante;
    private String numeroComprobante;
    private LocalDate fechaVenta;
    private LocalTime horaVenta;
    private int idTienda;
    private Integer idCliente;
    private int idUsuarioVendedor;
    private double subtotal;
    private double descuento;
    private double igv;
    private double total;
    private String metodoPago;
    private String estado;
    private String observaciones;
    private final List<DetalleVenta> detalles;

    public Venta() {
        this.detalles = new ArrayList<>();
        this.estado = "EMITIDA";
        this.metodoPago = "EFECTIVO";
        this.tipoComprobante = "TICKET";
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getSerieComprobante() {
        return serieComprobante;
    }

    public void setSerieComprobante(String serieComprobante) {
        this.serieComprobante = serieComprobante;
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public LocalTime getHoraVenta() {
        return horaVenta;
    }

    public void setHoraVenta(LocalTime horaVenta) {
        this.horaVenta = horaVenta;
    }

    public int getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdUsuarioVendedor() {
        return idUsuarioVendedor;
    }

    public void setIdUsuarioVendedor(int idUsuarioVendedor) {
        this.idUsuarioVendedor = idUsuarioVendedor;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Lista inmutable de detalles de venta.
     */
    public List<DetalleVenta> getDetalles() {
        return Collections.unmodifiableList(detalles);
    }

    /**
     * Agrega un detalle de venta.
     */
    public void agregarDetalle(DetalleVenta detalle) {
        if (detalle == null) {
            throw new IllegalArgumentException("Detalle no puede ser nulo");
        }
        if (detalle.getCantidad() <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser positiva");
        }
        if (detalle.getPrecioUnitario() <= 0) {
            throw new IllegalArgumentException("Precio unitario debe ser positivo");
        }
        detalles.add(detalle);
    }
}

