package co.edu.unbosque.proyectoia.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectoia.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

/**
 * Servicio utilitario para la generación, validación y manejo de tokens JWT.
 * Proporciona métodos para crear tokens, extraer información de ellos y validar su autenticidad.
 */
@Service
public class JwtUtil {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.expirationtime}")
    private long expirationTime;

    /**
     * Genera un token JWT con los claims adicionales y el sujeto especificado.
     */
    public String obtenerToken(Map<String, Object> extraClaims, String subject) {
        long now = System.currentTimeMillis();
        return Jwts
            .builder()
            .setClaims(extraClaims)  // ✅ AGREGAR ESTA LÍNEA
            .setSubject(subject)
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + expirationTime))
            .signWith(obtenerKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Key obtenerKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Genera un token JWT para los detalles de usuario proporcionados.
     * Incluye las authorities del usuario con el prefijo ROLE_ requerido por Spring Security.
     * 
     * @param userDetails detalles del usuario autenticado
     * @return token JWT firmado con información del usuario y sus roles
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        
        // ✅ CONVERTIR AUTHORITIES AGREGANDO ROLE_ SI NO LO TIENE
        List<Map<String, String>> authorities = userDetails.getAuthorities().stream()
            .map(auth -> {
                String authority = auth.getAuthority();
                // Agregar ROLE_ si no está presente
                if (!authority.startsWith("ROLE_")) {
                    authority = "ROLE_" + authority;
                }
                Map<String, String> authorityMap = new HashMap<>();
                authorityMap.put("authority", authority);
                return authorityMap;
            })
            .collect(Collectors.toList());
        
        claims.put("authorities", authorities);
        
        // Agregar rol explícitamente si el UserDetails es un Usuario
        if (userDetails instanceof Usuario) {
            Usuario usuario = (Usuario) userDetails;
            String role = usuario.getRole().name();
            if (!role.startsWith("ROLE_")) {
                role = "ROLE_" + role;
            }
            claims.put("role", role);
        }
        
        String usuario = userDetails.getUsername();
        return obtenerToken(claims, usuario);
    }
    /**
     * Extrae todos los claims del token JWT.
     * Método público para permitir el acceso desde otros componentes.
     * 
     * @param token Token JWT a procesar
     * @return Claims contenidos en el token
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(obtenerKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public JwtUtil() {
    }
}
