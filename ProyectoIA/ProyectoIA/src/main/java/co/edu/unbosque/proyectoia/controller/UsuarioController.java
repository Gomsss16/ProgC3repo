package co.edu.unbosque.proyectoia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.proyectoia.dto.UsuarioDTO;

import co.edu.unbosque.proyectoia.service.UsuarioService;
import jakarta.validation.constraints.Email;


@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/api/usuarios" })
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	public UsuarioController() {
		// TODO Auto-generated constructor stub
	}
	
	 
	  
	  @PostMapping(path = "/createusuario")
	    public ResponseEntity<String> createNew(@RequestParam String nombre, @RequestParam @Email(message = "Debe ingresar un correo válido")String correo, 
	            @RequestParam String contraseña) {
		  UsuarioDTO newUser = new UsuarioDTO(nombre, correo, contraseña, null, false);
	        int status = usuarioService.create(newUser);

	        switch (status) {
	            case 0:
	                return new ResponseEntity<>("Administrador creado exitosamente", HttpStatus.CREATED);
	            case 1:
	                return new ResponseEntity<>("El usuario ya existe", HttpStatus.CONFLICT);
	            case 2:
	                return new ResponseEntity<>("Datos inválidos", HttpStatus.BAD_REQUEST);
	            default:
	                return new ResponseEntity<>("Error desconocido", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	
	  @PostMapping(path = "/loginusuario")
	    public ResponseEntity<String> loginUsuario(@RequestParam String correo, @RequestParam String conrasenia) {
	        
	        UsuarioDTO usuario = new UsuarioDTO();
	        System.out.println("=== ENDPOINT LOGIN ADMIN ===");
	        System.out.println("Usuario: " + usuario.getCorreo());
	        
	        try {
	            if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
	                return new ResponseEntity<>("{\"error\": \"Usuario es requerido\"}", HttpStatus.BAD_REQUEST);
	            }
	            
	            if (usuario.getContrasenia() == null || usuario.getContrasenia().trim().isEmpty()) {
	                return new ResponseEntity<>("{\"error\": \"Contraseña es requerida\"}", HttpStatus.BAD_REQUEST);
	            }
	            
	            int authResult = usuarioService.authenticateUsuario(usuario.getCorreo(), usuario.getContrasenia());
	            System.out.println("Resultado autenticación: " + authResult);
	            
	            switch (authResult) {
	                case 0:
	                    System.out.println("Login exitoso");
	                    
	                    String response = String.format(
	                        "{\"message\": \"Login exitoso\", \"usuario\": \"%s\"}", 
	                        usuario.getCorreo()
	                    );
	                    return new ResponseEntity<>(response, HttpStatus.OK);
	                    
	                case 1:
	                    System.out.println("Usuario no encontrado");
	                    return new ResponseEntity<>("{\"error\": \"Usuario no encontrado\"}", HttpStatus.NOT_FOUND);
	                    
	                case 2:
	                    System.out.println("Contraseña incorrecta");
	                    return new ResponseEntity<>("{\"error\": \"Contraseña incorrecta\"}", HttpStatus.UNAUTHORIZED);
	                    
	                case 3:
	                    System.out.println("Error del sistema");
	                    return new ResponseEntity<>("{\"error\": \"Error del sistema\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	                    
	                default:
	                    return new ResponseEntity<>("{\"error\": \"Error desconocido\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	            }
	            
	        } catch (Exception e) {
	            System.err.println("Excepción en login: " + e.getMessage());
	            return new ResponseEntity<>("{\"error\": \"Error interno del servidor\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	@PostMapping("/generarCodigo")
	public ResponseEntity<String> generarNuevoCodigo(
	        @RequestParam @Email(message = "Debe ingresar un correo válido") String correo)
	        {

	    boolean generado = usuarioService.generarNuevoCodigo(correo);

	    if (!generado) {
	        return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
	    }

	    return new ResponseEntity<>("Operación realizada", HttpStatus.OK);
	}
	
//	@PostMapping("/generarCodigo")
//	public ResponseEntity<String> generarNuevoCodigo(HttpSession session) {
//	    Long usuarioId = (Long) session.getAttribute("usuarioId"); // usuario logueado
//	    if (usuarioId == null) {
//	        return new ResponseEntity<>("Usuario no autenticado", HttpStatus.UNAUTHORIZED);
//	    }
//
//	    boolean generado = usuarioService.generarNuevoCodigoParaUsuario(usuarioId);
//	    if (!generado) {
//	        return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
//	    }
//
//	    return new ResponseEntity<>("Operación realizada", HttpStatus.OK);
//	}
//	  
//	 
	
	@PostMapping("/verificar")
	public ResponseEntity<String> verificar(
	        @RequestParam @Email(message = "Debe ingresar un correo válido")String correo,
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
	
	
}
