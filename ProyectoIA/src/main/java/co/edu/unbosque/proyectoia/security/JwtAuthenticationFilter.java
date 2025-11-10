package co.edu.unbosque.proyectoia.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import co.edu.unbosque.proyectoia.util.AESUtil;
import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Filtro de autenticación JWT que intercepta las solicitudes HTTP para validar el token JWT.
 * 
 * Este filtro se ejecuta una vez por cada solicitud y es responsable de:
 * <ul>
 *   <li>Verificar la presencia y validez del token JWT en el encabezado de autorización.</li>
 *   <li>Extraer el nombre de usuario y las authorities del token.</li>
 *   <li>Cargar los detalles del usuario desde la base de datos.</li>
 *   <li>Establecer la autenticación en el contexto de seguridad de Spring si el token es válido.</li>
 * </ul>
 * 
 * <p>
 * El filtro excluye automáticamente ciertas rutas públicas que no requieren autenticación,
 * como los endpoints de Swagger, la consola H2, y las rutas de autenticación.
 * </p>
 * 
 * <p>
 * Además, extrae las authorities directamente del token JWT para evitar discrepancias
 * entre los roles almacenados en la base de datos y los del token, garantizando que
 * los permisos sean consistentes con la información del token firmado.
 * </p>
 * 
 * @author Equipo ProyectoIA
 * @version 2.0
 * @see JwtUtil
 * @see UserDetailsService
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /** Utilidad para manejar operaciones relacionadas con JWT. */
    private final JwtUtil jwtUtil;

    /** Servicio para cargar los detalles del usuario desde la base de datos. */
    private final UserDetailsService userDetailsService;

    /**
     * Constructor del filtro de autenticación JWT.
     * Inyecta las dependencias necesarias para validar y procesar tokens JWT.
     *
     * @param jwtUtil Utilidad para manejar operaciones JWT (generación, validación, extracción de claims).
     * @param userDetailsService Servicio para cargar los detalles completos del usuario.
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Método que filtra cada solicitud HTTP para validar el token JWT.
     * Excluye ciertas rutas públicas y procesa la autenticación para las rutas protegidas.
     * 
     * <p>
     * Flujo del procesamiento:
     * <ol>
     *   <li>Verifica si la ruta es pública (Swagger, H2, Auth, etc.) y la omite si es así.</li>
     *   <li>Extrae el token JWT del encabezado "Authorization".</li>
     *   <li>Desencripta el correo del usuario desde el token.</li>
     *   <li>Extrae las authorities directamente del token JWT.</li>
     *   <li>Carga los detalles del usuario desde la base de datos.</li>
     *   <li>Valida el token contra los detalles del usuario.</li>
     *   <li>Establece la autenticación en el SecurityContext con las authorities del token.</li>
     *   <li>Continúa con la cadena de filtros.</li>
     * </ol>
     * </p>
     *
     * @param request Solicitud HTTP entrante.
     * @param response Respuesta HTTP saliente.
     * @param filterChain Cadena de filtros para continuar el procesamiento de la solicitud.
     * @throws ServletException Si ocurre un error en el procesamiento del filtro.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Rutas públicas que no requieren autenticación
        if (path.startsWith("/swagger-ui") ||
            path.startsWith("/v3/api-docs") ||
            path.startsWith("/api-docs") ||
            path.startsWith("/swagger-resources") ||
            path.startsWith("/webjars") ||
            path.startsWith("/api/analizador-ia") ||
            path.startsWith("/auth") ||
            path.startsWith("/usuarios/verificar") ||
            path.startsWith("/usuarios/obtenercorreo") ||
            path.startsWith("/usuarios/obtener") ||
            path.startsWith("/h2-console")) {

            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // Extraer el token JWT del encabezado Authorization
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                String correoEncriptado = jwtUtil.extractUsername(jwt);
                username = AESUtil.decrypt(correoEncriptado);
            } catch (Exception e) {
                logger.error("Error al extraer el nombre de usuario del token: " + e.getMessage(), e);
            }
        }

        // Si el usuario es válido y no hay autenticación establecida
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            
            // Validar el token
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // ✅ EXTRAER AUTHORITIES DEL TOKEN JWT
                List<GrantedAuthority> authorities = extractAuthoritiesFromToken(jwt);
                
                // Crear el token de autenticación con las authorities del JWT
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                
                logger.debug("Usuario autenticado: " + username + " con authorities: " + authorities);
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extrae las authorities (roles y permisos) directamente del token JWT.
     * 
     * Este método parsea los claims del token y extrae la lista de authorities,
     * convirtiéndolas en objetos {@link SimpleGrantedAuthority} que Spring Security
     * puede utilizar para la autorización.
     * 
     * <p>
     * Formato esperado en el token JWT:
     * <pre>
     * "authorities": [
     *   {"authority": "ROLE_ADMIN"},
     *   {"authority": "ROLE_USER"}
     * ]
     * </pre>
     * </p>
     * 
     * @param token Token JWT del cual extraer las authorities.
     * @return Lista de {@link GrantedAuthority} extraídas del token, o lista vacía si no hay authorities.
     */
    private List<GrantedAuthority> extractAuthoritiesFromToken(String token) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        try {
            Claims claims = jwtUtil.extractAllClaims(token);
            List<Map<String, String>> authoritiesClaim = claims.get("authorities", List.class);
            
            if (authoritiesClaim != null) {
                for (Map<String, String> authority : authoritiesClaim) {
                    String authorityName = authority.get("authority");
                    if (authorityName != null) {
                        authorities.add(new SimpleGrantedAuthority(authorityName));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al extraer authorities del token: " + e.getMessage(), e);
        }
        return authorities;
    }
}
