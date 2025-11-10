package co.edu.unbosque.proyectoia.controller;

import co.edu.unbosque.proyectoia.dto.AnalizadorIADTO;
import co.edu.unbosque.proyectoia.dto.ResultadoIADTO;
import co.edu.unbosque.proyectoia.service.AnalizadorIAService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnalizadorIAController.class)
@Import(SecurityTestConfig.class)
class AnalizadorIAControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalizadorIAService analizadorIAService;

    @Test
    void analizarTexto_DeberiaRetornarResultado() throws Exception {
        ResultadoIADTO resultadoMock = new ResultadoIADTO();
        resultadoMock.setMensaje("Análisis completado");

        when(analizadorIAService.analizarTexto(anyString())).thenReturn(resultadoMock);

        mockMvc.perform(post("/api/analizador-ia/analizar-texto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"texto\":\"Este es un texto de prueba.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Análisis completado"));
    }

    @Test
    void analizarArchivo_DeberiaRetornarResultado() throws Exception {
        ResultadoIADTO resultadoMock = new ResultadoIADTO();
        resultadoMock.setMensaje("Análisis completado");

        when(analizadorIAService.analizarTexto(anyString())).thenReturn(resultadoMock);

        MockMultipartFile archivoMock = new MockMultipartFile(
                "archivo", "prueba.txt", "text/plain", "Contenido de prueba".getBytes()
        );

        mockMvc.perform(multipart("/api/analizador-ia/analizar-archivo")
                        .file(archivoMock))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Análisis completado"));
    }

    @Test
    void analizarVideo_DeberiaRetornarDatosSimulados() throws Exception {
        MockMultipartFile videoMock = new MockMultipartFile(
                "video", "video.mp4", "video/mp4", "Contenido de video".getBytes()
        );

        mockMvc.perform(multipart("/api/analizador-ia/analizar-video")
                        .file(videoMock))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreVideo").value("video.mp4"))
                .andExpect(jsonPath("$.mensaje").value("Análisis de video completado "));
    }

    @Test
    void analizarImagen_DeberiaRetornarResultado() throws Exception {
        ResultadoIADTO resultadoMock = new ResultadoIADTO();
        resultadoMock.setMensaje("Análisis completado");

        when(analizadorIAService.analizarImagen(anyString())).thenReturn(resultadoMock);

        mockMvc.perform(post("/api/analizador-ia/analizar-imagen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"imagenBase64\":\"base64encodedimage\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Análisis completado"));
    }
}
