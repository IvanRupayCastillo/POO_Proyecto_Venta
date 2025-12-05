package proyecto_sistema_venta.Negocio;

import proyecto_sistema_venta.Datos.UsuarioDAO;
import proyecto_sistema_venta.Entidades.Usuario;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AutenticacionNegocio {
    
    private final UsuarioDAO usuarioDAO;
    
    public AutenticacionNegocio() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    /**
     * Autentica un usuario con email y contraseña
     */
    public Usuario login(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es requerido");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }
        
        // Buscar usuario por email
        Usuario usuario = usuarioDAO.buscarPorEmail(email.trim());
        
        if (usuario == null) {
            return null; // Usuario no encontrado
        }
        
        // Verificar contraseña
        if (!verificarPassword(password, usuario.getPasswordHash())) {
            return null; // Contraseña incorrecta
        }
        
        // Actualizar último acceso
        usuarioDAO.actualizarUltimoAcceso(usuario.getIdUsuario());
        
        return usuario;
    }
    
    /**
     * Registra un nuevo usuario
     */
    public String registrarUsuario(String codigoUsuario, String nombreCompleto, String email, 
                                   String password, String confirmarPassword, String rol) {
        
        // Validaciones
        if (codigoUsuario == null || codigoUsuario.trim().isEmpty()) {
            return "El código de usuario es requerido";
        }
        
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            return "El nombre completo es requerido";
        }
        
        if (email == null || email.trim().isEmpty()) {
            return "El email es requerido";
        }
        
        if (!esEmailValido(email)) {
            return "El formato del email no es válido";
        }
        
        if (password == null || password.trim().isEmpty()) {
            return "La contraseña es requerida";
        }
        
        if (password.length() < 6) {
            return "La contraseña debe tener al menos 6 caracteres";
        }
        
        if (!password.equals(confirmarPassword)) {
            return "Las contraseñas no coinciden";
        }
        
        // Verificar si el email ya existe
        if (usuarioDAO.existeEmail(email.trim())) {
            return "El email ya está registrado";
        }
        
        // Verificar si el código de usuario ya existe
        if (usuarioDAO.existeCodigoUsuario(codigoUsuario.trim())) {
            return "El código de usuario ya está registrado";
        }
        
        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setCodigoUsuario(codigoUsuario.trim());
        usuario.setNombreCompleto(nombreCompleto.trim());
        usuario.setEmail(email.trim().toLowerCase());
        usuario.setPasswordHash(hashPassword(password));
        usuario.setRol(rol != null && !rol.isEmpty() ? rol : "VENDEDOR");
        usuario.setActivo(true);
        
        // Insertar en base de datos
        int idGenerado = usuarioDAO.insertar(usuario);
        
        if (idGenerado > 0) {
            return "OK";
        } else {
            return "Error al registrar el usuario";
        }
    }
    
    /**
     * Cambia la contraseña de un usuario
     */
    public String cambiarPassword(String email, String passwordActual, String nuevaPassword, String confirmarPassword) {
        
        if (email == null || email.trim().isEmpty()) {
            return "El email es requerido";
        }
        
        if (passwordActual == null || passwordActual.trim().isEmpty()) {
            return "La contraseña actual es requerida";
        }
        
        if (nuevaPassword == null || nuevaPassword.trim().isEmpty()) {
            return "La nueva contraseña es requerida";
        }
        
        if (nuevaPassword.length() < 6) {
            return "La nueva contraseña debe tener al menos 6 caracteres";
        }
        
        if (!nuevaPassword.equals(confirmarPassword)) {
            return "Las contraseñas no coinciden";
        }
        
        // Buscar usuario
        Usuario usuario = usuarioDAO.buscarPorEmail(email.trim());
        
        if (usuario == null) {
            return "Usuario no encontrado";
        }
        
        // Verificar contraseña actual
        if (!verificarPassword(passwordActual, usuario.getPasswordHash())) {
            return "La contraseña actual es incorrecta";
        }
        
        // Actualizar contraseña
        String nuevoHash = hashPassword(nuevaPassword);
        boolean actualizado = usuarioDAO.actualizarPassword(usuario.getIdUsuario(), nuevoHash);
        
        if (actualizado) {
            return "OK";
        } else {
            return "Error al actualizar la contraseña";
        }
    }
    
    /**
     * Recupera la contraseña (genera una temporal)
     */
    public String recuperarPassword(String email) {
        
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        
        // Buscar usuario
        Usuario usuario = usuarioDAO.buscarPorEmail(email.trim());
        
        if (usuario == null) {
            return null; // No revelar si el email existe o no por seguridad
        }
        
        // Generar contraseña temporal
        String passwordTemporal = generarPasswordTemporal();
        
        // Actualizar contraseña
        String nuevoHash = hashPassword(passwordTemporal);
        boolean actualizado = usuarioDAO.actualizarPassword(usuario.getIdUsuario(), nuevoHash);
        
        if (actualizado) {
            // En un sistema real, aquí se enviaría un email
            // Por ahora, retornamos la contraseña temporal
            return passwordTemporal;
        }
        
        return null;
    }
    
    /**
     * Genera un hash SHA-256 de la contraseña
     */
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar hash de contraseña", e);
        }
    }
    
    /**
     * Verifica si una contraseña coincide con su hash
     */
    private boolean verificarPassword(String password, String hash) {
        String passwordHash = hashPassword(password);
        return passwordHash.equals(hash);
    }
    
    /**
     * Valida el formato de un email
     */
    private boolean esEmailValido(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }
    
    /**
     * Genera una contraseña temporal aleatoria
     */
    private String generarPasswordTemporal() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[8];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, 8);
    }
}
