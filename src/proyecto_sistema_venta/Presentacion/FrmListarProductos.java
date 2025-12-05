package proyecto_sistema_venta.Presentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import proyecto_sistema_venta.Negocio.ProductoNegocio;

public class FrmListarProductos extends JInternalFrame {

    private JTable tblProductos;
    private JButton btnRefrescar;
    private JScrollPane jScrollPane1;
    private ProductoNegocio productoNegocio;
    private Integer idTiendaActual;

    public FrmListarProductos() {
        // Obtener tienda del usuario logueado
        SessionManager sessionManager = SessionManager.getInstance();
        this.idTiendaActual = sessionManager.getCurrentStoreId();
        
        if (idTiendaActual == null) {
            JOptionPane.showMessageDialog(this,
                "Error: No hay una sesión activa. Por favor, inicie sesión nuevamente.",
                "Error de Sesión", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        productoNegocio = new ProductoNegocio();
        initComponents();
        cargarProductos();
    }

    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        tblProductos = new JTable();
        btnRefrescar = new JButton("Refrescar");

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Listado de Productos");

        tblProductos.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Nombre", "Descripción", "Precio", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblProductos);

        btnRefrescar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
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
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRefrescar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRefrescar)
                .addContainerGap())
        );

        pack();
    }

    private void btnRefrescarActionPerformed(ActionEvent evt) {
        cargarProductos();
    }

    private void cargarProductos() {
        DefaultTableModel model = (DefaultTableModel) tblProductos.getModel();
        model.setRowCount(0); // Limpiar tabla
        try {
            // Usar listarPorTienda en lugar de obtenerTodosProductos
            model = (DefaultTableModel) productoNegocio.listarPorTienda("", idTiendaActual);
            tblProductos.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmListarProductos().setVisible(true);
            }
        });
    }
}
