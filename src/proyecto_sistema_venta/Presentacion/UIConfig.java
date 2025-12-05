package proyecto_sistema_venta.Presentacion;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

/**
 * Clase de utilidad para configurar la apariencia de la interfaz gráfica
 * Aumenta el tamaño de las fuentes para mejor legibilidad
 */
public class UIConfig {
    
    // Tamaños de fuente
    private static final int FONT_SIZE_NORMAL = 14;
    private static final int FONT_SIZE_LARGE = 16;
    private static final int FONT_SIZE_TITLE = 18;
    private static final int FONT_SIZE_HEADER = 20;
    
    /**
     * Configura las fuentes globales de la aplicación
     */
    public static void configurarFuentesGlobales() {
        try {
            // Configurar el Look and Feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Aumentar el tamaño de todas las fuentes
            setUIFont(new FontUIResource("Segoe UI", Font.PLAIN, FONT_SIZE_NORMAL));
            
            // Configuraciones específicas para componentes
            UIManager.put("Table.font", new FontUIResource("Segoe UI", Font.PLAIN, FONT_SIZE_NORMAL));
            UIManager.put("Table.rowHeight", 28);
            UIManager.put("TableHeader.font", new FontUIResource("Segoe UI", Font.BOLD, FONT_SIZE_NORMAL));
            
            UIManager.put("Button.font", new FontUIResource("Segoe UI", Font.BOLD, FONT_SIZE_NORMAL));
            UIManager.put("Label.font", new FontUIResource("Segoe UI", Font.PLAIN, FONT_SIZE_NORMAL));
            UIManager.put("TextField.font", new FontUIResource("Segoe UI", Font.PLAIN, FONT_SIZE_NORMAL));
            UIManager.put("TextArea.font", new FontUIResource("Segoe UI", Font.PLAIN, FONT_SIZE_NORMAL));
            UIManager.put("ComboBox.font", new FontUIResource("Segoe UI", Font.PLAIN, FONT_SIZE_NORMAL));
            
            UIManager.put("Menu.font", new FontUIResource("Segoe UI", Font.PLAIN, FONT_SIZE_LARGE));
            UIManager.put("MenuItem.font", new FontUIResource("Segoe UI", Font.PLAIN, FONT_SIZE_NORMAL));
            UIManager.put("MenuBar.font", new FontUIResource("Segoe UI", Font.PLAIN, FONT_SIZE_LARGE));
            
            UIManager.put("TitledBorder.font", new FontUIResource("Segoe UI", Font.BOLD, FONT_SIZE_LARGE));
            
            UIManager.put("OptionPane.messageFont", new FontUIResource("Segoe UI", Font.PLAIN, FONT_SIZE_NORMAL));
            UIManager.put("OptionPane.buttonFont", new FontUIResource("Segoe UI", Font.BOLD, FONT_SIZE_NORMAL));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Establece una fuente para todos los componentes de Swing
     */
    private static void setUIFont(FontUIResource font) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, font);
            }
        }
    }
    
    /**
     * Configura una tabla con estilos mejorados
     */
    public static void configurarTabla(JTable tabla) {
        // Fuente más grande
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, FONT_SIZE_NORMAL));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, FONT_SIZE_NORMAL));
        
        // Altura de filas
        tabla.setRowHeight(28);
        
        // Colores alternados
        tabla.setShowGrid(true);
        tabla.setGridColor(new Color(230, 230, 230));
        
        // Centrar el contenido de las celdas
        tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(245, 245, 245));
                    }
                }
                
                return c;
            }
        });
    }
    
    /**
     * Crea un botón con estilo mejorado
     */
    public static JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, FONT_SIZE_NORMAL));
        boton.setPreferredSize(new Dimension(150, 35));
        boton.setFocusPainted(false);
        return boton;
    }
    
    /**
     * Crea un label con estilo de título
     */
    public static JLabel crearTitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, FONT_SIZE_HEADER));
        return label;
    }
    
    /**
     * Crea un label con estilo de subtítulo
     */
    public static JLabel crearSubtitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, FONT_SIZE_TITLE));
        return label;
    }
    
    /**
     * Crea un campo de texto con estilo mejorado
     */
    public static JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, FONT_SIZE_NORMAL));
        campo.setPreferredSize(new Dimension(200, 30));
        return campo;
    }
}
