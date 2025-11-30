
package proyecto_sistema_venta.Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/";
    private final String DB ="sistema_ventas_almacen";
    private final String USER ="root";
    private final String PASSWORD ="290405Gra$";
    
    public Connection cnx;
    public static Conexion instancia;
    
    public Conexion (){
        this.cnx = null;
    }
    
    public Connection conectar(){
        try {
            Class.forName(DRIVER);  
            this.cnx =DriverManager.getConnection(URL+DB,USER,PASSWORD);
        } catch (ClassNotFoundException| SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
        return this.cnx;
    }
    
    public void desconectar(){
        try {
            this.cnx.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
    }
    
    //patron singleton
    public synchronized static Conexion getInstancia(){
        if(instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }
       
}