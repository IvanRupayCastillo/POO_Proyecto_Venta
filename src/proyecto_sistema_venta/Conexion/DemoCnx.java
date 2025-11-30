/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_sistema_venta.Conexion;

/**
 *
 * @author User
 */
public class DemoCnx {
    public static void main(String [] args){
       Conexion cnx = new Conexion();
       cnx.conectar();
        if (cnx.cnx != null) {
            System.out.print("Conexion exitosa");
        } else {
           System.out.print("Conexion fallida");
       }
    }
     
}
