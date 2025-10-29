package co.edu.unbosque.proyectoia.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import co.edu.unbosque.proyectoia.security.JWTAuthFilter;


import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationFilter  jwtAuthFilter;
	
//	@Autowired
//	private AuthenticationProvider authProvider;
//	
	@Autowired
	private  UserDetailsService userDetailsService;

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
//		return httpSecurity
//				.authorizeHttpRequests(authRequest -> authRequest.requestMatchers("/auth/**").permitAll().anyRequest().authenticated());
//		
//		
//		
//	}
	
	public SecurityConfig(
		      JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {
		    this.jwtAuthFilter = jwtAuthFilter;
		    this.userDetailsService = userDetailsService;
		  }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	        .csrf(csrf -> csrf.disable()) // 1️⃣ Desactiva CSRF (porque usas tokens)
	        .authorizeHttpRequests(auth -> auth
	        		   .requestMatchers("/auth/**").permitAll()
	                   .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
	                   .requestMatchers("/usuarios").hasAnyRole("USER", "ADMIN")
	            .anyRequest().authenticated()            // 3️⃣ Todo lo demás requiere token
	        )
	        .sessionManagement(session -> 
	            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //la seguridad depende de cada token (JWT) enviado en los headers.
	        )
	        .authenticationProvider(authenticationProvider()) // 👈 Aquí se registra
	        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // 6️⃣ Añade filtro JWT
	        .build(); // 7️⃣ Construye la configuración final
	    
	    
	} 
	
	// 🔹 2. AuthenticationProvider — define el proveedor de autenticación
	
	@Bean
    public AuthenticationProvider authenticationProvider() {
		
    	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
    	System.out.println("contrasenia encriptada " + passwordEncoder());
        authProvider.setPasswordEncoder(passwordEncoder());   // Usa el método siguiente
        return authProvider;
    }
	
	 @Bean
	    public PasswordEncoder passwordEncoder() {
		 System.out.println("entra ene en el PassWordEncoder " );
	        return new BCryptPasswordEncoder();
	    }

	// 🔹 4. AuthenticationManager — gestiona la autenticación de usuarios
	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    	 System.out.println("entra ene en el AuthenticationManager " );
	        return config.getAuthenticationManager();
	    }
	
}
