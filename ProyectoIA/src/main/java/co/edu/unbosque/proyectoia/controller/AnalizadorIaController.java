package co.edu.unbosque.proyectoia.controller;

import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class AnalizadorIaController {
	
//	 @Autowired
//	    private RestTemplate restTemplate;
//
//	    @PostMapping("/analizar")
//	    public ResponseEntity<Map<String, Object>> analizarArchivo(@RequestParam("file") MultipartFile file) throws Exception {
//	        String texto = new String(file.getBytes(), StandardCharsets.UTF_8);
//
//	        Map<String, Object> resultados = new HashMap<>();
//	        resultados.put("perplexity", consultarPerplexity(texto));
//	        resultados.put("deepseek", consultarDeepSeek(texto));
//	        resultados.put("chatgpt", consultarOpenAI(texto));
//	        resultados.put("claude", consultarClaude(texto));
//	        resultados.put("gemini", consultarGemini(texto));
//	        resultados.put("copilot", consultarCopilot(texto));
//
//	        return ResponseEntity.ok(resultados);
//	    }
//
//	    // Ejemplo genérico: cada método debe ajustarse según la API real
//	    private Double consultarPerplexity(String texto) {
//	        String apiUrl = "https://api.perplexity.ai/v1/your-endpoint";
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.setContentType(MediaType.APPLICATION_JSON);
//	        headers.set("Authorization", "Bearer TU_API_KEY_PERPLEXITY");
//
//	        // Adapta el JSON según el API real
//	        String body = "{ "prompt": "¿Este texto fue generado por IA? " + texto.replace(""", "") + "" }";
//
//	        HttpEntity<String> request = new HttpEntity<>(body, headers);
//	        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
//
//	        // Procesa el JSON y extrae el score (aquí se simula para ejemplo)
//	        return parsearScore(response.getBody());
//	    }
//
//	    private Double consultarDeepSeek(String texto) {
//	        // Igual al anterior, cambia apiUrl, headers y body según doc DeepSeek
//	        return Math.random() * 100; // Simulado
//	    }
//
//	    private Double consultarOpenAI(String texto) {
//	        // Igual, cambia apiUrl, headers y body según doc OpenAI
//	        return Math.random() * 100; // Simulado
//	    }
//
//	    private Double consultarClaude(String texto) {
//	        // Igual, cambia apiUrl, headers y body según doc Anthropic Claude
//	        return Math.random() * 100; // Simulado
//	    }
//
//	    private Double consultarGemini(String texto) {
//	        // Igual, cambia apiUrl, headers y body según doc Gemini
//	        return Math.random() * 100; // Simulado
//	    }
//
//	    private Double consultarCopilot(String texto) {
//	        // Igual, cambia apiUrl, headers y body según doc Copilot
//	        return Math.random() * 100; // Simulado
//	    }
//
//	    private Double parsearScore(String responseBody) {
//	        // Debes implementar la lógica que decodifica el JSON y extrae el score de IA
//	        return Math.random() * 100; // Simulado para ejemplo
//	    }
//	}

}
