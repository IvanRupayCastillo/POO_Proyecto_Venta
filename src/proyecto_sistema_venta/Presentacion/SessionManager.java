package proyecto_sistema_venta.Presentacion;

import proyecto_sistema_venta.Entidades.Usuario;

/**
 * Clase para gestionar la sesión del usuario actual.
 *
 * @author Sistema de Ventas
 */
public class SessionManager {

    private static SessionManager instance;
    private Usuario usuarioActual;

    private SessionManager() {
        // Constructor privado para patrón Singleton
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * Inicia sesión con un usuario
     */
    public void login(Usuario usuario) {
        this.usuarioActual = usuario;
        System.out.println("DEBUG: Usuario logueado - " + usuario.getNombreCompleto());
    }

    /**
     * Cierra la sesión del usuario actual
     */
    public void logout() {
        this.usuarioActual = null;
        System.out.println("DEBUG: Sesión cerrada");
    }

    /**
     * Obtiene el usuario actualmente logueado
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Obtiene el ID del usuario actualmente logueado
     */
    public Integer getCurrentUserId() {
        return usuarioActual != null ? usuarioActual.getIdUsuario() : null;
    }

    /**
     * Obtiene el ID de la tienda del usuario actualmente logueado
     */
    public Integer getCurrentStoreId() {
        return usuarioActual != null ? usuarioActual.getIdTiendaAsignada() : null;
    }

    /**
     * Obtiene el nombre del usuario actualmente logueado
     */
    public String getCurrentUserName() {
        return usuarioActual != null ? usuarioActual.getNombreCompleto() : null;
    }

    /**
     * Obtiene el rol del usuario actualmente logueado
     */
    public String getCurrentUserRole() {
        return usuarioActual != null ? usuarioActual.getRol() : null;
    }

    /**
     * Verifica si hay un usuario logueado
     */
    public boolean isLoggedIn() {
        return usuarioActual != null;
    }

    /**
     * Método temporal para inicializar con valores por defecto
     * Esto debe ser reemplazado por el sistema de login real
     */
    public void initializeDefaultSession() {
        Usuario usuarioDefault = new Usuario();
        usuarioDefault.setIdUsuario(1);
        usuarioDefault.setCodigoUsuario("ADMIN");
        usuarioDefault.setNombreCompleto("Usuario Default");
        usuarioDefault.setEmail("admin@sistema.com");
        usuarioDefault.setRol("ADMIN");
        usuarioDefault.setIdTiendaAsignada(1);
        usuarioDefault.setActivo(true);
        
        this.usuarioActual = usuarioDefault;
        System.out.println("DEBUG: Sesión inicializada con valores por defecto");
    }
}