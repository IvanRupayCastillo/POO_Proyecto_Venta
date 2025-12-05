package proyecto_sistema_venta;

import proyecto_sistema_venta.Presentacion.FrmMenu;

public class Main {
    public static void main(String[] args) {
        // Iniciar la interfaz gráfica de usuario (GUI)
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMenu().setVisible(true);
            }
        });
    }
}

