package proyecto_sistema_venta.Presentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import proyecto_sistema_venta.Entidades.DetalleVenta;
import proyecto_sistema_venta.Entidades.Producto;
import proyecto_sistema_venta.Entidades.Venta;
import proyecto_sistema_venta.Negocio.ProductoNegocio;
import proyecto_sistema_venta.Negocio.VentaNegocio;

public class FrmRegistrarVenta extends JInternalFrame {

    private JComboBox<String> cmbProductos;
    private JTextField txtCantidad;
    private JButton btnAgregarProducto;
    private JTable tblDetalleVenta;
    private JButton btnRegistrarVenta;
    private JScrollPane jScrollPane1;
    private JLabel lblTotal;
    private JTextField txtTotal;

    private ProductoNegocio productoNegocio;
    private VentaNegocio ventaNegocio;
    private DefaultTableModel detalleTableModel;
    private List<DetalleVenta> detallesVenta;
    private List<Producto> productosDisponibles;

    public FrmRegistrarVenta() {
        productoNegocio = new ProductoNegocio();
        ventaNegocio = new VentaNegocio();
        detallesVenta = new ArrayList<>();
        initComponents();
        cargarProductosDisponibles();
        actualizarTablaDetalle();
    }

    private void initComponents() {
        cmbProductos = new JComboBox<>();
        txtCantidad = new JTextField();
        btnAgregarProducto = new JButton("Agregar Producto");
        jScrollPane1 = new JScrollPane();
        tblDetalleVenta = new JTable();
        btnRegistrarVenta = new JButton("Registrar Venta");
        lblTotal = new JLabel("Total:");
        txtTotal = new JTextField();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registrar Venta");

        txtTotal.setEditable(false);

        detalleTableModel = new DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID Producto", "Nombre", "Cantidad", "Precio Unitario", "Subtotal"
            }
        );
        tblDetalleVenta.setModel(detalleTableModel);
        jScrollPane1.setViewportView(tblDetalleVenta);

        btnAgregarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAgregarProductoActionPerformed(evt);
            }
        });

        btnRegistrarVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRegistrarVentaActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbProductos, 0, 200, Short.MAX_VALUE)
                            .addComponent(lblTotal, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCantidad, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(txtTotal))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregarProducto)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegistrarVenta)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbProductos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarProducto))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(txtTotal)
                    .addComponent(btnRegistrarVenta))
                .addContainerGap())
        );

        pack();
    }

    private void cargarProductosDisponibles() {
        try {
            productosDisponibles = productoNegocio.obtenerTodosProductos();
            cmbProductos.removeAllItems();
            for (Producto p : productosDisponibles) {
                cmbProductos.addItem(p.getNombreProducto() + " (Stock: " + p.getStockMinimo() + ")");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void btnAgregarProductoActionPerformed(ActionEvent evt) {
        int selectedIndex = cmbProductos.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Producto productoSeleccionado = productosDisponibles.get(selectedIndex);
        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cantidad > productoSeleccionado.getStockMinimo()) {
            JOptionPane.showMessageDialog(this, "No hay suficiente stock para el producto seleccionado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verificar si el producto ya está en el detalle de venta
        boolean found = false;
        for (DetalleVenta dv : detallesVenta) {
            if (dv.getIdProducto() == productoSeleccionado.getIdProducto()) {
                dv.setCantidad(dv.getCantidad() + cantidad);
                found = true;
                break;
            }
        }
        if (!found) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setIdProducto(productoSeleccionado.getIdProducto());
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(productoSeleccionado.getPrecioVentaMenor());
            detallesVenta.add(detalle);
        }

        actualizarTablaDetalle();
        calcularTotal();
        txtCantidad.setText("");
    }

    private void actualizarTablaDetalle() {
        detalleTableModel.setRowCount(0);
        for (DetalleVenta dv : detallesVenta) {
            try {
                Producto p = productoNegocio.obtenerProductoPorId(dv.getIdProducto());
                detalleTableModel.addRow(new Object[]{
                    dv.getIdProducto(),
                    p.getNombreProducto(),
                    dv.getCantidad(),
                    dv.getPrecioUnitario(),
                    dv.getCantidad() * dv.getPrecioUnitario()
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void calcularTotal() {
        double total = 0;
        for (DetalleVenta dv : detallesVenta) {
            total += dv.getCantidad() * dv.getPrecioUnitario();
        }
        txtTotal.setText(String.format("%.2f", total));
    }

    private void btnRegistrarVentaActionPerformed(ActionEvent evt) {
        if (detallesVenta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agregue productos al detalle de venta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Aquí se asumen valores para idTienda, idUsuarioVendedor, metodoPago, descuentoGlobal
            // En una aplicación real, estos valores vendrían de la interfaz de usuario o de la sesión del usuario
            int idTienda = 1; // Ejemplo
            int idUsuarioVendedor = 1; // Ejemplo
            String metodoPago = "Efectivo"; // Ejemplo
            double descuentoGlobal = 0.0; // Ejemplo

            Venta venta = ventaNegocio.procesarVenta(idTienda, idUsuarioVendedor, detallesVenta, metodoPago, descuentoGlobal);
            JOptionPane.showMessageDialog(this, "Venta registrada con éxito. ID de Venta: " + venta.getIdVenta(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            detallesVenta.clear();
            actualizarTablaDetalle();
            calcularTotal();
            cargarProductosDisponibles(); // Refrescar stock
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmRegistrarVenta().setVisible(true);
            }
        });
    }
}
