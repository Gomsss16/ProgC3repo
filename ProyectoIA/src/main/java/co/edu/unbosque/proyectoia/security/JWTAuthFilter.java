package co.edu.unbosque.proyectoia.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	
	//Existen 4 tipos de JWT

	//REALIZA TODOS LOS FILTROS NECESARIOS DEL TOKEN
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	
			throws ServletException, IOException {
		
		System.out.println("🔍 Ejecutando filtro JWT...");
		final String token = getTokenFromRequest(request);
		System.out.println("Token recibido: " + token);
		System.out.println("Usuario autenticado: " + SecurityContextHolder.getContext().getAuthentication());
		if(token == null) {
			System.out.println("EL TOKEN ES NULO");
			filterChain.doFilter(request, response);
			return;
		}
		filterChain.doFilter(request, response);
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			System.out.println("ENTRA AL IF");
			return authHeader.substring(7);
		}
		return null;
	}

	
}
