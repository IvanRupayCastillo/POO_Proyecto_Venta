package proyecto_sistema_venta.Presentacion;

import proyecto_sistema_venta.Negocio.ProductoNegocio;
import proyecto_sistema_venta.Negocio.TipoProductoNegocio;
import proyecto_sistema_venta.Negocio.ColorNegocio;
import proyecto_sistema_venta.Negocio.TallaNegocio;
import proyecto_sistema_venta.Entidades.TipoProducto;
import proyecto_sistema_venta.Entidades.Color;
import proyecto_sistema_venta.Entidades.Talla;
import javax.swing.JOptionPane;
import javax.swing.table.TableRowSorter;
import java.util.List;

public class FrmProductos extends javax.swing.JInternalFrame {

    private final ProductoNegocio CONTROL;
    private final TipoProductoNegocio TIPO_CONTROL;
    private final ColorNegocio COLOR_CONTROL;
    private final TallaNegocio TALLA_CONTROL;
    private String accion;
    private String codigoAnt;
    
    // Listas para mapear índices de combo con IDs
    private List<TipoProducto> listaTipos;
    private List<Color> listaColores;
    private List<Talla> listaTallas;
    
    public FrmProductos() {
        this.CONTROL = new ProductoNegocio();
        this.TIPO_CONTROL = new TipoProductoNegocio();
        this.COLOR_CONTROL = new ColorNegocio();
        this.TALLA_CONTROL = new TallaNegocio();
        this.accion = "guardar";
        
        initComponents();
        
        try {
            this.cargarCombos();
            this.listar("");
            this.ocultarColumnas();
            TxtTotal.setText("Total de registros: " + this.CONTROL.total());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void cargarCombos() {
        try {
            // Cargar Tipos de Producto
            CmbTipo.removeAllItems();
            listaTipos = TIPO_CONTROL.seleccionar();
            if (listaTipos != null && !listaTipos.isEmpty()) {
                for (TipoProducto tipo : listaTipos) {
                    CmbTipo.addItem(tipo.getNombreTipo());
                }
            } else {
                CmbTipo.addItem("Sin tipos disponibles");
            }
            
            // Cargar Colores
            CmbColor.removeAllItems();
            listaColores = COLOR_CONTROL.seleccionar();
            if (listaColores != null && !listaColores.isEmpty()) {
                for (Color color : listaColores) {
                    CmbColor.addItem(color.getNombreColor());
                }
            } else {
                CmbColor.addItem("Sin colores disponibles");
            }
            
            // Cargar Tallas
            CmbTalla.removeAllItems();
            listaTallas = TALLA_CONTROL.seleccionar();
            if (listaTallas != null && !listaTallas.isEmpty()) {
                for (Talla talla : listaTallas) {
                    CmbTalla.addItem(talla.getNombreTalla());
                }
            } else {
                CmbTalla.addItem("Sin tallas disponibles");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar combos: " + e.getMessage());
            e.printStackTrace();
            // Agregar items por defecto en caso de error
            if (CmbTipo.getItemCount() == 0) CmbTipo.addItem("Error al cargar");
            if (CmbColor.getItemCount() == 0) CmbColor.addItem("Error al cargar");
            if (CmbTalla.getItemCount() == 0) CmbTalla.addItem("Error al cargar");
        }
    }

    private void ocultarColumnas() {
        try {
            if (TblDatos.getColumnCount() > 0) {
                TblDatos.getColumnModel().getColumn(0).setMaxWidth(0);
                TblDatos.getColumnModel().getColumn(0).setMinWidth(0);
                TblDatos.getColumnModel().getColumn(0).setPreferredWidth(0);
            }
            // Ocultar IDs de tipo, color, talla
            if (TblDatos.getColumnCount() > 6) {
                TblDatos.getColumnModel().getColumn(4).setMaxWidth(0);
                TblDatos.getColumnModel().getColumn(4).setMinWidth(0);
                TblDatos.getColumnModel().getColumn(5).setMaxWidth(0);
                TblDatos.getColumnModel().getColumn(5).setMinWidth(0);
                TblDatos.getColumnModel().getColumn(6).setMaxWidth(0);
                TblDatos.getColumnModel().getColumn(6).setMinWidth(0);
            }
        } catch (Exception e) {
            System.err.println("Error al ocultar columnas: " + e.getMessage());
        }
    }

    private void listar(String texto) {
        TblDatos.setModel(this.CONTROL.listar(texto));
        TableRowSorter orden = new TableRowSorter(TblDatos.getModel());
        TblDatos.setRowSorter(orden);
        this.ocultarColumnas();
        LblTotalRegistros.setText("Total de registros: " + this.CONTROL.total());
    }

    private void limpiar() {
        TxtCodigo.setText("");
        TxtNombre.setText("");
        TxtDescripcion.setText("");
        TxtPrecioCompra.setText("0.00");
        TxtPrecioVentaMayor.setText("0.00");
        TxtPrecioVentaMenor.setText("0.00");
        TxtStockMinimo.setText("0");
        TxtId.setText("");
        if (CmbTipo.getItemCount() > 0) CmbTipo.setSelectedIndex(0);
        if (CmbColor.getItemCount() > 0) CmbColor.setSelectedIndex(0);
        if (CmbTalla.getItemCount() > 0) CmbTalla.setSelectedIndex(0);
        ChkActivo.setSelected(true);
        this.accion = "guardar";
    }

    private void habilitar(boolean valor) {
        TxtCodigo.setEnabled(valor);
        TxtNombre.setEnabled(valor);
        TxtDescripcion.setEnabled(valor);
        CmbTipo.setEnabled(valor);
        CmbColor.setEnabled(valor);
        CmbTalla.setEnabled(valor);
        TxtPrecioCompra.setEnabled(valor);
        TxtPrecioVentaMayor.setEnabled(valor);
        TxtPrecioVentaMenor.setEnabled(valor);
        TxtStockMinimo.setEnabled(valor);
        ChkActivo.setEnabled(valor);
        BtnGuardar.setEnabled(valor);
        BtnCancelar.setEnabled(valor);
    }

    private void mostrarBotones(boolean valor) {
        BtnNuevo.setVisible(!valor);
        BtnEditar.setVisible(!valor);
        BtnAnular.setVisible(!valor);
    }

    @SuppressWarnings("unchecked")
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
        jLabel4 = new javax.swing.JLabel();
        TxtDescripcion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        CmbTipo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        CmbColor = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        CmbTalla = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        TxtPrecioCompra = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        TxtPrecioVentaMayor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        TxtPrecioVentaMenor = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        TxtStockMinimo = new javax.swing.JTextField();
        ChkActivo = new javax.swing.JCheckBox();
        BtnGuardar = new javax.swing.JButton();
        BtnCancelar = new javax.swing.JButton();
        TxtId = new javax.swing.JTextField();
        TxtTotal = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Productos");

        TblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {}
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
        jLabel4.setText("Descripción:");
        jLabel5.setText("Tipo (*):");
        jLabel6.setText("Color (*):");
        jLabel7.setText("Talla (*):");
        jLabel8.setText("Precio Compra (*):");
        jLabel9.setText("Precio Venta Mayor (*):");
        jLabel10.setText("Precio Venta Menor (*):");
        jLabel11.setText("Stock Mínimo (*):");

        TxtPrecioCompra.setText("0.00");
        TxtPrecioVentaMayor.setText("0.00");
        TxtPrecioVentaMenor.setText("0.00");
        TxtStockMinimo.setText("0");

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
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TxtCodigo)
                            .addComponent(TxtNombre)
                            .addComponent(TxtDescripcion)
                            .addComponent(CmbTipo, 0, 250, Short.MAX_VALUE)
                            .addComponent(CmbColor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CmbTalla, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TxtPrecioCompra)
                            .addComponent(TxtPrecioVentaMayor)
                            .addComponent(TxtPrecioVentaMenor)
                            .addComponent(TxtStockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(200, Short.MAX_VALUE))
        );
        PnlMantenimientoLayout.setVerticalGroup(
            PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlMantenimientoLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TxtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(TxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(CmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(CmbColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(CmbTalla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(TxtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(TxtPrecioVentaMayor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(TxtPrecioVentaMenor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(TxtStockMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(ChkActivo)
                .addGap(10, 10, 10)
                .addGroup(PnlMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnGuardar)
                    .addComponent(BtnCancelar))
                .addContainerGap(20, Short.MAX_VALUE))
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
        if (CmbTipo.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo", "Sistema", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (CmbColor.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un color", "Sistema", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (CmbTalla.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una talla", "Sistema", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double precioCompra, precioVentaMayor, precioVentaMenor;
        int stockMinimo;
        try {
            precioCompra = Double.parseDouble(TxtPrecioCompra.getText());
            precioVentaMayor = Double.parseDouble(TxtPrecioVentaMayor.getText());
            precioVentaMenor = Double.parseDouble(TxtPrecioVentaMenor.getText());
            stockMinimo = Integer.parseInt(TxtStockMinimo.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los precios y stock deben ser valores numéricos válidos", "Sistema", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener IDs de los combos
        int idTipo = listaTipos != null && CmbTipo.getSelectedIndex() >= 0 && CmbTipo.getSelectedIndex() < listaTipos.size() 
                     ? listaTipos.get(CmbTipo.getSelectedIndex()).getIdTipo() : 1;
        int idColor = listaColores != null && CmbColor.getSelectedIndex() >= 0 && CmbColor.getSelectedIndex() < listaColores.size() 
                      ? listaColores.get(CmbColor.getSelectedIndex()).getIdColor() : 1;
        int idTalla = listaTallas != null && CmbTalla.getSelectedIndex() >= 0 && CmbTalla.getSelectedIndex() < listaTallas.size() 
                      ? listaTallas.get(CmbTalla.getSelectedIndex()).getIdTalla() : 1;

        String resp;
        if (this.accion.equals("guardar")) {
            resp = this.CONTROL.insertar(
                TxtCodigo.getText(),
                TxtNombre.getText(),
                TxtDescripcion.getText(),
                idTipo,
                idColor,
                idTalla,
                precioCompra,
                precioVentaMayor,
                precioVentaMenor,
                stockMinimo,
                true
            );
            if (resp.equals("OK")) {
                this.limpiar();
                this.listar("");
                JOptionPane.showMessageDialog(this, "Producto registrado correctamente", "Sistema", JOptionPane.INFORMATION_MESSAGE);
                TabGeneral.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, resp, "Sistema", JOptionPane.WARNING_MESSAGE);
            }
        } else if (this.accion.equals("editar")) {
            resp = this.CONTROL.actualizar(
                Integer.parseInt(TxtId.getText()),
                TxtCodigo.getText(),
                TxtNombre.getText(),
                TxtDescripcion.getText(),
                idTipo,
                idColor,
                idTalla,
                precioCompra,
                precioVentaMayor,
                precioVentaMenor,
                stockMinimo,
                ChkActivo.isSelected(),
                this.codigoAnt
            );
            if (resp.equals("OK")) {
                this.limpiar();
                this.listar("");
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente", "Sistema", JOptionPane.INFORMATION_MESSAGE);
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
            String descripcion = String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 3));
            int idTipo = Integer.parseInt(String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 4)));
            int idColor = Integer.parseInt(String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 5)));
            int idTalla = Integer.parseInt(String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 6)));
            String precioCompra = String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 7));
            String precioVentaMayor = String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 8));
            String precioVentaMenor = String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 9));
            String stockMinimo = String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 10));
            String estado = String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 11));

            TxtId.setText(id);
            TxtCodigo.setText(codigo);
            this.codigoAnt = codigo;
            TxtNombre.setText(nombre);
            TxtDescripcion.setText(descripcion);
            
            // Seleccionar tipo por ID
            for (int i = 0; i < listaTipos.size(); i++) {
                if (listaTipos.get(i).getIdTipo() == idTipo) {
                    CmbTipo.setSelectedIndex(i);
                    break;
                }
            }
            
            // Seleccionar color por ID
            for (int i = 0; i < listaColores.size(); i++) {
                if (listaColores.get(i).getIdColor() == idColor) {
                    CmbColor.setSelectedIndex(i);
                    break;
                }
            }
            
            // Seleccionar talla por ID
            for (int i = 0; i < listaTallas.size(); i++) {
                if (listaTallas.get(i).getIdTalla() == idTalla) {
                    CmbTalla.setSelectedIndex(i);
                    break;
                }
            }
            
            TxtPrecioCompra.setText(precioCompra);
            TxtPrecioVentaMayor.setText(precioVentaMayor);
            TxtPrecioVentaMenor.setText(precioVentaMenor);
            TxtStockMinimo.setText(stockMinimo);
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
            String estado = String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 11));
            String nombre = String.valueOf(TblDatos.getValueAt(TblDatos.getSelectedRow(), 2));

            if (JOptionPane.showConfirmDialog(this, "¿Desea cambiar el estado del producto " + nombre + "?", "Sistema", JOptionPane.YES_NO_OPTION) == 0) {
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
    private javax.swing.JComboBox<String> CmbTipo;
    private javax.swing.JComboBox<String> CmbColor;
    private javax.swing.JComboBox<String> CmbTalla;
    private javax.swing.JLabel LblTotalRegistros;
    private javax.swing.JPanel PnlListado;
    private javax.swing.JPanel PnlMantenimiento;
    private javax.swing.JTabbedPane TabGeneral;
    private javax.swing.JTable TblDatos;
    private javax.swing.JTextField TxtBuscar;
    private javax.swing.JTextField TxtCodigo;
    private javax.swing.JTextField TxtDescripcion;
    private javax.swing.JTextField TxtId;
    private javax.swing.JTextField TxtNombre;
    private javax.swing.JTextField TxtPrecioCompra;
    private javax.swing.JTextField TxtPrecioVentaMayor;
    private javax.swing.JTextField TxtPrecioVentaMenor;
    private javax.swing.JTextField TxtStockMinimo;
    private javax.swing.JLabel TxtTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration
}
