package proyecto_sistema_venta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExecuteSP {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/sistema_ventas_almacen";
        String user = "root";
        String password = "";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            
            // Leer el archivo SQL
            String sqlScript = new String(Files.readAllBytes(Paths.get("sp_registrar_venta_completa.sql")));
            
            // Dividir por DELIMITER y ejecutar
            String[] statements = sqlScript.split("//");
            Statement stmt = conn.createStatement();
            
            for (String sqlStatement : statements) {
                sqlStatement = sqlStatement.trim();
                if (!sqlStatement.isEmpty() && !sqlStatement.startsWith("DELIMITER")) {
                    try {
                        stmt.execute(sqlStatement);
                        System.out.println("✓ Ejecutado: " + sqlStatement.substring(0, Math.min(50, sqlStatement.length())));
                    } catch (SQLException e) {
                        System.err.println("✗ Error: " + e.getMessage());
                    }
                }
            }
            
            stmt.close();
            conn.close();
            System.out.println("\n✓ Stored Procedure creado exitosamente!");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
