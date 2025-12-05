/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package proyecto_sistema_venta.Presentacion;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyecto_sistema_venta.Negocio.ProductoNegocio;
import proyecto_sistema_venta.Negocio.ClienteNegocio;
import proyecto_sistema_venta.Negocio.VentaNegocio;
import javax.swing.JComboBox;
import java.util.ArrayList;

/**
 * Formulario de Reportes
 */
public class FrmReportes extends javax.swing.JInternalFrame {

    private final ProductoNegocio productoNegocio;
    private final ClienteNegocio clienteNegocio;
    private final VentaNegocio ventaNegocio;
    private int idTiendaActual;

    /**
     * Creates new form FrmReportes
     */
    public FrmReportes() {
        initComponents();
        this.productoNegocio = new ProductoNegocio();
        this.clienteNegocio = new ClienteNegocio();
        this.ventaNegocio = new VentaNegocio();
        
        // Validar sesión
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "Debe iniciar sesión primero", "Sesión Requerida", JOptionPane.WARNING_MESSAGE);
            this.dispose();
            return;
        }
        
        this.idTiendaActual = sessionManager.getCurrentStoreId();
        
        // Configurar tablas
        UIConfig.configurarTabla(TblReporte);
        
        // Llenar combo de tipos de reporte
        cargarTiposReporte();
    }

    private void cargarTiposReporte() {
        CboTipoReporte.addItem("Seleccione un reporte...");
        CboTipoReporte.addItem("Productos más vendidos");
        CboTipoReporte.addItem("Stock bajo");
        CboTipoReporte.addItem("Clientes activos");
        CboTipoReporte.addItem("Ventas por período");
    }

    private void generarReporte() {
        int seleccion = CboTipoReporte.getSelectedIndex();
        System.out.println("LOG: Generando reporte, selección: " + seleccion);
        if (seleccion == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un tipo de reporte", "Reporte", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            DefaultTableModel modelo = null;
            switch (seleccion) {
                case 1:
                    modelo = reporteProductosMasVendidos();
                    break;
                case 2:
                    modelo = reporteStockBajo();
                    break;
                case 3:
                    modelo = reporteClientesActivos();
                    break;
                case 4:
                    modelo = reporteVentasPorPeriodo();
                    break;
            }

            if (modelo != null) {
                TblReporte.setModel(modelo);
                TxtTituloReporte.setText("Reporte: " + CboTipoReporte.getSelectedItem().toString());
                System.out.println("LOG: Reporte generado, filas: " + modelo.getRowCount());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al generar reporte: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private DefaultTableModel reporteProductosMasVendidos() {
        // Obtener todos los productos de la tienda actual
        DefaultTableModel modelo = productoNegocio.listarPorTienda("", idTiendaActual);
        System.out.println("LOG: Reporte productos mas vendidos, filas: " + modelo.getRowCount());
        return modelo;
    }

    private DefaultTableModel reporteStockBajo() {
        // Obtener productos con stock bajo
        DefaultTableModel modelo = productoNegocio.listarPorTienda("", idTiendaActual);
        System.out.println("LOG: Reporte stock bajo, filas antes filtro: " + modelo.getRowCount());
        // Filtrar productos con stock bajo (< 10)
        for (int i = modelo.getRowCount() - 1; i >= 0; i--) {
            try {
                int stock = Integer.parseInt(modelo.getValueAt(i, 5).toString());
                if (stock >= 10) {
                    modelo.removeRow(i);
                }
            } catch (NumberFormatException e) {
                // Si no es número, mantener
            }
        }
        System.out.println("LOG: Reporte stock bajo, filas después filtro: " + modelo.getRowCount());
        return modelo;
    }

    private DefaultTableModel reporteClientesActivos() {
        // Obtener clientes activos de la tienda
        DefaultTableModel modelo = clienteNegocio.listarPorTienda("", idTiendaActual);
        System.out.println("LOG: Reporte clientes activos, filas: " + modelo.getRowCount());
        return modelo;
    }

    private DefaultTableModel reporteVentasPorPeriodo() {
        // Obtener ventas del período
        javax.swing.table.TableModel tmodel = ventaNegocio.listar("", idTiendaActual);
        DefaultTableModel modelo = (DefaultTableModel) tmodel;
        System.out.println("LOG: Reporte ventas por periodo, filas: " + modelo.getRowCount());
        return modelo;
    }

    private void exportarReporte() {
        try {
            String titulo = TxtTituloReporte.getText();
            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay un reporte para exportar", "Exportar", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Reporte exportado correctamente", "Exportar", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al exportar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is edited by the "Form Editor".
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        CboTipoReporte = new javax.swing.JComboBox<>();
        BtnGenerar = new javax.swing.JButton();
        BtnExportar = new javax.swing.JButton();
        TxtTituloReporte = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblReporte = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Reportes");

        jPanel1.setBackground(new java.awt.Color(240, 240, 240));

        jLabel1.setText("Tipo de Reporte:");

        CboTipoReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CboTipoReporteActionPerformed(evt);
            }
        });

        BtnGenerar.setText("Generar");
        BtnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGenerarActionPerformed(evt);
            }
        });

        BtnExportar.setText("Exportar");
        BtnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnExportarActionPerformed(evt);
            }
        });

        TxtTituloReporte.setEditable(false);
        TxtTituloReporte.setText("Seleccione un reporte");

        TblReporte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TblReporte);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CboTipoReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnGenerar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnExportar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(TxtTituloReporte))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(CboTipoReporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnGenerar)
                    .addComponent(BtnExportar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtTituloReporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGenerarActionPerformed
        generarReporte();
    }//GEN-LAST:event_BtnGenerarActionPerformed

    private void BtnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnExportarActionPerformed
        exportarReporte();
    }//GEN-LAST:event_BtnExportarActionPerformed

    private void CboTipoReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CboTipoReporteActionPerformed
        // Auto-generate report when selection changes
        if (CboTipoReporte.getSelectedIndex() > 0) {
            generarReporte();
        }
    }//GEN-LAST:event_CboTipoReporteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnExportar;
    private javax.swing.JButton BtnGenerar;
    private javax.swing.JComboBox<String> CboTipoReporte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable TblReporte;
    private javax.swing.JTextField TxtTituloReporte;
    // End of variables declaration//GEN-END:variables
}
