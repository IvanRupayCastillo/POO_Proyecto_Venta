package proyecto_sistema_venta.Datos.Interfaces;

import proyecto_sistema_venta.Entidades.Cliente;
import java.util.List;

/**
 * Interfaz ICliente - Define las operaciones CRUD para la entidad Cliente
 */
public interface ICliente {
    
    public List<Cliente> listar(String texto);
    public boolean insertar(Cliente obj);
    public boolean actualizar(Cliente obj);
    public boolean desactivar(int id);
    public boolean activar(int id);
    public int total();
    public boolean existe(String numeroDocumento);
    public Cliente buscarPorDocumento(String numeroDocumento);
}
