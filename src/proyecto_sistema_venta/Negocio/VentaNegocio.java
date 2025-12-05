package proyecto_sistema_venta.Negocio;

import proyecto_sistema_venta.Datos.DetalleVentaDAO;
import proyecto_sistema_venta.Datos.InventarioDAO;
import proyecto_sistema_venta.Datos.ProductoDAO;
import proyecto_sistema_venta.Datos.VentaDAO;
import proyecto_sistema_venta.Entidades.DetalleVenta;
import proyecto_sistema_venta.Entidades.Producto;
import proyecto_sistema_venta.Entidades.Tienda;
import proyecto_sistema_venta.Entidades.Venta;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VentaNegocio {

    private static final double IGV_RATE = 0.18;

    private final VentaDAO ventaDAO;
    private final DetalleVentaDAO detalleDAO;
    private final InventarioDAO inventarioDAO;
    private final ProductoDAO productoDAO;

    public VentaNegocio() {
        this.ventaDAO = new VentaDAO();
        this.detalleDAO = new DetalleVentaDAO();
        this.inventarioDAO = new InventarioDAO();
        this.productoDAO = new ProductoDAO();
    }

    /**
     * Procesa una venta completa: valida stock, calcula totales, persiste y actualiza inventario.
     */
    public Venta procesarVenta(int idTienda, int idUsuarioVendedor, List<DetalleVenta> items, String metodoPago, double descuentoGlobal) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("La venta debe contener al menos un producto");
        }
        if (descuentoGlobal < 0) {
            throw new IllegalArgumentException("El descuento global no puede ser negativo");
        }

        List<DetalleVenta> detallesValidados = new ArrayList<>();
        double subtotal = 0.0;

        for (DetalleVenta item : items) {
            Producto p = productoDAO.buscarPorId(item.getIdProducto());
            if (p == null || !p.isActivo()) {
                throw new IllegalArgumentException("Producto no válido: " + item.getIdProducto());
            }
            Integer stockDisp = inventarioDAO.obtenerStockDisponible(idTienda, item.getIdProducto());
            if (stockDisp == null || stockDisp < item.getCantidad()) {
                throw new IllegalStateException("Stock insuficiente para producto " + p.getCodigoProducto());
            }
            DetalleVenta d = new DetalleVenta();
            d.setIdProducto(item.getIdProducto());
            d.setCantidad(item.getCantidad());
            d.setPrecioUnitario(p.getPrecioVentaMenor());
            d.setDescuentoItem(item.getDescuentoItem());
            detallesValidados.add(d);
            subtotal += (d.getCantidad() * d.getPrecioUnitario()) - d.getDescuentoItem();
        }

        double descuentoAplicado = Math.min(descuentoGlobal, subtotal);
        double baseImponible = subtotal - descuentoAplicado;
        double igv = round2(baseImponible * IGV_RATE);
        double total = round2(baseImponible + igv);

        Venta venta = new Venta();
        venta.setTipoComprobante("TICKET");
        venta.setSerieComprobante("T001");
        venta.setNumeroComprobante(generarNumero());
        venta.setFechaVenta(LocalDate.now());
        venta.setHoraVenta(LocalTime.now());
        venta.setIdTienda(idTienda);
        venta.setIdUsuarioVendedor(idUsuarioVendedor);
        venta.setSubtotal(round2(subtotal));
        venta.setDescuento(round2(descuentoAplicado));
        venta.setIgv(igv);
        venta.setTotal(total);
        venta.setMetodoPago(metodoPago == null ? "EFECTIVO" : metodoPago);
        venta.setEstado("EMITIDA");

        int idVenta = ventaDAO.insertar(venta);
        if (idVenta <= 0) {
            throw new IllegalStateException("No se pudo registrar la venta");
        }
        venta.setIdVenta(idVenta);

        boolean detallesOk = detalleDAO.insertarLote(idVenta, detallesValidados);
        if (!detallesOk) {
            throw new IllegalStateException("No se pudieron registrar los detalles de la venta");
        }

        for (DetalleVenta d : detallesValidados) {
            boolean upd = inventarioDAO.disminuirStock(idTienda, d.getIdProducto(), d.getCantidad());
            if (!upd) {
                throw new IllegalStateException("No se pudo actualizar inventario para producto " + d.getIdProducto());
            }
        }

        for (DetalleVenta d : detallesValidados) {
            venta.agregarDetalle(d);
        }
        generarReciboJSON(venta);
        generarReciboXML(venta);
        return venta;
    }

    private String generarNumero() {
        long ts = System.currentTimeMillis();
        return String.valueOf(ts % 100000000L);
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    /**
     * Genera un recibo en formato JSON sin librerías externas.
     */
    public void generarReciboJSON(Venta v) {
        String dir = "data/ventas";
        File folder = new File(dir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String path = dir + "/venta_" + v.getIdVenta() + ".json";
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"idVenta\": ").append(v.getIdVenta()).append(",\n");
        sb.append("  \"comprobante\": {\n");
        sb.append("    \"tipo\": \"").append(v.getTipoComprobante()).append("\",\n");
        sb.append("    \"serie\": \"").append(v.getSerieComprobante()).append("\",\n");
        sb.append("    \"numero\": \"").append(v.getNumeroComprobante()).append("\"\n");
        sb.append("  },\n");
        sb.append("  \"fecha\": \"").append(v.getFechaVenta()).append("\",\n");
        sb.append("  \"hora\": \"").append(v.getHoraVenta()).append("\",\n");
        sb.append("  \"idTienda\": ").append(v.getIdTienda()).append(",\n");
        sb.append("  \"idVendedor\": ").append(v.getIdUsuarioVendedor()).append(",\n");
        sb.append("  \"subtotal\": ").append(v.getSubtotal()).append(",\n");
        sb.append("  \"descuento\": ").append(v.getDescuento()).append(",\n");
        sb.append("  \"igv\": ").append(v.getIgv()).append(",\n");
        sb.append("  \"total\": ").append(v.getTotal()).append(",\n");
        sb.append("  \"metodoPago\": \"").append(v.getMetodoPago()).append("\",\n");
        sb.append("  \"detalles\": [\n");
        for (int i = 0; i < v.getDetalles().size(); i++) {
            DetalleVenta d = v.getDetalles().get(i);
            sb.append("    {\n");
            sb.append("      \"idProducto\": ").append(d.getIdProducto()).append(",\n");
            sb.append("      \"cantidad\": ").append(d.getCantidad()).append(",\n");
            sb.append("      \"precioUnitario\": ").append(d.getPrecioUnitario()).append(",\n");
            sb.append("      \"descuentoItem\": ").append(d.getDescuentoItem()).append("\n");
            sb.append("    }");
            if (i < v.getDetalles().size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  ]\n");
        sb.append("}\n");
        try {
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(sb.toString().getBytes());
            }
        } catch (IOException ignored) {
        }
    }

    /**
     * Genera un recibo en formato XML sin librerías externas.
     */
    public void generarReciboXML(Venta v) {
        String dir = "data/ventas";
        File folder = new File(dir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String path = dir + "/venta_" + v.getIdVenta() + ".xml";
        StringBuilder sb = new StringBuilder();
        sb.append("<venta id=\"").append(v.getIdVenta()).append("\">\n");
        sb.append("  <comprobante tipo=\"").append(v.getTipoComprobante()).append("\" serie=\"").append(v.getSerieComprobante()).append("\" numero=\"").append(v.getNumeroComprobante()).append("\"/>\n");
        sb.append("  <fecha>").append(v.getFechaVenta()).append("</fecha>\n");
        sb.append("  <hora>").append(v.getHoraVenta()).append("</hora>\n");
        sb.append("  <idTienda>").append(v.getIdTienda()).append("</idTienda>\n");
        sb.append("  <idVendedor>").append(v.getIdUsuarioVendedor()).append("</idVendedor>\n");
        sb.append("  <subtotal>").append(v.getSubtotal()).append("</subtotal>\n");
        sb.append("  <descuento>").append(v.getDescuento()).append("</descuento>\n");
        sb.append("  <igv>").append(v.getIgv()).append("</igv>\n");
        sb.append("  <total>").append(v.getTotal()).append("</total>\n");
        sb.append("  <metodoPago>").append(v.getMetodoPago()).append("</metodoPago>\n");
        sb.append("  <detalles>\n");
        for (DetalleVenta d : v.getDetalles()) {
            sb.append("    <detalle idProducto=\"").append(d.getIdProducto()).append("\" cantidad=\"").append(d.getCantidad()).append("\" precioUnitario=\"").append(d.getPrecioUnitario()).append("\" descuentoItem=\"").append(d.getDescuentoItem()).append("\"/>\n");
        }
        sb.append("  </detalles>\n");
        sb.append("</venta>\n");
        try {
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(sb.toString().getBytes());
            }
        } catch (IOException ignored) {
        }
    }

    /**
     * Serializa el estado de la tienda a un archivo.
     */
    public void guardarEstadoTienda(Tienda tienda, String rutaArchivo) {
        try {
            File f = new File(rutaArchivo);
            File parent = f.getParentFile();
            if (parent != null && !parent.exists()) parent.mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
                oos.writeObject(tienda);
            }
        } catch (IOException ignored) {
        }
    }

    /**
     * Lista todas las ventas, opcionalmente filtradas por texto de búsqueda
     */
    public javax.swing.table.TableModel listar(String texto) {
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        String[] columnas = {"ID Venta", "Fecha", "Total", "Método Pago"};
        model.setColumnIdentifiers(columnas);
        
        try {
            // Obtener todas las ventas (por ahora de todas las tiendas)
            // TODO: Filtrar por tienda del usuario logueado
            List<Venta> ventas = ventaDAO.listarPorTienda(1); // Tienda 1 por defecto
            
            for (Venta v : ventas) {
                Object[] fila = new Object[4];
                fila[0] = v.getIdVenta();
                fila[1] = v.getFechaVenta();
                fila[2] = String.format("S/ %.2f", v.getTotal());
                fila[3] = v.getMetodoPago();
                
                // Filtrar por texto si se proporciona
                if (texto == null || texto.trim().isEmpty()) {
                    model.addRow(fila);
                } else {
                    String busqueda = texto.toLowerCase();
                    String idStr = String.valueOf(v.getIdVenta()).toLowerCase();
                    String fecha = v.getFechaVenta().toString().toLowerCase();
                    String metodo = v.getMetodoPago().toLowerCase();
                    
                    if (idStr.contains(busqueda) || fecha.contains(busqueda) || metodo.contains(busqueda)) {
                        model.addRow(fila);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar ventas: " + e.getMessage());
            e.printStackTrace();
        }
        
        return model;
    }

    /**
     * Lista los tipos de comprobante disponibles
     */
    public List<String> listarTipoComprobante() {
        List<String> tipos = new ArrayList<>();
        tipos.add("Boleta");
        tipos.add("Factura");
        tipos.add("Ticket");
        return tipos;
    }

    /**
     * Registra una venta completa usando el stored procedure
     * @param venta Objeto Venta con los datos de la cabecera
     * @param detalles Lista de DetalleVenta con los productos
     * @return Mensaje de resultado
     */
    public String registrarVentaCompleta(Venta venta, List<DetalleVenta> detalles) {
        System.out.println("VentaNegocio.registrarVentaCompleta - INICIO");
        
        try {
            // Validaciones
            if (detalles == null || detalles.isEmpty()) {
                System.out.println("ERROR: Detalles vacíos");
                return "ERROR: La venta debe contener al menos un producto";
            }
            
            System.out.println("LOG: Detalles recibidos: " + detalles.size());
            
            // ID del tipo de movimiento para "SALIDA POR VENTA" (debe existir en la BD)
            int idTipoMovimiento = 2; // Ajustar según tu base de datos
            
            // Generar número de documento para el movimiento
            String numeroDocMov = "MOV-" + System.currentTimeMillis();
            
            System.out.println("LOG: Llamando a ventaDAO.registrarVentaCompleta...");
            System.out.println("LOG: idTipoMovimiento: " + idTipoMovimiento);
            System.out.println("LOG: numeroDocMov: " + numeroDocMov);
            
            // Llamar al DAO
            Object[] resultado = ventaDAO.registrarVentaCompleta(venta, detalles, idTipoMovimiento, numeroDocMov);
            
            System.out.println("LOG: Resultado DAO recibido");
            System.out.println("LOG: resultado[0] (idVenta): " + resultado[0]);
            System.out.println("LOG: resultado[1] (idMovimiento): " + resultado[1]);
            System.out.println("LOG: resultado[2] (codigoError): " + resultado[2]);
            System.out.println("LOG: resultado[3] (mensaje): " + resultado[3]);
            
            int codigoError = (int) resultado[2];
            String mensaje = (String) resultado[3];
            
            if (codigoError == 0) {
                System.out.println("LOG: Venta exitosa");
                // Éxito
                int idVenta = (int) resultado[0];
                venta.setIdVenta(idVenta);
                
                // Generar recibos
                for (DetalleVenta d : detalles) {
                    venta.agregarDetalle(d);
                }
                System.out.println("LOG: Generando recibos...");
                generarReciboJSON(venta);
                generarReciboXML(venta);
                
                System.out.println("VentaNegocio.registrarVentaCompleta - FIN EXITOSO");
                return "OK";
            } else {
                // Error
                System.out.println("ERROR: " + mensaje);
                System.out.println("VentaNegocio.registrarVentaCompleta - FIN CON ERROR");
                return mensaje;
            }
            
        } catch (Exception e) {
            System.out.println("EXCEPTION en VentaNegocio.registrarVentaCompleta: " + e.getClass().getName());
            System.out.println("EXCEPTION Message: " + e.getMessage());
            e.printStackTrace();
            return "ERROR: " + (e.getMessage() != null ? e.getMessage() : e.toString());
        }
    }
}
