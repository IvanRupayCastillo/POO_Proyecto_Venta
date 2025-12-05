package proyecto_sistema_venta.Presentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import proyecto_sistema_venta.Conexion.Conexion;

public class FrmKardex extends javax.swing.JInternalFrame {

    private JTable tblKardex;
    private JScrollPane jScrollPane1;
    private JButton btnBuscar;
    private JTextField txtBuscar;
    private JLabel lblTotal;
    private JLabel lblBuscar;
    private JLabel lblTipo;
    private JComboBox<String> cmbTipoMovimiento;
    private Conexion conexion;

    public FrmKardex() {
        conexion = new Conexion();
        initComponents();
        cargarTiposMovimiento();
        cargarKardex("");
    }

    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        tblKardex = new JTable();
        btnBuscar = new JButton("Buscar");
        txtBuscar = new JTextField();
        lblTotal = new JLabel("Total de registros: 0");
        lblBuscar = new JLabel("Buscar:");
        lblTipo = new JLabel("Tipo:");
        cmbTipoMovimiento = new JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Kardex - Movimientos de Almacén");

        tblKardex.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID Mov", "Nro Doc", "Tipo Movimiento", "Tienda Origen", "Tienda Destino", 
                "Fecha", "Hora", "Estado", "Monto Total", "Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        tblKardex.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKardexMouseClicked(evt);
            }
        });
        
        jScrollPane1.setViewportView(tblKardex);

        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        
        cmbTipoMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoMovimientoActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblBuscar)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTipo)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipoMovimiento, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTotal)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscar)
                    .addComponent(txtBuscar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipo)
                    .addComponent(cmbTipoMovimiento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotal)
                .addContainerGap())
        );

        pack();
    }

    private void cargarTiposMovimiento() {
        cmbTipoMovimiento.removeAllItems();
        cmbTipoMovimiento.addItem("TODOS");
        
        String sql = "SELECT nombre_tipo FROM tipos_movimiento WHERE activo = 1 ORDER BY nombre_tipo";
        
        try (Connection conn = conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                cmbTipoMovimiento.addItem(rs.getString("nombre_tipo"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar tipos de movimiento: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void cargarKardex(String filtro) {
        DefaultTableModel model = (DefaultTableModel) tblKardex.getModel();
        model.setRowCount(0);
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m.id_movimiento, m.numero_documento, tm.nombre_tipo, ");
        sql.append("to1.nombre_tienda AS tienda_origen, ");
        sql.append("COALESCE(to2.nombre_tienda, '-') AS tienda_destino, ");
        sql.append("m.fecha_movimiento, m.hora_movimiento, m.estado, m.monto_total, ");
        sql.append("u.nombre_completo AS usuario ");
        sql.append("FROM movimientos_almacen_cabecera m ");
        sql.append("INNER JOIN tipos_movimiento tm ON m.id_tipo_movimiento = tm.id_tipo_movimiento ");
        sql.append("INNER JOIN tiendas to1 ON m.id_tienda_origen = to1.id_tienda ");
        sql.append("LEFT JOIN tiendas to2 ON m.id_tienda_destino = to2.id_tienda ");
        sql.append("INNER JOIN usuarios u ON m.id_usuario_registro = u.id_usuario ");
        sql.append("WHERE 1=1 ");
        
        if (filtro != null && !filtro.trim().isEmpty()) {
            sql.append("AND (m.numero_documento LIKE ? OR tm.nombre_tipo LIKE ? OR to1.nombre_tienda LIKE ?) ");
        }
        
        String tipoSeleccionado = (String) cmbTipoMovimiento.getSelectedItem();
        if (tipoSeleccionado != null && !tipoSeleccionado.equals("TODOS")) {
            sql.append("AND tm.nombre_tipo = ? ");
        }
        
        sql.append("ORDER BY m.fecha_movimiento DESC, m.hora_movimiento DESC");
        
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            if (filtro != null && !filtro.trim().isEmpty()) {
                String filtroLike = "%" + filtro + "%";
                pstmt.setString(paramIndex++, filtroLike);
                pstmt.setString(paramIndex++, filtroLike);
                pstmt.setString(paramIndex++, filtroLike);
            }
            
            if (tipoSeleccionado != null && !tipoSeleccionado.equals("TODOS")) {
                pstmt.setString(paramIndex, tipoSeleccionado);
            }
            
            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_movimiento"),
                    rs.getString("numero_documento"),
                    rs.getString("nombre_tipo"),
                    rs.getString("tienda_origen"),
                    rs.getString("tienda_destino"),
                    rs.getDate("fecha_movimiento"),
                    rs.getTime("hora_movimiento"),
                    rs.getString("estado"),
                    String.format("%.2f", rs.getDouble("monto_total")),
                    rs.getString("usuario")
                });
                count++;
            }
            
            lblTotal.setText("Total de registros: " + count);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar kardex: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        cargarKardex(txtBuscar.getText());
    }

    private void cmbTipoMovimientoActionPerformed(java.awt.event.ActionEvent evt) {
        cargarKardex(txtBuscar.getText());
    }

    private void tblKardexMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2 && tblKardex.getSelectedRow() != -1) {
            int idMovimiento = (int) tblKardex.getValueAt(tblKardex.getSelectedRow(), 0);
            mostrarDetalle(idMovimiento);
        }
    }

    private void mostrarDetalle(int idMovimiento) {
        StringBuilder detalle = new StringBuilder();
        detalle.append("DETALLE DEL MOVIMIENTO\n\n");
        
        String sql = "SELECT p.codigo_producto, p.nombre_producto, d.cantidad, " +
                    "d.precio_unitario, d.subtotal, d.stock_anterior, d.stock_nuevo, d.observaciones " +
                    "FROM movimientos_almacen_detalle d " +
                    "INNER JOIN productos p ON d.id_producto = p.id_producto " +
                    "WHERE d.id_movimiento = ?";
        
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idMovimiento);
            ResultSet rs = pstmt.executeQuery();
            
            detalle.append(String.format("%-15s %-30s %10s %12s %12s %10s %10s\n", 
                          "Código", "Producto", "Cantidad", "P. Unitario", "Subtotal", "Stock Ant", "Stock Nvo"));
            detalle.append("-".repeat(110)).append("\n");
            
            while (rs.next()) {
                detalle.append(String.format("%-15s %-30s %10d %12.2f %12.2f %10s %10s\n",
                    rs.getString("codigo_producto"),
                    rs.getString("nombre_producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario"),
                    rs.getDouble("subtotal"),
                    rs.getObject("stock_anterior") != null ? rs.getInt("stock_anterior") : "-",
                    rs.getObject("stock_nuevo") != null ? rs.getInt("stock_nuevo") : "-"
                ));
            }
            
            JTextArea textArea = new JTextArea(detalle.toString());
            textArea.setFont(new java.awt.Font("Monospaced", 0, 12));
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(900, 400));
            
            JOptionPane.showMessageDialog(this, scrollPane, "Detalle del Movimiento", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar detalle: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmKardex().setVisible(true);
            }
        });
    }
}
