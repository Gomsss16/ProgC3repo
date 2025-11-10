package co.edu.unbosque.proyectoia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import co.edu.unbosque.proyectoia.dto.AuthResponse;
import co.edu.unbosque.proyectoia.dto.UsuarioDTO;
import co.edu.unbosque.proyectoia.entity.TipoAccion;
import co.edu.unbosque.proyectoia.security.JwtUtil;
import co.edu.unbosque.proyectoia.service.UsuarioService;

/**
 * Controlador REST para la autenticación y registro de usuarios.
 * Este controlador maneja los endpoints relacionados con la seguridad de la aplicación,
 * incluyendo el inicio de sesión (login) mediante autenticación JWT y el registro de nuevos usuarios.
 * Integra Spring Security para validar credenciales y genera tokens JWT para sesiones autenticadas.
 * 
 * Ruta base: {@code /auth}
 * 
 * CORS: Este controlador permite solicitudes Cross-Origin únicamente desde {@code http://localhost:4200},
 * típicamente utilizado para aplicaciones Angular en desarrollo. En producción, esta configuración debe
 * ajustarse según las necesidades de seguridad y los orígenes permitidos.
 * 
 * Flujo de Autenticación:
 * - El usuario envía credenciales (correo y contraseña) al endpoint de login
 * - Spring Security valida las credenciales mediante {@link AuthenticationManager}
 * - Si la autenticación es exitosa, se genera un token JWT
 * - El token se retorna al cliente para ser incluido en solicitudes posteriores
 * - Se registra la acción de login en el historial del usuario
 * 
 * @author Equipo ProyectoIA
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:4200" })
public class AuthController {

    /**
     * Administrador de autenticación de Spring Security que procesa las credenciales del usuario
     * y valida su identidad contra el almacenamiento de datos configurado.
     * Este componente orquesta el proceso completo de autenticación, incluyendo la carga de detalles
     * del usuario y la verificación de contraseñas.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Utilidad para la generación y validación de tokens JWT (JSON Web Tokens).
     * Proporciona métodos para crear tokens con información del usuario autenticado,
     * extraer claims, validar firmas y verificar la expiración de tokens.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Servicio de negocio para operaciones relacionadas con usuarios.
     * Incluye funciones para crear, actualizar, obtener usuarios y gestionar el historial de acciones.
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Autentica un usuario y genera un token JWT si las credenciales son válidas.
     * 
     * Este endpoint implementa el proceso completo de inicio de sesión utilizando Spring Security.
     * Recibe las credenciales del usuario (correo electrónico y contraseña), las valida contra
     * la base de datos, y si son correctas, genera un token JWT que el cliente puede utilizar
     * para autenticar solicitudes futuras. Además, registra la acción de login en el historial del usuario.
     * 
     * Método HTTP: POST
     * Ruta: {@code /auth/login}
     * Content-Type: application/json
     * 
     * Proceso de Autenticación:
     * 1. Recepción de credenciales (correo y contraseña) desde el cliente
     * 2. Creación de un token de autenticación usando {@link UsernamePasswordAuthenticationToken}
     * 3. Validación de credenciales mediante {@link AuthenticationManager#authenticate(Authentication)}
     * 4. Si es exitoso: extracción de detalles del usuario autenticado
     * 5. Generación de token JWT usando {@link JwtUtil#generateToken(UserDetails)}
     * 6. Registro de la acción de login en el historial del usuario
     * 7. Retorno del token al cliente en un objeto {@link AuthResponse}
     * 
     * Manejo de Errores:
     * Si las credenciales son inválidas, el usuario no existe, o la cuenta está deshabilitada,
     * se captura una {@link AuthenticationException} y se retorna un error 401 Unauthorized
     * con información detallada del problema.
     * 
     * @param loginRequest objeto {@link UsuarioDTO} que contiene las credenciales del usuario:
     *                     - {@code correo}: dirección de correo electrónico del usuario
     *                     - {@code contrasenia}: contraseña en texto plano (será validada contra la versión encriptada)
     * @return un {@link ResponseEntity} con:
     *         - Código 200 (OK) y un objeto {@link AuthResponse} conteniendo el token JWT si la autenticación es exitosa
     *         - Código 401 (Unauthorized) con un mensaje de error descriptivo si las credenciales son inválidas
     * @throws AuthenticationException si ocurre un error durante el proceso de autenticación
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getContrasenia())
            );

            if (authentication == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Error: Authentication failed - Invalid credentials");
            }
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(userDetails);

            // Registrar login en historial
            try {
                usuarioService.agregarAccion(
                    loginRequest.getCorreo(),
                    TipoAccion.LOGIN,
                    "Usuario inició sesión",
                    obtenerIP()
                );
            } catch (Exception e) {
                System.err.println("Error al registrar login en historial: " + e.getMessage());
            }

            return ResponseEntity.ok(new AuthResponse(jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * Este endpoint maneja el proceso completo de registro de nuevos usuarios, validando
     * los datos proporcionados, verificando que el correo electrónico no esté ya registrado,
     * y creando la cuenta en la base de datos con la contraseña encriptada.
     * Además, registra la acción de registro en el historial del usuario.
     * 
     * Método HTTP: POST
     * Ruta: {@code /auth/register}
     * Content-Type: application/json
     * 
     * Proceso de Registro:
     * 1. Recepción de datos del nuevo usuario desde el cliente
     * 2. Validación de datos completos y formato correcto
     * 3. Verificación de que el correo no esté ya registrado
     * 4. Encriptación de la contraseña usando el encoder configurado
     * 5. Creación del usuario en la base de datos
     * 6. Envío de código de verificación al correo (si está implementado)
     * 7. Registro de la acción de registro en el historial
     * 
     * Códigos de Resultado del Servicio:
     * - 0: Registro exitoso, usuario creado correctamente
     * - 1: Conflicto, el correo electrónico ya está registrado
     * - 2: Datos incompletos o error de validación
     * - Otro: Error interno desconocido
     * 
     * @param registerRequest objeto {@link UsuarioDTO} que contiene los datos del nuevo usuario:
     *                        - {@code correo}: dirección de correo electrónico (debe ser única)
     *                        - {@code contrasenia}: contraseña en texto plano (será encriptada antes de almacenarla)
     *                        - Otros campos requeridos según la implementación del DTO
     * @return un {@link ResponseEntity} con:
     *         - Código 201 (Created) con mensaje de éxito si el registro es exitoso
     *         - Código 409 (Conflict) con mensaje de advertencia si el correo ya existe
     *         - Código 400 (Bad Request) con mensaje de error si los datos son incompletos
     *         - Código 500 (Internal Server Error) con mensaje de error si ocurre un error interno
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioDTO registerRequest) {
        int result = usuarioService.create(registerRequest);

        switch (result) {
            case 0:
                // Registrar acción de registro en historial
                try {
                    usuarioService.agregarAccion(
                        registerRequest.getCorreo(),
                        TipoAccion.REGISTRO,
                        "Usuario se registró en el sistema",
                        obtenerIP()
                    );
                } catch (Exception e) {
                    System.err.println("Error al registrar acción de registro: " + e.getMessage());
                }
                
                return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuario registrado correctamente. Se envió el código de verificación al correo.");
            case 1:
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El usuario ya existe con ese correo electrónico.");
            case 2:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Datos incompletos o error interno al registrar el usuario.");
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❗Error desconocido al registrar el usuario.");
        }
    }

    /**
     * Obtiene la dirección IP del servidor.
     * Este método auxiliar se utiliza para registrar la IP en el historial de acciones del usuario.
     * Si no es posible obtener la IP real, retorna la dirección localhost por defecto.
     * 
     * @return la dirección IP del servidor, o "127.0.0.1" si ocurre un error
     */
    private String obtenerIP() {
        try {
            return java.net.InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "127.0.0.1";
        }
    }

    /**
     * Constructor por defecto del controlador.
     */
    public AuthController() {
    }
}
