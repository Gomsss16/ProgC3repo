package co.edu.unbosque.proyectoia.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import co.edu.unbosque.proyectoia.repository.UsuarioRepository;

@Configuration
public class ApplicationConfig {
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	
	
//	 // 🔹 1. UserDetailsService — le dice a Spring cómo encontrar el usuario
//	@Bean
//	public UserDetailsService userDetailsService() {
//	    return username -> usuarioRepo.findByCorreo(username)
//	            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
//	}
//	
//	// 🔹 2. AuthenticationProvider — define el proveedor de autenticación
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//    	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());   // Usa el método siguiente
//        return authProvider;
//    }
//
//    // 🔹 3. PasswordEncoder — encripta/valida contraseñas
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    // 🔹 4. AuthenticationManager — gestiona la autenticación de usuarios
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }

}
