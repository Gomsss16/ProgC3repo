package co.edu.unbosque.proyectoia.controller;

import co.edu.unbosque.proyectoia.dto.AnalizadorIADTO;
import co.edu.unbosque.proyectoia.dto.ResultadoIADTO;
import co.edu.unbosque.proyectoia.service.AnalizadorIAService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


/**
 Controlador REST para el análisis de contenido mediante Inteligencia Artificial.
Proporciona endpoints para analizar texto, archivos, videos e imágenes utilizando servicios de IA.
Detecta patrones, genera estadísticas y entrega resultados detallados del análisis.
Ruta base: /api/analizador-ia
Permite solicitudes CORS desde cualquier origen. En producción, se recomienda restringir los orígenes por seguridad.
Formatos de archivo soportados:

.txt (texto plano UTF-8)
.pdf (usando Apache PDFBox)
.docx (usando Apache POI)
 */
@RestController
@RequestMapping("/api/analizador-ia")
@CrossOrigin(origins = "*")
public class AnalizadorIAController {

	/**
	 * Servicio de análisis de IA que contiene la lógica de negocio para procesar
	 * diferentes tipos de contenido y generar resultados de análisis mediante
	 * integración con APIs de Inteligencia Artificial.
	 */
	@Autowired
	private AnalizadorIAService analizadorIAService;

	/**
	 * Analiza un texto proporcionado directamente en el cuerpo de la petición.
	 * <p>
	 * Este endpoint recibe texto plano a través de un objeto DTO y lo procesa
	 * mediante servicios de IA para generar estadísticas de análisis, detección
	 * de patrones, o cualquier otra funcionalidad implementada en el servicio.
	 * </p>
	 * <p>
	 * <strong>Método HTTP:</strong> POST<br>
	 * <strong>Ruta:</strong> {@code /api/analizador-ia/analizar-texto}<br>
	 * <strong>Content-Type:</strong> application/json
	 * </p>
	 * 
	 * @param request objeto {@link AnalizadorIADTO} que contiene el texto a analizar
	 *                en su propiedad {@code texto}. No debe ser nulo y el texto
	 *                debe tener contenido válido.
	 * @return un {@link ResponseEntity} con código de estado 200 (OK) y un objeto
	 *         {@link ResultadoIADTO} que contiene los resultados del análisis,
	 *         incluyendo porcentajes de detección, promedios y otros datos relevantes.
	 * @see AnalizadorIAService#analizarTexto(String)
	 * @see AnalizadorIADTO
	 * @see ResultadoIADTO
	 */
	@PostMapping("/analizar-texto")
	public ResponseEntity<ResultadoIADTO> analizarTexto(@RequestBody AnalizadorIADTO request) {
		ResultadoIADTO resultado = analizadorIAService.analizarTexto(request.getTexto());
		return ResponseEntity.ok(resultado);
	}

	/**
	 * Analiza el contenido de un archivo cargado por el usuario.
	 * <p>
	 * Este endpoint acepta archivos en formatos .txt, .pdf y .docx, extrae su contenido
	 * de texto utilizando bibliotecas especializadas (Apache PDFBox para PDF, Apache POI
	 * para Word), y luego procesa el texto extraído mediante servicios de IA.
	 * </p>
	 * <p>
	 * <strong>Método HTTP:</strong> POST<br>
	 * <strong>Ruta:</strong> {@code /api/analizador-ia/analizar-archivo}<br>
	 * <strong>Content-Type:</strong> multipart/form-data
	 * </p>
	 * <p>
	 * El proceso incluye:
	 * </p>
	 * <ol>
	 *   <li>Recepción del archivo y registro de información básica (nombre, tamaño, tipo)</li>
	 *   <li>Extracción del texto según el formato del archivo</li>
	 *   <li>Validación de que se haya extraído texto válido</li>
	 *   <li>Análisis del texto mediante el servicio de IA</li>
	 *   <li>Retorno de los resultados del análisis</li>
	 * </ol>
	 * 
	 * @param archivo el {@link MultipartFile} que contiene el archivo a analizar.
	 *                Debe ser un archivo válido en formato .txt, .pdf o .docx.
	 *                El tamaño máximo del archivo está determinado por la configuración
	 *                de Spring Boot (propiedades {@code spring.servlet.multipart.max-file-size}
	 *                y {@code spring.servlet.multipart.max-request-size}).
	 * @return un {@link ResponseEntity} con:
	 *         <ul>
	 *           <li>Código 200 (OK) y {@link ResultadoIADTO} si el análisis es exitoso</li>
	 *           <li>Código 400 (Bad Request) con mensaje de error si no se pudo extraer texto</li>
	 *           <li>Código 500 (Internal Server Error) con mensaje de error si ocurre una excepción</li>
	 *         </ul>
	 * @see #extraerTextoDeArchivo(MultipartFile)
	 * @see AnalizadorIAService#analizarTexto(String)
	 * @see MultipartFile
	 */
	@PostMapping("/analizar-archivo")
	public ResponseEntity<?> analizarArchivo(@RequestParam("archivo") MultipartFile archivo) {
		try {

			String texto = extraerTextoDeArchivo(archivo);

			if (texto == null || texto.trim().isEmpty()) {
				Map<String, String> error = new HashMap<>();
				error.put("error", "No se pudo extraer texto del archivo");
				return ResponseEntity.badRequest().body(error);
			}

			ResultadoIADTO resultado = analizadorIAService.analizarTexto(texto);

			return ResponseEntity.ok(resultado);

		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> error = new HashMap<>();
			error.put("error", "Error al procesar el archivo: " + e.getMessage());
			return ResponseEntity.status(500).body(error);
		}
	}

	/**
	 * Analiza el contenido de un video cargado por el usuario.
	 * <p>
	 * <strong>Nota:</strong> Actualmente, este endpoint retorna datos simulados para
	 * demostración. La implementación completa de análisis de video mediante IA está
	 * pendiente de desarrollo.
	 * </p>
	 * <p>
	 * El análisis simulado genera porcentajes de detección para diferentes modelos de IA:
	 * GPT-4o-mini, Claude-3.5-Haiku, DeepSeek-V3, GPT-3.5-Turbo, Claude-3-Opus y GPT-4-Turbo.
	 * También calcula un promedio general de todos los modelos.
	 * </p>
	 * <p>
	 * <strong>Método HTTP:</strong> POST<br>
	 * <strong>Ruta:</strong> {@code /api/analizador-ia/analizar-video}<br>
	 * <strong>Content-Type:</strong> multipart/form-data
	 * </p>
	 * 
	 * @param video el {@link MultipartFile} que contiene el archivo de video a analizar.
	 *              Debe ser un archivo de video válido. El tamaño máximo está determinado
	 *              por la configuración de Spring Boot.
	 * @return un {@link ResponseEntity} con:
	 *         <ul>
	 *           <li>Código 200 (OK) y un {@code Map} que contiene:
	 *             <ul>
	 *               <li>{@code porcentajes}: mapa con porcentajes de detección por modelo de IA</li>
	 *               <li>{@code promedioGeneral}: promedio de todos los porcentajes</li>
	 *               <li>{@code nombreVideo}: nombre del archivo de video procesado</li>
	 *               <li>{@code mensaje}: mensaje indicando que son datos simulados</li>
	 *             </ul>
	 *           </li>
	 *           <li>Código 500 (Internal Server Error) con mensaje de error si ocurre una excepción</li>
	 *         </ul>
	 * @see MultipartFile
	 */
	@PostMapping("/analizar-video")
	public ResponseEntity<?> analizarVideo(@RequestParam("video") MultipartFile video) {
		try {

			Map<String, Double> porcentajes = new HashMap<>();
			porcentajes.put("GPT-4o-mini", 45.0);
			porcentajes.put("Claude-3.5-Haiku", 50.0);
			porcentajes.put("DeepSeek-V3", 48.0);
			porcentajes.put("GPT-3.5-Turbo", 42.0);
			porcentajes.put("Claude-3-Opus", 55.0);
			porcentajes.put("GPT-4-Turbo", 47.0);

			double promedio = porcentajes.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

			Map<String, Object> response = new HashMap<>();
			response.put("porcentajes", porcentajes);
			response.put("promedioGeneral", promedio);
			response.put("nombreVideo", video.getOriginalFilename());
			response.put("mensaje", "Análisis de video completado (datos simulados)");

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			Map<String, String> error = new HashMap<>();
			error.put("error", "Error al procesar el video: " + e.getMessage());
			return ResponseEntity.status(500).body(error);
		}
	}

	/**
	 * Analiza una imagen codificada en Base64 proporcionada en el cuerpo de la petición.
	 * <p>
	 * Este endpoint recibe una imagen codificada en formato Base64 a través de un objeto
	 * DTO y la procesa mediante servicios de IA especializados en análisis de imágenes
	 * (reconocimiento de contenido, detección de patrones, clasificación, etc.).
	 * </p>
	 * <p>
	 * <strong>Método HTTP:</strong> POST<br>
	 * <strong>Ruta:</strong> {@code /api/analizador-ia/analizar-imagen}<br>
	 * <strong>Content-Type:</strong> application/json
	 * </p>
	 * 
	 * @param request objeto {@link AnalizadorIADTO} que contiene la imagen codificada
	 *                en Base64 en su propiedad {@code imagenBase64}. La cadena debe ser
	 *                una representación Base64 válida de una imagen.
	 * @return un {@link ResponseEntity} con código de estado 200 (OK) y un objeto
	 *         {@link ResultadoIADTO} que contiene los resultados del análisis de imagen,
	 *         incluyendo clasificaciones, porcentajes de confianza y otros datos relevantes.
	 * @see AnalizadorIAService#analizarImagen(String)
	 * @see AnalizadorIADTO
	 * @see ResultadoIADTO
	 */
	@PostMapping("/analizar-imagen")
	public ResponseEntity<ResultadoIADTO> analizarImagen(@RequestBody AnalizadorIADTO request) {
		ResultadoIADTO resultado = analizadorIAService.analizarImagen(request.getImagenBase64());
		return ResponseEntity.ok(resultado);
	}

	/**
	 * Extrae el contenido de texto de un archivo cargado según su formato.
	 * <p>
	 * Este método privado es utilizado internamente por {@link #analizarArchivo(MultipartFile)}
	 * para extraer texto de diferentes formatos de archivo utilizando bibliotecas especializadas:
	 * </p>
	 * <ul>
	 *   <li><strong>.txt:</strong> Lee el archivo como texto plano con codificación UTF-8</li>
	 *   <li><strong>.pdf:</strong> Utiliza Apache PDFBox ({@link PDFTextStripper}) para extraer
	 *       texto de documentos PDF, procesando todas las páginas del documento</li>
	 *   <li><strong>.docx:</strong> Utiliza Apache POI ({@link XWPFWordExtractor}) para extraer
	 *       texto de documentos de Microsoft Word en formato OOXML</li>
	 * </ul>
	 * <p>
	 * El método determina el formato del archivo basándose en su extensión, convirtiéndola
	 * a minúsculas para comparación sin distinción de mayúsculas.
	 * </p>
	 * 
	 * @param archivo el {@link MultipartFile} del cual se extraerá el texto. Debe tener
	 *                un nombre de archivo válido con una extensión reconocida.
	 * @return el contenido de texto extraído del archivo como {@link String}. El texto
	 *         incluye todo el contenido legible del archivo, preservando la estructura
	 *         en la medida que lo permita el formato original.
	 * @throws Exception si ocurre un error durante la lectura del archivo o el proceso
	 *                   de extracción de texto (problemas de I/O, archivo corrupto, etc.)
	 * @throws UnsupportedOperationException si el formato del archivo no está soportado
	 *                   (extensión diferente de .txt, .pdf o .docx)
	 * @see MultipartFile
	 * @see PDDocument
	 * @see PDFTextStripper
	 * @see XWPFDocument
	 * @see XWPFWordExtractor
	 */
	String extraerTextoDeArchivo(MultipartFile archivo) throws Exception {
		String nombreArchivo = archivo.getOriginalFilename();
		String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".")).toLowerCase();

		switch (extension) {
			case ".txt":
				return new String(archivo.getBytes(), StandardCharsets.UTF_8);

			case ".pdf":
				try (InputStream inputStream = archivo.getInputStream();
					 PDDocument document = PDDocument.load(inputStream)) {
					PDFTextStripper stripper = new PDFTextStripper();
					return stripper.getText(document);
				}

			case ".docx":
				try (InputStream inputStream = archivo.getInputStream();
					 XWPFDocument document = new XWPFDocument(inputStream);
					 XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
					return extractor.getText();
				}

			default:
				throw new UnsupportedOperationException("Tipo de archivo no soportado: " + extension);
		}
	}
	
	/** Constructor */
	public AnalizadorIAController() {
		// TODO Auto-generated constructor stub
	}
}
