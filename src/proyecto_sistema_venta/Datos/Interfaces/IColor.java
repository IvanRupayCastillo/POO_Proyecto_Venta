package proyecto_sistema_venta.Datos.Interfaces;

import proyecto_sistema_venta.Entidades.Color;
import java.util.List;

/**
 * Interfaz IColor - Define las operaciones CRUD para la entidad Color
 */
public interface IColor {
    
    public List<Color> listar(String texto);
    public boolean insertar(Color obj);
    public boolean actualizar(Color obj);
    public boolean desactivar(int id);
    public boolean activar(int id);
    public int total();
    public boolean existe(String texto);
}
