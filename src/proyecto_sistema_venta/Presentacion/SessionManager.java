package proyecto_sistema_venta.Presentacion;

/**
 * Clase para gestionar la sesión del usuario actual.
 * Esta es una implementación básica que debe ser reemplazada por un sistema de autenticación completo.
 *
 * @author Sistema de Ventas
 */
public class SessionManager {

    private static SessionManager instance;
    private Integer currentUserId;
    private Integer currentStoreId;
    private String currentUserName;

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
     * Simula el login de un usuario.
     * En una implementación real, esto vendría de un formulario de login.
     *
     * @param userId ID del usuario
     * @param storeId ID de la tienda asignada al usuario
     * @param userName Nombre del usuario
     */
    public void login(int userId, int storeId, String userName) {
        this.currentUserId = userId;
        this.currentStoreId = storeId;
        this.currentUserName = userName;
        System.out.println("DEBUG: Usuario logueado - ID: " + userId + ", Tienda: " + storeId + ", Nombre: " + userName);
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public void logout() {
        this.currentUserId = null;
        this.currentStoreId = null;
        this.currentUserName = null;
        System.out.println("DEBUG: Sesión cerrada");
    }

    /**
     * Obtiene el ID del usuario actualmente logueado.
     *
     * @return ID del usuario o null si no hay sesión
     */
    public Integer getCurrentUserId() {
        return currentUserId;
    }

    /**
     * Obtiene el ID de la tienda del usuario actualmente logueado.
     *
     * @return ID de la tienda o null si no hay sesión
     */
    public Integer getCurrentStoreId() {
        return currentStoreId;
    }

    /**
     * Obtiene el nombre del usuario actualmente logueado.
     *
     * @return Nombre del usuario o null si no hay sesión
     */
    public String getCurrentUserName() {
        return currentUserName;
    }

    /**
     * Verifica si hay un usuario logueado.
     *
     * @return true si hay sesión activa, false en caso contrario
     */
    public boolean isLoggedIn() {
        return currentUserId != null && currentStoreId != null;
    }

    /**
     * Método temporal para inicializar con valores por defecto.
     * Esto debe ser reemplazado por el sistema de login real.
     */
    public void initializeDefaultSession() {
        // Valores por defecto para desarrollo - deben ser reemplazados por login real
        this.currentUserId = 1; // Usuario por defecto
        this.currentStoreId = 1; // Tienda por defecto
        this.currentUserName = "Usuario Default";
        System.out.println("DEBUG: Sesión inicializada con valores por defecto");
    }
}