
package proyecto_sistema_venta.Presentacion;

import proyecto_sistema_venta.Negocio.ColorNegocio;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class FrmColor extends  javax.swing.JInternalFrame {

    private final ColorNegocio CONTROL;
    private String accion;
    private String nombreAnt;
    
    /**
     * Constructor
     */
    public FrmColor() {
        initComponents();
        this.CONTROL = new ColorNegocio();
        this.accion = "guardar";
        this.listar("");
        this.ocultarColumnas();
        TxtTotal.setText("Total de registros: " + this.CONTROL.total());
    }

    /**
     * Oculta la columna ID de la tabla
     */
    private void ocultarColumnas() {
        TblDatos.getColumnModel().getColumn(0).setMaxWidth(0);
        TblDatos.getColumnModel().getColumn(0).setMinWidth(0);
        TblDatos.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    /**
     * Lista los colores en la tabla
     */
    private void listar(String texto) {
        TblDatos.setModel(this.CONTROL.listar(texto));
        TableRowSorter<TableModel> orden = new TableRowSorter<>(TblDatos.getModel());
        TblDatos.setRowSorter(orden);
        this.ocultarColumnas();
        LblTotalRegistros.setText("Total de registros: " + this.CONTROL.total());
    }

    /**
     * Limpia los campos del formulario
     */
    private void limpiar() {
        TxtCodigo.setText("");
        TxtNombre.setText("");
        TxtId.setText("");
        ChkActivo.setSelected(true);
        this.accion = "guardar";
    }

    /**
     * Habilita los campos del formulario
     */
    private void habilitar(boolean valor) {
        TxtCodigo.setEnabled(valor);
        TxtNombre.setEnabled(valor);
        ChkActivo.setEnabled(valor);
        BtnGuardar.setEnabled(valor);
        BtnCancelar.setEnabled(valor);
    }

    /**
     * Muestra los botones de edición
     */
    private void mostrarBotones(boolean valor) {
        BtnNuevo.setVisible(!valor);
        BtnEditar.setVisible(!valor);
        BtnAnular.setVisible(!valor);
    }

    /**
     * Método de inicialización de componentes generado por el editor visual
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        TabGeneral = new javax.swing.JTabbedPane();
        PnlListado = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblDatos = new javax.swing.JTable();
        LblTotalRegistros = new javax.swing.JLabel();
        BtnBuscar = new javax.swing.JButton();
        TxtBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        BtnNuevo = new javax.swing.JButton();
        BtnEditar = new javax.swing.JButton();
        BtnAnular = new javax.swing.JButton();
        PnlMantenimiento = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        TxtCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TxtNombre = new javax.swing.JTextField();
        ChkActivo = new javax.swing.JCheckBox();
        BtnGuardar = new javax.swing.JButton();
        BtnCancelar = new javax.swing.JButton();
        TxtId = new javax.swing.JTextField();
        TxtTotal = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Colores");

        TblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        TblDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblDatosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TblDatos);

        LblTotalRegistros.setText("Total de registros:");

        BtnBuscar.setText("Buscar");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });

        jLabel1.setText("Buscar:");

        BtnNuevo.setText("Nuevo");
        BtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoActionPerformed(evt);
            }
        });

        BtnEditar.setText("Editar");
        BtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditarActionPerformed(evt);
            }
        });

        BtnAnular.setText("Anular");
        BtnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAnularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PnlListadoLayout = new javax.swing.GroupLayout(PnlListado);
        PnlListado.setLayout(PnlListadoLayout);
        PnlListadoLayout.setHorizontalGroup(
            PnlListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlListadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnlListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
                    .addGroup(PnlListadoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnBuscar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PnlListadoLayout.createSequentialGroup()
                        .addComponent(LblTotalRegistros)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnAnular)))
                .addContainerGap())
        );
        PnlListadoLayout.setVerticalGroup(
            PnlListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PnlListadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnlListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PnlListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LblTotalRegistros)
                    .addComponent(BtnNuevo)
                    .addComponent(BtnEditar)
                    .addComponent(BtnAnular))
                .addContainerGap())
        );

        TabGeneral.addTab("Listado", PnlListado);

        jLabel2.setText("Código (*):");

        jLabel3.setText("Nombre (*):");

        ChkActivo.setText("Activo");
        ChkActivo.setSelected(true);

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

        TxtId.setEnabled(false);

        javax.swing.GroupLayout PnlMantenimientoLayout = new javax.swing.GroupLayout(PnlMantenimiento);
        PnlMantenimiento.setLayout(PnlMantenimientoLayout);
        PnlMantenimientoLayout.setHorizontalGroup(
            PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlMantenimientoLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlMantenimientoLayout.createSequentialGroup()
                        .addComponent(BtnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnCancelar))
                    .addComponent(ChkActivo)
                    .addGroup(PnlMantenimientoLayout.createSequentialGroup()
                        .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TxtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(TxtNombre)
                            .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(347, Short.MAX_VALUE))
        );
        PnlMantenimientoLayout.setVerticalGroup(
            PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlMantenimientoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(ChkActivo)
                .addGap(18, 18, 18)
                .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnGuardar)
                    .addComponent(BtnCancelar))
                .addContainerGap(161, Short.MAX_VALUE))
        );

        TabGeneral.addTab("Mantenimiento", PnlMantenimiento);

        TxtTotal.setText("Total:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TabGeneral)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TxtTotal)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TxtTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TabGeneral)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        this.listar(TxtBuscar.getText());
    }                                          

    private void BtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {                                         
        TabGeneral.setSelectedIndex(1);
        this.limpiar();
        this.habilitar(true);
        this.mostrarBotones(true);
        BtnGuardar.setText("Guardar");
        this.accion = "guardar";
    }                                        

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        if (TxtCodigo.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un código", "Sistema", JOptionPane.WARNING_MESSAGE);
            TxtCodigo.requestFocus();
            return;
        }
        if (TxtNombre.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un nombre", "Sistema", JOptionPane.WARNING_MESSAGE);
            TxtNombre.requestFocus();
            return;
        }

        String resp;
        if (this.accion.equals("guardar")) {
            resp = this.CONTROL.insertar(TxtCodigo.getText(), TxtNombre.getText());
            if (resp.equals("OK")) {
                this.limpiar();
                this.listar("");
                JOptionPane.showMessageDialog(this, "Color registrado correctamente", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                TabGeneral.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, resp, "Sistema", JOptionPane.WARNING_MESSAGE);
            }
        } else if (this.accion.equals("editar")) {
            if (TxtNombre.getText().equals(this.nombreAnt)) {
                JOptionPane.showMessageDialog(this, "El nombre no ha cambiado", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            resp = this.CONTROL.actualizar(
                Integer.parseInt(TxtId.getText()),
                TxtCodigo.getText(),
                TxtNombre.getText(),
                ChkActivo.isSelected()
            );
            if (resp.equals("OK")) {
                this.limpiar();
                this.listar("");
                JOptionPane.showMessageDialog(this, "Color actualizado correctamente", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                TabGeneral.setSelectedIndex(0);
                this.habilitar(false);
                this.mostrarBotones(false);
            } else {
                JOptionPane.showMessageDialog(this, resp, "Sistema", JOptionPane.WARNING_MESSAGE);
            }
        }
    }                                          

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        TabGeneral.setSelectedIndex(0);
        this.limpiar();
        this.habilitar(false);
        this.mostrarBotones(false);
    }                                           

    private void TblDatosMouseClicked(java.awt.event.MouseEvent evt) {                                      
        // Implementar si se desea acción al hacer clic en la tabla
    }                                     

    private void BtnEditarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        if (TblDatos.getSelectedRowCount() == 1) {
            String id = String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 0));
            String codigo = String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 1));
            String nombre = String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 2));
            String estado = String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 3));

            TxtId.setText(id);
            TxtCodigo.setText(codigo);
            this.nombreAnt = nombre;
            TxtNombre.setText(nombre);
            ChkActivo.setSelected(estado.equals("Activo"));

            TabGeneral.setSelectedIndex(1);
            this.habilitar(true);
            this.mostrarBotones(true);
            BtnGuardar.setText("Actualizar");
            this.accion = "editar";
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla", "Sistema", JOptionPane.WARNING_MESSAGE);
        }
    }                                         

    private void BtnAnularActionPerformed(java.awt.event.ActionEvent evt) {                                          
        if (TblDatos.getSelectedRowCount() == 1) {
            String id = String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 0));
            String estado = String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 3));
            String nombre = String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 2));

            if (JOptionPane.showConfirmDialog(this, "¿Desea cambiar el estado del color " + nombre + "?", "Sistema", JOptionPane.YES_NO_OPTION) == 0) {
                String resp;
                if (estado.equals("Activo")) {
                    resp = this.CONTROL.desactivar(Integer.parseInt(id));
                } else {
                    resp = this.CONTROL.activar(Integer.parseInt(id));
                }
                if (resp.equals("OK")) {
                    this.listar("");
                    JOptionPane.showMessageDialog(this, "Estado cambiado correctamente", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, resp, "Sistema", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla", "Sistema", JOptionPane.WARNING_MESSAGE);
        }
    }                                         

    // Variables declaration - do not modify                     
    private javax.swing.JButton BtnAnular;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JButton BtnEditar;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnNuevo;
    private javax.swing.JCheckBox ChkActivo;
    private javax.swing.JLabel LblTotalRegistros;
    private javax.swing.JPanel PnlListado;
    private javax.swing.JPanel PnlMantenimiento;
    private javax.swing.JTabbedPane TabGeneral;
    private javax.swing.JTable TblDatos;
    private javax.swing.JTextField TxtBuscar;
    private javax.swing.JTextField TxtCodigo;
    private javax.swing.JTextField TxtId;
    private javax.swing.JTextField TxtNombre;
    private javax.swing.JLabel TxtTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration



}
