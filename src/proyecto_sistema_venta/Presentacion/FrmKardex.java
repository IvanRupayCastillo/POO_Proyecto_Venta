package proyecto_sistema_venta.Presentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;
import proyecto_sistema_venta.Conexion.Conexion;

public class FrmKardex extends javax.swing.JInternalFrame {

    private Conexion conexion;

    public FrmKardex() {
        conexion = new Conexion();
        initComponents();
        inicializarFechas();
        cargarCombos();
        cargarKardex();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblFechaDesde = new javax.swing.JLabel();
        txtFechaDesde = new javax.swing.JTextField();
        lblFechaHasta = new javax.swing.JLabel();
        txtFechaHasta = new javax.swing.JTextField();
        lblTienda = new javax.swing.JLabel();
        cmbTienda = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        lblTipo = new javax.swing.JLabel();
        cmbTipoMovimiento = new javax.swing.JComboBox<>();
        lblEstado = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox<>();
        lblUsuario = new javax.swing.JLabel();
        cmbUsuario = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKardex = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Kardex - Movimientos de Almacén");

        jPanel1.setLayout(new java.awt.GridLayout(3, 0));

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblFechaDesde.setText("Fecha Desde:");
        jPanel3.add(lblFechaDesde);

        txtFechaDesde.setColumns(10);
        txtFechaDesde.setToolTipText("Formato: YYYY-MM-DD");
        jPanel3.add(txtFechaDesde);

        lblFechaHasta.setText("Fecha Hasta:");
        jPanel3.add(lblFechaHasta);

        txtFechaHasta.setColumns(10);
        txtFechaHasta.setToolTipText("Formato: YYYY-MM-DD");
        jPanel3.add(txtFechaHasta);

        lblTienda.setText("Tienda:");
        jPanel3.add(lblTienda);

        cmbTienda.setPreferredSize(new java.awt.Dimension(150, 22));
        jPanel3.add(cmbTienda);

        jPanel1.add(jPanel3);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblTipo.setText("Tipo Movimiento:");
        jPanel4.add(lblTipo);

        cmbTipoMovimiento.setPreferredSize(new java.awt.Dimension(150, 22));
        jPanel4.add(cmbTipoMovimiento);

        lblEstado.setText("Estado:");
        jPanel4.add(lblEstado);

        cmbEstado.setPreferredSize(new java.awt.Dimension(150, 22));
        jPanel4.add(cmbEstado);

        lblUsuario.setText("Usuario:");
        jPanel4.add(lblUsuario);

        cmbUsuario.setPreferredSize(new java.awt.Dimension(150, 22));
        jPanel4.add(cmbUsuario);

        jPanel1.add(jPanel4);

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblBuscar.setText("Buscar (Nro Doc):");
        jPanel5.add(lblBuscar);

        txtBuscar.setColumns(15);
        jPanel5.add(txtBuscar);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel5.add(btnBuscar);

        btnLimpiar.setText("Limpiar Filtros");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel5.add(btnLimpiar);

        jPanel1.add(jPanel5);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tblKardex.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Mov", "Nro Doc", "Tipo Movimiento", "Tienda Origen", "Tienda Destino", "Fecha", "Hora", "Estado", "Monto Total", "Usuario"
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

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblTotal.setText("Total de registros: 0");
        jPanel2.add(lblTotal);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        cargarKardex();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarFiltros();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tblKardexMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKardexMouseClicked
        if (evt.getClickCount() == 2 && tblKardex.getSelectedRow() != -1) {
            Object value = tblKardex.getValueAt(tblKardex.getSelectedRow(), 0);
            if (value instanceof Integer) {
                int idMovimiento = (Integer) value;
                mostrarDetalle(idMovimiento);
            } else {
                JOptionPane.showMessageDialog(this, "Error: El ID del movimiento no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_tblKardexMouseClicked

    private void inicializarFechas() {
        // Establecer fecha desde: primer día del mes actual
        LocalDate hoy = LocalDate.now();
        LocalDate primerDiaMes = hoy.withDayOfMonth(1);
        txtFechaDesde.setText(primerDiaMes.toString());
        txtFechaHasta.setText(hoy.toString());
    }

    private void cargarCombos() {
        cargarTiendas();
        cargarTiposMovimiento();
        cargarEstados();
        cargarUsuarios();
    }

    private void cargarTiendas() {
        cmbTienda.removeAllItems();
        cmbTienda.addItem("TODAS");
        
        String sql = "SELECT nombre_tienda FROM tiendas WHERE activo = 1 ORDER BY nombre_tienda";
        
        try (Connection conn = conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                cmbTienda.addItem(rs.getString("nombre_tienda"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar tiendas: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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

    private void cargarEstados() {
        cmbEstado.removeAllItems();
        cmbEstado.addItem("TODOS");
        cmbEstado.addItem("PENDIENTE");
        cmbEstado.addItem("AUTORIZADO");
        cmbEstado.addItem("CONFIRMADO");
        cmbEstado.addItem("ANULADO");
    }

    private void cargarUsuarios() {
        cmbUsuario.removeAllItems();
        cmbUsuario.addItem("TODOS");
        
        String sql = "SELECT nombre_completo FROM usuarios WHERE activo = 1 ORDER BY nombre_completo";
        
        try (Connection conn = conexion.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                cmbUsuario.addItem(rs.getString("nombre_completo"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void limpiarFiltros() {
        inicializarFechas();
        txtBuscar.setText("");
        cmbTienda.setSelectedIndex(0);
        cmbTipoMovimiento.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        cmbUsuario.setSelectedIndex(0);
        cargarKardex();
    }

    private void cargarKardex() {
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
        
        // Filtro por fechas (OBLIGATORIO)
        sql.append("AND m.fecha_movimiento BETWEEN ? AND ? ");
        
        // Filtro por número de documento
        String filtroDoc = txtBuscar.getText().trim();
        if (!filtroDoc.isEmpty()) {
            sql.append("AND m.numero_documento LIKE ? ");
        }
        
        // Filtro por tienda
        String tiendaSeleccionada = (String) cmbTienda.getSelectedItem();
        if (tiendaSeleccionada != null && !tiendaSeleccionada.equals("TODAS")) {
            sql.append("AND to1.nombre_tienda = ? ");
        }
        
        // Filtro por tipo de movimiento
        String tipoSeleccionado = (String) cmbTipoMovimiento.getSelectedItem();
        if (tipoSeleccionado != null && !tipoSeleccionado.equals("TODOS")) {
            sql.append("AND tm.nombre_tipo = ? ");
        }
        
        // Filtro por estado
        String estadoSeleccionado = (String) cmbEstado.getSelectedItem();
        if (estadoSeleccionado != null && !estadoSeleccionado.equals("TODOS")) {
            sql.append("AND m.estado = ? ");
        }
        
        // Filtro por usuario
        String usuarioSeleccionado = (String) cmbUsuario.getSelectedItem();
        if (usuarioSeleccionado != null && !usuarioSeleccionado.equals("TODOS")) {
            sql.append("AND u.nombre_completo = ? ");
        }
        
        sql.append("ORDER BY m.fecha_movimiento DESC, m.hora_movimiento DESC");
        
        try (Connection conn = conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            
            // Parámetros de fecha (obligatorios)
            pstmt.setString(paramIndex++, txtFechaDesde.getText());
            pstmt.setString(paramIndex++, txtFechaHasta.getText());
            
            // Parámetro de número de documento
            if (!filtroDoc.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + filtroDoc + "%");
            }
            
            // Parámetro de tienda
            if (tiendaSeleccionada != null && !tiendaSeleccionada.equals("TODAS")) {
                pstmt.setString(paramIndex++, tiendaSeleccionada);
            }
            
            // Parámetro de tipo de movimiento
            if (tipoSeleccionado != null && !tipoSeleccionado.equals("TODOS")) {
                pstmt.setString(paramIndex++, tipoSeleccionado);
            }
            
            // Parámetro de estado
            if (estadoSeleccionado != null && !estadoSeleccionado.equals("TODOS")) {
                pstmt.setString(paramIndex++, estadoSeleccionado);
            }
            
            // Parámetro de usuario
            if (usuarioSeleccionado != null && !usuarioSeleccionado.equals("TODOS")) {
                pstmt.setString(paramIndex++, usuarioSeleccionado);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<String> cmbTienda;
    private javax.swing.JComboBox<String> cmbTipoMovimiento;
    private javax.swing.JComboBox<String> cmbUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaDesde;
    private javax.swing.JLabel lblFechaHasta;
    private javax.swing.JLabel lblTienda;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblKardex;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtFechaDesde;
    private javax.swing.JTextField txtFechaHasta;
    // End of variables declaration//GEN-END:variables
}
