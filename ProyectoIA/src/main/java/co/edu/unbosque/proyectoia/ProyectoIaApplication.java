package co.edu.unbosque.proyectoia;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Clase principal de la aplicación Spring Boot ProyectoIA.
 * <p>
 * Esta clase sirve como punto de entrada para la aplicación y utiliza la anotación
 * {@code @SpringBootApplication} que combina tres anotaciones esenciales:
 * {@code @Configuration}, {@code @EnableAutoConfiguration} y {@code @ComponentScan}.
 * </p>
 * <p>
 * La anotación {@code @SpringBootApplication} habilita las siguientes características:
 * </p>
 * <ul>
 *   <li>Auto-configuración de Spring Boot basada en las dependencias del proyecto</li>
 *   <li>Escaneo de componentes en el paquete actual y sus subpaquetes</li>
 *   <li>Definición de beans de configuración adicionales</li>
 * </ul>
 * 
 * @version 1.0
 * @since 2025-11-07
 */
@SpringBootApplication
public class ProyectoIaApplication {

	/**
	 * Método principal que inicia la aplicación Spring Boot.
	 * <p>
	 * Este método utiliza {@link SpringApplication#run(Class, String...)} para
	 * arrancar la aplicación, configurar el contenedor de Spring y desplegar
	 * el servidor embebido.
	 * </p>
	 * 
	 * @param args argumentos de línea de comandos pasados al iniciar la aplicación.
	 *             Estos argumentos pueden ser utilizados para configurar propiedades
	 *             de la aplicación en tiempo de ejecución.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProyectoIaApplication.class, args);
	}
	
	/** constructor vacio */
	public ProyectoIaApplication() {
		// TODO Auto-generated constructor stub
	}
	
	/** Model Mapper*/
	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}
}
