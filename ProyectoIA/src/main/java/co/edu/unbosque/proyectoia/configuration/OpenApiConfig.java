package co.edu.unbosque.proyectoia.configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para la documentación OpenAPI/Swagger de la aplicación.
 * <p>
 * Esta clase utiliza SpringDoc OpenAPI para generar documentación interactiva de la API REST,
 * incluyendo la especificación completa de endpoints, esquemas de seguridad basados en JWT,
 * y respuestas de error comunes. La documentación generada está disponible a través de
 * Swagger UI, permitiendo a los desarrolladores probar los endpoints directamente desde el navegador.
 * </p>
 * <p>
 * La configuración incluye:
 * </p>
 * <ul>
 *   <li>Información general de la API (título, versión, descripción)</li>
 *   <li>Esquema de seguridad Bearer JWT para autenticación</li>
 *   <li>Respuestas de error globales reutilizables (401 Unauthorized, 403 Forbidden)</li>
 *   <li>Requisitos de seguridad aplicados globalmente a todos los endpoints</li>
 * </ul>
 * <p>
 * La documentación generada puede ser accedida en las siguientes URLs:
 * </p>
 * <ul>
 *   <li>Swagger UI: {@code http://servidor:puerto/context-path/swagger-ui.html}</li>
 *   <li>Especificación JSON: {@code http://servidor:puerto/context-path/v3/api-docs}</li>
 *   <li>Especificación YAML: {@code http://servidor:puerto/context-path/v3/api-docs.yaml}</li>
 * </ul>
 * 
 * @author [kevin]
 * @version 1.0
 * @since 2025-11-07
 * @see OpenAPI
 * @see Configuration
 * @see io.swagger.v3.oas.annotations.Operation
 */
@Configuration
public class OpenApiConfig {
    
	/**
	 * Crea y configura el bean principal de OpenAPI para la documentación de la API.
	 * <p>
	 * Este método construye la especificación completa de OpenAPI 3.0, incluyendo metadatos
	 * de la API, configuración de seguridad JWT, y definiciones de respuestas de error comunes
	 * que pueden ser referenciadas en toda la aplicación.
	 * </p>
	 * <p>
	 * <strong>Información de la API:</strong> Define el título, versión y descripción general
	 * que aparecerán en la interfaz de Swagger UI como encabezado de la documentación.
	 * </p>
	 * <p>
	 * <strong>Esquema de Seguridad:</strong> Configura el esquema de autenticación Bearer JWT
	 * que permite a los usuarios autenticarse en Swagger UI proporcionando un token JWT válido
	 * en el formato: {@code Bearer <token>}. El token debe ser incluido en el encabezado
	 * HTTP {@code Authorization} de cada petición protegida.
	 * </p>
	 * <p>
	 * <strong>Respuestas de Error Globales:</strong> Define dos respuestas de error reutilizables:
	 * </p>
	 * <ul>
	 *   <li><strong>UnauthorizedError (401):</strong> Se utiliza cuando el token JWT es inválido,
	 *       ha expirado, o no se ha proporcionado en peticiones que requieren autenticación.</li>
	 *   <li><strong>ForbiddenError (403):</strong> Se utiliza cuando el usuario está autenticado
	 *       pero no tiene los permisos necesarios para acceder al recurso solicitado.</li>
	 * </ul>
	 * <p>
	 * Cada respuesta de error incluye ejemplos en formato JSON que muestran la estructura
	 * esperada del mensaje de error, facilitando la comprensión del formato de respuesta
	 * para los consumidores de la API.
	 * </p>
	 * <p>
	 * <strong>Requisito de Seguridad Global:</strong> El método activa la seguridad Bearer JWT
	 * de forma global mediante {@code addSecurityItem}, lo que significa que todos los endpoints
	 * documentados mostrarán el botón "Authorize" en Swagger UI y requerirán autenticación
	 * por defecto. Los endpoints públicos pueden excluirse individualmente usando la anotación
	 * {@code @SecurityRequirement(name = "")}.
	 * </p>
	 * 
	 * @return una instancia completamente configurada de {@link OpenAPI} que contiene toda
	 *         la especificación de la documentación de la API, incluyendo información general,
	 *         esquemas de seguridad, componentes reutilizables y requisitos de autenticación.
	 * @see OpenAPI
	 * @see Info
	 * @see SecurityScheme
	 * @see Components
	 * @see ApiResponse
	 * @see SecurityRequirement
	 */
	@Bean
    public OpenAPI customOpenAPI() {

        Info info = new Info()
            .title("API de Usuarios")
            .version("1.0")
            .description("Documentación de la API para gestión de usuarios con JWT");

        SecurityScheme securityScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(SecurityScheme.In.HEADER)
            .name("Authorization");

        return new OpenAPI()
            .info(info)
            .components(
                new Components()
                    .addSecuritySchemes("bearerAuth", securityScheme)
                    .addResponses(
                        "UnauthorizedError",
                        new ApiResponse()
                            .description("No autenticado - Token JWT inválido o expirado")
                            .content(
                                new Content().addMediaType(
                                    "application/json",
                                    new MediaType().addExamples(
                                        "error",
                                        new Example()
                                            .value("{\"error\": \"No autorizado\", \"mensaje\": \"Token inválido o expirado\"}")
                                    )
                                )
                            )
                    )
                    .addResponses(
                        "ForbiddenError",
                        new ApiResponse()
                            .description("Acceso prohibido - No tienes permisos suficientes")
                            .content(
                                new Content().addMediaType(
                                    "application/json",
                                    new MediaType().addExamples(
                                        "error",
                                        new Example()
                                            .value("{\"error\": \"Acceso prohibido\", \"mensaje\": \"No tienes permisos para esta operación\"}")
                                    )
                                )
                            )
                    )
            )
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
	
	
	/** constructor vacio */
	public OpenApiConfig() {
		// TODO Auto-generated constructor stub
	}
}
