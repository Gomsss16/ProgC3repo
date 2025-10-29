package co.edu.unbosque.proyectoia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import co.edu.unbosque.proyectoia.dto.AuthResponse;
import co.edu.unbosque.proyectoia.dto.UsuarioDTO;
import co.edu.unbosque.proyectoia.security.JwtUtil;

import co.edu.unbosque.proyectoia.service.UsuarioService;
import co.edu.unbosque.proyectoia.util.AESUtil;



@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
	@Autowired
	 private  AuthenticationManager authenticationManager;
	@Autowired
	    private JwtUtil jwtUtil;
	@Autowired
	private UsuarioService usuarioService;
	
	

//	@PostMapping("/register")
//    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest request) {
//        final TokenResponse token = authService.register(request);
//        return ResponseEntity.ok(token);
//        
//        
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<TokenResponse> authenticate(@RequestBody LoginRequest request) {
//        final TokenResponse token = authService.login(request);
//        return ResponseEntity.ok(token);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO loginRequest) {
    	System.out.println("entraaa");
        try {
        	System.out.println("➡️ Intentando login con correo: " + loginRequest.getCorreo());
        	System.out.println("➡️ Contraseña ingresada: " + loginRequest.getContrasenia());
        	
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken( loginRequest.getCorreo(), loginRequest.getContrasenia()));
            
            System.out.println("🎯 Autenticación completada: " + authentication.isAuthenticated());

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            System.out.println("credenciales: " + userDetails);
            System.out.println("login: " + loginRequest.getCorreo() + "contrasenia" + loginRequest.getContrasenia() );
            String jwt = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResponse(jwt));
        } catch (AuthenticationException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("el correo y/o contraseña no coinciden");
        	
        	System.out.println("❌ Tipo de excepción: " + e.getClass().getSimpleName());
            System.out.println("📄 Mensaje: " + e.getMessage());
            e.printStackTrace(); // Opcional: imprime toda la traza en consola
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioDTO registerRequest) {
    	System.out.println("📩 JSON recibido: " + registerRequest);
    	
    	 int result = usuarioService.create(registerRequest);

         switch (result) {
             case 0:
                 return ResponseEntity.status(HttpStatus.CREATED)
                         .body("✅ Usuario registrado correctamente. Se envió el código de verificación al correo.");
             case 1:
                 return ResponseEntity.status(HttpStatus.CONFLICT)
                         .body("⚠️ El usuario ya existe con ese correo electrónico.");
             case 2:
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                         .body("❌ Datos incompletos o error interno al registrar el usuario.");
             default:
                 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                         .body("❗ Error desconocido al registrar el usuario.");
         }

      
    }
	
}
