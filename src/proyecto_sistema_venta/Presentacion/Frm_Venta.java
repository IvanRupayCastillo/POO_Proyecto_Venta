/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package proyecto_sistema_venta.Presentacion;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JOptionPane;
import proyecto_sistema_venta.Entidades.Venta;
import proyecto_sistema_venta.Entidades.Cliente;
import proyecto_sistema_venta.Entidades.DetalleVenta;
import proyecto_sistema_venta.Negocio.VentaNegocio;
import proyecto_sistema_venta.Negocio.ClienteNegocio;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Frm_Venta extends javax.swing.JInternalFrame {

    /**
     * Creates new form Frm_Venta
     */
    
    
    private final VentaNegocio CONTROL;
    private final VentaNegocio doc;
    private final ClienteNegocio clienteNegocio;
    // private String accion; // Removed unused field
    private Venta venta;
    private int id_cliente_seleccionado = 0;
    @SuppressWarnings("unused")
    private boolean primeraCarga = true;
    public JFrame contenedor;
   
    
    
    public Frm_Venta(JFrame frmP) {
           initComponents();
           // Add missing action listener for BtnQuitarProducto
           BtnQuitarProducto.addActionListener(new java.awt.event.ActionListener() {
               public void actionPerformed(java.awt.event.ActionEvent evt) {
                   BtnQuitarProductoActionPerformed(evt);
               }
           });
           this.contenedor=frmP;
          this.CONTROL = new VentaNegocio();
          this.doc = new VentaNegocio();
          this.clienteNegocio = new ClienteNegocio();

          // Inicializar sesión por defecto si no hay una activa
          SessionManager sessionManager = SessionManager.getInstance();
          if (!sessionManager.isLoggedIn()) {
              sessionManager.initializeDefaultSession();
          }

          cargarTipoComprobante();
          configurarBusquedaCliente();
          inicializarTablaProductos();
          
          // Configurar apariencia de las tablas con fuentes más grandes
          UIConfig.configurarTabla(TblVenta);
          UIConfig.configurarTabla(TblProducto);
          
          // Configurar listener para cargar ventas cuando se muestre la pestaña
          TabVenta.addChangeListener(new javax.swing.event.ChangeListener() {
              public void stateChanged(javax.swing.event.ChangeEvent evt) {
                  if (TabVenta.getSelectedIndex() == 0) {
                      // Pestaña Listado seleccionada, recargar ventas
                      listar("");
                  }
              }
          });
          
          // Cargar ventas inicialmente
          this.listar("");

          this.primeraCarga=false;
          //TabListado.setEnabledAt(1, false);
          //this.accion="guardar"; // Removed unused assignment
          //this.crearDetalles();
     }
    
    /**
     * Configura el campo de búsqueda de cliente con autocompletado
     */
    private void configurarBusquedaCliente() {
        // Hacer el campo TxtNombreMant editable para búsqueda
        TxtNombreMant.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String texto = TxtNombreMant.getText().trim();
                
                // Solo buscar si hay 3 o más caracteres
                if (texto.length() >= 3) {
                    buscarClientes(texto);
                } else if (texto.length() == 0) {
                    // Limpiar cuando se borra todo
                    TxtId.setText("");
                    id_cliente_seleccionado = 0;
                }
            }
        });
    }
    
    /**
     * Busca clientes que coincidan con el texto ingresado
     */
    private void buscarClientes(String texto) {
        try {
            List<Cliente> clientes = clienteNegocio.seleccionar();
            
            // Filtrar clientes que contengan el texto en nombre o documento
            List<Cliente> clientesFiltrados = new java.util.ArrayList<>();
            for (Cliente c : clientes) {
                String nombreCompleto = c.getRazon_social().toLowerCase();
                String documento = c.getNumero_documento().toLowerCase();
                String busqueda = texto.toLowerCase();
                
                if (nombreCompleto.contains(busqueda) || documento.contains(busqueda)) {
                    clientesFiltrados.add(c);
                }
            }
            
            // Si hay resultados, mostrar en un diálogo de selección
            if (!clientesFiltrados.isEmpty() && clientesFiltrados.size() <= 10) {
                // Si hay pocos resultados, podemos mostrarlos automáticamente
                // Por ahora, el usuario puede usar el botón "..." para ver la lista completa
            }
            
        } catch (Exception ex) {
            System.err.println("Error al buscar clientes: " + ex.getMessage());
        }
    }

   
    private void listar(String texto) {
        TblVenta.setModel(this.CONTROL.listar(texto));
    }
    
    
    
    
     private void cargarTipoComprobante() {
        CbxTipoComprobante.removeAllItems();
        CbxTipoComprobante.addItem("--SELECCIONE--");
        for (String t : doc.listarTipoComprobante()) {
        CbxTipoComprobante.addItem(t);
        }
        CbxTipoComprobante.setSelectedIndex(0);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabVenta = new javax.swing.JTabbedPane();
        TabListado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TxtNombre = new javax.swing.JTextField();
        BtnBuscar = new javax.swing.JButton();
        BtnNuevVenta = new javax.swing.JButton();
        BtnVerVenta = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblVenta = new javax.swing.JTable();
        BtnAnulaVenta = new javax.swing.JButton();
        TabMantenimiento = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        TxtId = new javax.swing.JTextField();
        TxtNombreMant = new javax.swing.JTextField();
        BtnSeleccionarCliente = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        TxtIgv = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        CbxTipoComprobante = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        TxtSerieComp = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        TxtNumeroComp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        TxtBuscarProducto = new javax.swing.JTextField();
        BtnVerProducto = new javax.swing.JButton();
        BtnQuitarProducto = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TblProducto = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        TxtSubtotal = new javax.swing.JTextField();
        TxtTotalImpuesto = new javax.swing.JTextField();
        TxtTotal = new javax.swing.JTextField();
        BtnGuardar = new javax.swing.JButton();
        BtnCancelar = new javax.swing.JButton();

        jLabel1.setText("Nombre");

        BtnBuscar.setText("Buscar");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });

        BtnNuevVenta.setText("Nueva Venta");
        BtnNuevVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevVentaActionPerformed(evt);
            }
        });

        BtnVerVenta.setText("Ver Venta");

        TblVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(TblVenta);

        BtnAnulaVenta.setText("Anular");

        javax.swing.GroupLayout TabListadoLayout = new javax.swing.GroupLayout(TabListado);
        TabListado.setLayout(TabListadoLayout);
        TabListadoLayout.setHorizontalGroup(
            TabListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabListadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TabListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TabListadoLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(TabListadoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(BtnBuscar)
                        .addGap(27, 27, 27)
                        .addComponent(BtnNuevVenta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 430, Short.MAX_VALUE)
                        .addComponent(BtnVerVenta)
                        .addGap(14, 14, 14))))
            .addGroup(TabListadoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(BtnAnulaVenta)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        TabListadoLayout.setVerticalGroup(
            TabListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabListadoLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(TabListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnBuscar)
                    .addComponent(BtnNuevVenta)
                    .addComponent(BtnVerVenta))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(BtnAnulaVenta)
                .addContainerGap(145, Short.MAX_VALUE))
        );

        TabVenta.addTab("Listado", TabListado);

        jLabel2.setText("Cliente (*)");

        BtnSeleccionarCliente.setText("...");
        BtnSeleccionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeleccionarClienteActionPerformed(evt);
            }
        });

        jLabel3.setText("Impuesto (*)");

        TxtIgv.setText("0.18");

        jLabel4.setText("Tipo Comprobante (*)");

        CbxTipoComprobante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Serie Comprobante");

        jLabel6.setText("Número Comprobante");

        jLabel7.setText("Producto");

        BtnVerProducto.setText("Ver");
        BtnVerProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnVerProductoActionPerformed(evt);
            }
        });

        BtnQuitarProducto.setText("Quitar");

        TblProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "CODIGO", "PRODUCTO", "STOCK", "CANTIDAD", "PRECIO", "DESCUENTO", "SUBTOTAL"
            }
        ));
        jScrollPane2.setViewportView(TblProducto);

        jLabel8.setText("Subtotal");

        jLabel9.setText("Total Impuesto");

        jLabel10.setText("Total");
        jLabel11 = new javax.swing.JLabel();
        jLabel11.setText("Método de Pago (*)");

        CbxMetodoPago = new javax.swing.JComboBox<>();
        CbxMetodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--SELECCIONE--", "EFECTIVO", "TARJETA", "TRANSFERENCIA" }));

        jLabel12 = new javax.swing.JLabel();
        jLabel12.setText("Observaciones");

        TxtObservaciones = new javax.swing.JTextField();

        BtnGuardar.setText("Guardar");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        BtnCancelar.setText("Cancelar");
        BtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TabMantenimientoLayout = new javax.swing.GroupLayout(TabMantenimiento);
        TabMantenimiento.setLayout(TabMantenimientoLayout);
        TabMantenimientoLayout.setHorizontalGroup(
            TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabMantenimientoLayout.createSequentialGroup()
                .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(TabMantenimientoLayout.createSequentialGroup()
                        .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(TabMantenimientoLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8)))
                            .addGroup(TabMantenimientoLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(BtnGuardar)
                                .addGap(18, 18, 18)
                                .addComponent(BtnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)))
                        .addGap(30, 30, 30)
                        .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TxtSubtotal)
                            .addComponent(TxtTotalImpuesto)
                            .addComponent(TxtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, TabMantenimientoLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addGroup(TabMantenimientoLayout.createSequentialGroup()
                                .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(TabMantenimientoLayout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(CbxTipoComprobante, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(TabMantenimientoLayout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(18, 18, 18)
                                        .addComponent(CbxMetodoPago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(TabMantenimientoLayout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(18, 18, 18)
                                        .addComponent(TxtObservaciones))
                                    .addGroup(TabMantenimientoLayout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(TxtNombreMant, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(TabMantenimientoLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(18, 18, 18)
                                        .addComponent(TxtBuscarProducto)))
                                .addGap(18, 18, 18)
                                .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(TabMantenimientoLayout.createSequentialGroup()
                                        .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(BtnSeleccionarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(TabMantenimientoLayout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(18, 18, 18)
                                                .addComponent(TxtIgv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(TxtSerieComp))
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(TxtNumeroComp, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(TabMantenimientoLayout.createSequentialGroup()
                                        .addComponent(BtnVerProducto)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(BtnQuitarProducto)))))))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        TabMantenimientoLayout.setVerticalGroup(
            TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabMantenimientoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtNombreMant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnSeleccionarCliente)
                    .addComponent(jLabel3)
                    .addComponent(TxtIgv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(CbxTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(TxtSerieComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(TxtNumeroComp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(CbxMetodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(TxtObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(TxtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnVerProducto)
                    .addComponent(BtnQuitarProducto))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(TxtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(TxtTotalImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(TabMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(TxtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnGuardar)
                    .addComponent(BtnCancelar))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        TabVenta.addTab("Mantenimiento", TabMantenimiento);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabVenta)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(TabVenta)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        // Buscar ventas por nombre de cliente
        String texto = TxtNombre.getText().trim();
        listar(texto);
    }
    
    private void BtnNuevVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevVentaActionPerformed
        TabVenta.setEnabledAt(1, true);
        TabVenta.setEnabledAt(0, false);
        TabVenta.setSelectedIndex(1);
        //this.accion="guardar"; // Removed unused assignment
        BtnGuardar.setText("Guardar");
        //this.obtenerNumero();
    }//GEN-LAST:event_BtnNuevVentaActionPerformed

    private void BtnSeleccionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeleccionarClienteActionPerformed
        // Abrir diálogo de selección de cliente
        Frm_Selecionar_Cliente frmSelCliente = new Frm_Selecionar_Cliente(contenedor, true);
        frmSelCliente.setVisible(true);
        
        // Obtener el cliente seleccionado
        if (frmSelCliente.getClienteSeleccionado() != null) {
            Cliente cliente = frmSelCliente.getClienteSeleccionado();
            TxtId.setText(String.valueOf(cliente.getId_cliente()));
            TxtNombreMant.setText(cliente.getRazon_social());
            id_cliente_seleccionado = cliente.getId_cliente();
        }
    }//GEN-LAST:event_BtnSeleccionarClienteActionPerformed

    private void BtnVerProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVerProductoActionPerformed
        Frm_Seleccionar_Producto frm =new Frm_Seleccionar_Producto(contenedor, this, true);
        frm.toFront();
    }//GEN-LAST:event_BtnVerProductoActionPerformed

    private void BtnQuitarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnQuitarProductoActionPerformed
        if (TblProducto.getSelectedRowCount() == 1) {
            int fila = TblProducto.getSelectedRow();
            String nombreProducto = TblProducto.getValueAt(fila, 2).toString();
            
            int respuesta = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de quitar el producto '" + nombreProducto + "' de la venta?", 
                "Confirmar", 
                JOptionPane.YES_NO_OPTION);
            
            if (respuesta == JOptionPane.YES_OPTION) {
                javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) TblProducto.getModel();
                modelo.removeRow(fila);
                calcularTotales();
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un producto de la tabla para quitar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BtnQuitarProductoActionPerformed

    /**
     * Agrega un producto a la tabla de detalle de venta
     */
    public void agregarProducto(int idProducto, String codigo, String nombre, int stock, 
                                int cantidad, double precio, double descuento, double subtotal) {
        // Obtener el modelo de la tabla
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) TblProducto.getModel();
        
        // Verificar si el producto ya está en la tabla
        boolean productoExiste = false;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            int idExistente = Integer.parseInt(modelo.getValueAt(i, 0).toString());
            if (idExistente == idProducto) {
                // El producto ya existe, actualizar cantidad
                int cantidadExistente = Integer.parseInt(modelo.getValueAt(i, 4).toString());
                int nuevaCantidad = cantidadExistente + cantidad;
                
                // Validar que no exceda el stock
                if (nuevaCantidad > stock) {
                    JOptionPane.showMessageDialog(this, 
                        "La cantidad total (" + nuevaCantidad + ") excede el stock disponible (" + stock + ")", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Actualizar la fila
                double nuevoSubtotal = (nuevaCantidad * precio) - descuento;
                modelo.setValueAt(nuevaCantidad, i, 4);
                modelo.setValueAt(nuevoSubtotal, i, 7);
                productoExiste = true;
                break;
            }
        }
        
        // Si el producto no existe, agregarlo como nueva fila
         if (!productoExiste) {
             // Validar que la cantidad no exceda el stock
             if (cantidad > stock) {
                 JOptionPane.showMessageDialog(this,
                     "La cantidad (" + cantidad + ") excede el stock disponible (" + stock + ")",
                     "Error",
                     JOptionPane.ERROR_MESSAGE);
                 return;
             }
             Object[] fila = new Object[8];
             fila[0] = idProducto;
             fila[1] = codigo;
             fila[2] = nombre;
             fila[3] = stock;
             fila[4] = cantidad;
             fila[5] = precio;
             fila[6] = descuento;
             fila[7] = subtotal;
             modelo.addRow(fila);
         }
        
        // Actualizar totales
        calcularTotales();
    }
    
    /**
     * Calcula los totales de la venta
     */
    private void calcularTotales() {
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) TblProducto.getModel();
        double subtotal = 0.0;
        
        // Sumar todos los subtotales
        for (int i = 0; i < modelo.getRowCount(); i++) {
            double subtotalItem = Double.parseDouble(modelo.getValueAt(i, 7).toString());
            subtotal += subtotalItem;
        }
        
        // Calcular IGV
        double igv = 0.18; // Por defecto 18%
        try {
            igv = Double.parseDouble(TxtIgv.getText());
        } catch (NumberFormatException e) {
            igv = 0.18;
        }
        
        double totalImpuesto = subtotal * igv;
        double total = subtotal + totalImpuesto;
        
        // Actualizar campos
        TxtSubtotal.setText(String.format("%.2f", subtotal));
        TxtTotalImpuesto.setText(String.format("%.2f", totalImpuesto));
        TxtTotal.setText(String.format("%.2f", total));
    }
    
    /**
     * Inicializa la tabla de productos con el modelo correcto
     */
    private void inicializarTablaProductos() {
        String[] columnas = {"ID", "CODIGO", "PRODUCTO", "STOCK", "CANTIDAD", "PRECIO", "DESCUENTO", "SUBTOTAL"};
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo permitir editar cantidad y descuento
                return column == 4 || column == 6;
            }
        };
        TblProducto.setModel(modelo);
    }
    
    /**
     * Maneja el evento del botón Guardar para registrar la venta
     */
    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("=== INICIO REGISTRO DE VENTA ===");
        
        // Validar que haya un cliente seleccionado
        System.out.println("LOG: ID Cliente seleccionado: " + id_cliente_seleccionado);
        if (id_cliente_seleccionado == 0) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un cliente para la venta", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validar que haya productos en la tabla
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) TblProducto.getModel();
        System.out.println("LOG: Productos en tabla: " + modelo.getRowCount());
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Debe agregar al menos un producto a la venta", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validar tipo de comprobante
        if (CbxTipoComprobante.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un tipo de comprobante", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validar serie y número
        String serie = TxtSerieComp.getText().trim();
        String numero = TxtNumeroComp.getText().trim();
        if (serie.isEmpty() || numero.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Debe ingresar la serie y número del comprobante", 
                "Validación", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            System.out.println("LOG: Creando objeto Venta...");
            // Crear objeto Venta
            Venta venta = new Venta();
            System.out.println("LOG: Tipo comprobante: " + CbxTipoComprobante.getSelectedItem());
            venta.setTipoComprobante(CbxTipoComprobante.getSelectedItem().toString());
            venta.setSerieComprobante(serie);
            venta.setNumeroComprobante(numero);
            venta.setFechaVenta(java.time.LocalDate.now());
            venta.setHoraVenta(java.time.LocalTime.now());
            venta.setIdTienda(1); // TODO: Obtener de sesión o configuración
            venta.setIdCliente(id_cliente_seleccionado);
            venta.setIdUsuarioVendedor(1); // TODO: Obtener de sesión
            
            // Obtener valores de los campos
            double subtotal = Double.parseDouble(TxtSubtotal.getText());
            double totalImpuesto = Double.parseDouble(TxtTotalImpuesto.getText());
            double total = Double.parseDouble(TxtTotal.getText());
            
            venta.setSubtotal(subtotal);
            venta.setDescuento(0.0); // Por ahora sin descuento global
            venta.setIgv(totalImpuesto);
            venta.setTotal(total);
            venta.setMetodoPago("EFECTIVO"); // TODO: Agregar campo para método de pago
            venta.setObservaciones(""); // TODO: Agregar campo para observaciones
            
            // Crear lista de detalles
            List<DetalleVenta> detalles = new ArrayList<>();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setIdProducto(Integer.parseInt(modelo.getValueAt(i, 0).toString()));
                detalle.setCantidad(Integer.parseInt(modelo.getValueAt(i, 4).toString()));
                detalle.setPrecioUnitario(Double.parseDouble(modelo.getValueAt(i, 5).toString()));
                detalle.setDescuentoItem(Double.parseDouble(modelo.getValueAt(i, 6).toString()));
                detalles.add(detalle);
            }

            System.out.println("LOG: Detalles creados: " + detalles.size());
            System.out.println("LOG: Llamando a registrarVentaCompleta...");
            
            // Registrar la venta usando el stored procedure
            String resultado = CONTROL.registrarVentaCompleta(venta, detalles);
            
            System.out.println("LOG: Resultado: " + resultado);
            
            if ("OK".equals(resultado)) {
                JOptionPane.showMessageDialog(this, 
                    "Venta registrada exitosamente\nID Venta: " + venta.getIdVenta(), 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Limpiar formulario
                limpiarFormulario();
                
                // Volver a la pestaña de listado
                TabVenta.setEnabledAt(0, true);
                TabVenta.setSelectedIndex(0);
                TabVenta.setEnabledAt(1, false);
                
                // Actualizar listado
                listar("");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al registrar la venta:\n" + resultado, 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error en los datos numéricos: " + (e.getMessage() != null ? e.getMessage() : e.toString()), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            System.out.println("ERROR Exception: " + e.getClass().getName());
            System.out.println("ERROR Message: " + e.getMessage());
            e.printStackTrace();
            String errorMsg = "Error al registrar la venta:\n";
            if (e.getMessage() != null) {
                errorMsg += e.getMessage();
            } else {
                errorMsg += e.getClass().getName();
                if (e.getCause() != null) {
                    errorMsg += "\nCausa: " + e.getCause().getMessage();
                }
            }
            JOptionPane.showMessageDialog(this, 
                errorMsg, 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Maneja el evento del botón Cancelar
     */
    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        int respuesta = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de cancelar la venta? Se perderán todos los datos ingresados.", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION);
        
        if (respuesta == JOptionPane.YES_OPTION) {
            limpiarFormulario();
            TabVenta.setEnabledAt(0, true);
            TabVenta.setSelectedIndex(0);
            TabVenta.setEnabledAt(1, false);
        }
    }
    
    /**
     * Limpia todos los campos del formulario
     */
    private void limpiarFormulario() {
        TxtId.setText("");
        TxtNombreMant.setText("");
        id_cliente_seleccionado = 0;
        CbxTipoComprobante.setSelectedIndex(0);
        CbxMetodoPago.setSelectedIndex(0);
        TxtSerieComp.setText("");
        TxtNumeroComp.setText("");
        TxtBuscarProducto.setText("");
        TxtObservaciones.setText("");
        TxtSubtotal.setText("");
        TxtTotalImpuesto.setText("");
        TxtTotal.setText("");

        // Limpiar tabla de productos
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) TblProducto.getModel();
        modelo.setRowCount(0);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAnulaVenta;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnNuevVenta;
    private javax.swing.JButton BtnQuitarProducto;
    private javax.swing.JButton BtnSeleccionarCliente;
    private javax.swing.JButton BtnVerProducto;
    private javax.swing.JButton BtnVerVenta;
    private javax.swing.JComboBox<String> CbxTipoComprobante;
    private javax.swing.JPanel TabListado;
    private javax.swing.JPanel TabMantenimiento;
    private javax.swing.JTabbedPane TabVenta;
    private javax.swing.JTable TblProducto;
    private javax.swing.JTable TblVenta;
    private javax.swing.JTextField TxtBuscarProducto;
    private javax.swing.JTextField TxtId;
    private javax.swing.JTextField TxtIgv;
    private javax.swing.JTextField TxtNombre;
    private javax.swing.JTextField TxtNombreMant;
    private javax.swing.JTextField TxtNumeroComp;
    private javax.swing.JTextField TxtSerieComp;
    private javax.swing.JTextField TxtSubtotal;
    private javax.swing.JTextField TxtTotal;
    private javax.swing.JTextField TxtTotalImpuesto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> CbxMetodoPago;
    private javax.swing.JTextField TxtObservaciones;
    // End of variables declaration//GEN-END:variables
}
