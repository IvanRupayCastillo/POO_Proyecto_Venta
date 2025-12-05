package proyecto_sistema_venta;

import proyecto_sistema_venta.Presentacion.FrmLogin;
import proyecto_sistema_venta.Presentacion.UIConfig;

public class Main {
    public static void main(String[] args) {
        // Configurar fuentes más grandes y apariencia mejorada
        UIConfig.configurarFuentesGlobales();
        
        // Iniciar con el formulario de login
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }
}

