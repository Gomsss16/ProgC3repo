package co.edu.unbosque.proyectoia.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unbosque.proyectoia.dto.UsuarioDTO;
import co.edu.unbosque.proyectoia.entity.Usuario;
import co.edu.unbosque.proyectoia.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Email;

/**
 * Controlador REST para la gestión de usuarios y auditoría de acciones.
 * Proporciona endpoints para operaciones CRUD de usuarios, verificación de códigos,
 * consulta de historial de acciones y auditoría completa del sistema (solo para administradores).
 * 
 * Ruta base: {@code /usuarios}
 * Seguridad: Todos los endpoints requieren autenticación mediante JWT (excepto los marcados con permitAll en SecurityConfig)
 * 
 * @author Equipo ProyectoIA
 * @version 1.0
 */
@RestController
@RequestMapping(path = { "/usuarios" })
@CrossOrigin(origins = { "http://localhost:4200" })
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    /**
     * Servicio de negocio para operaciones relacionadas con usuarios.
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Constructor por defecto del controlador.
     */
    public UsuarioController() {
    }

    /**
     * Lista todos los usuarios registrados en el sistema.
     * 
     * @return lista de usuarios como objetos {@link UsuarioDTO}
     */
    @GetMapping("/ListarUsuarios")
    public ResponseEntity<List<UsuarioDTO>> listarTodas() {
        List<UsuarioDTO> lista = usuarioService.getAll();
        return ResponseEntity.ok(lista);
    }

    /**
     * Obtiene un usuario específico por su ID.
     * 
     * @param id identificador único del usuario
     * @return el usuario solicitado como objeto {@link UsuarioDTO}
     */
    @GetMapping("/obtener/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.getById(id);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Genera y envía un nuevo código de verificación al correo del usuario.
     * 
     * @param correo dirección de correo electrónico del usuario
     * @return mensaje de confirmación o error según el resultado
     */
    @PostMapping("/generarCodigo")
    public ResponseEntity<String> generarNuevoCodigo(
            @RequestParam @Email(message = "Debe ingresar un correo válido") String correo) {
        boolean generado = usuarioService.generarNuevoCodigo(correo);
        if (!generado) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Operación realizada", HttpStatus.OK);
    }

    /**
     * Obtiene un usuario completo por su correo electrónico.
     * 
     * @param correo dirección de correo electrónico del usuario
     * @return el usuario completo como entidad {@link Usuario} o 404 si no existe
     */
    @GetMapping("/obtenercorreo")
    public ResponseEntity<Usuario> obtenerUsuarioPorCorreo(@RequestParam String correo) {
        Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(correo);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Verifica el código de validación enviado al correo del usuario.
     * 
     * @param correo dirección de correo electrónico del usuario
     * @param codigo código de verificación a validar
     * @return mensaje indicando el resultado de la verificación
     */
    @PostMapping("/verificar")
    public ResponseEntity<String> verificar(
            @RequestParam @Email(message = "Debe ingresar un correo válido") String correo,
            @RequestParam String codigo) {
        int resultado = usuarioService.verificarCodigo(correo, codigo);
        switch (resultado) {
            case 0:
                return new ResponseEntity<>("Usuario validado correctamente", HttpStatus.OK);
            case 1:
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            case 2:
                return new ResponseEntity<>("Código incorrecto", HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<>("Error desconocido", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene el historial de acciones del usuario autenticado actualmente.
     * 
     * @param authentication contexto de autenticación del usuario actual
     * @return lista de acciones realizadas por el usuario
     */
    @GetMapping("/mi-historial")
    public ResponseEntity<List<Map<String, Object>>> obtenerMiHistorial(Authentication authentication) {
        String correo = authentication.getName();
        List<Map<String, Object>> historial = usuarioService.obtenerHistorial(correo);
        return ResponseEntity.ok(historial);
    }

    /**
     * Obtiene el historial de acciones de un usuario específico por su correo.
     * Este endpoint está restringido solo para administradores.
     * 
     * @param correo dirección de correo electrónico del usuario a consultar
     * @return lista de acciones realizadas por el usuario especificado
     */
    @GetMapping("/historial/{correo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> obtenerHistorialUsuario(@PathVariable String correo) {
        List<Map<String, Object>> historial = usuarioService.obtenerHistorial(correo);
        return ResponseEntity.ok(historial);
    }

    /**
     * Obtiene la lista completa de usuarios con sus respectivos historiales de acciones.
     * Este endpoint está diseñado para la funcionalidad de auditoría del sistema y está
     * restringido únicamente para usuarios con rol de ADMIN.
     * 
     * El método recopila todos los usuarios registrados en el sistema y para cada uno
     * obtiene su información básica (correo, nombre) junto con su historial completo de acciones.
     * 
     * Casos de uso:
     * - Panel de administración para supervisar la actividad de todos los usuarios
     * - Auditoría de seguridad y cumplimiento normativo
     * - Análisis de patrones de uso del sistema
     * - Generación de reportes de actividad
     * 
     * Manejo de errores:
     * - Si un usuario individual tiene datos corruptos o problemas de encriptación, se omite y continúa con los demás
     * - Los errores de procesamiento se registran en la consola del servidor
     * - Si ocurre un error general, retorna código 500 (Internal Server Error)
     * 
     * Formato de respuesta:
     * Cada elemento del array contiene:
     * - correo: String - dirección de correo del usuario (desencriptada)
     * - nombre: String - nombre completo del usuario (desencriptado)
     * - historial: List - array de objetos con las acciones realizadas, cada acción incluye:
     *   - tipo: String - tipo de acción (LOGIN, REGISTRO, ANALIZAR_TEXTO, etc.)
     *   - detalles: String - descripción detallada de la acción
     *   - fecha: String - timestamp de cuando ocurrió la acción
     *   - ip: String - dirección IP desde donde se realizó la acción
     * 
     * Ejemplo de respuesta:
     * [
     *   {
     *     "correo": "admin@example.com",
     *     "nombre": "Administrador",
     *     "historial": [
     *       {
     *         "tipo": "LOGIN",
     *         "detalles": "Usuario inició sesión",
     *         "fecha": "2025-11-09T19:30:00",
     *         "ip": "192.168.1.100"
     *       }
     *     ]
     *   }
     * ]
     * 
     * Seguridad:
     * - Requiere autenticación JWT válida
     * - Requiere rol ADMIN explícitamente mediante @PreAuthorize
     * - Los usuarios regulares (USER) recibirán un error 403 Forbidden si intentan acceder
     * 
     * Rendimiento:
     * - Para sistemas con muchos usuarios, considerar implementar paginación
     * - El procesamiento es secuencial, con manejo de errores individual por usuario
     * - Los errores en usuarios individuales no detienen el procesamiento completo
     * 
     * @return ResponseEntity con:
     *         - Código 200 (OK) y lista de usuarios con sus historiales si la operación es exitosa
     *         - Código 500 (Internal Server Error) si ocurre un error general durante el procesamiento
     */
    @GetMapping("/todos-con-historial")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodosConHistorial() {
        try {
            List<UsuarioDTO> todosDTO = usuarioService.getAll();
            List<Map<String, Object>> resultado = new ArrayList<>();
            
            for (UsuarioDTO dto : todosDTO) {
                try {
                    String correoDesencriptado = dto.getCorreo();
                    Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(correoDesencriptado);
                    
                    if (usuario != null) {
                        Map<String, Object> usuarioData = new HashMap<>();
                        usuarioData.put("correo", correoDesencriptado);
                        usuarioData.put("nombre", dto.getNombre());
                        usuarioData.put("historial", usuarioService.obtenerHistorial(correoDesencriptado));
                        
                        resultado.add(usuarioData);
                    }
                } catch (Exception e) {
                    System.err.println("Error procesando usuario: " + e.getMessage());
                    continue;
                }
            }
            
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
