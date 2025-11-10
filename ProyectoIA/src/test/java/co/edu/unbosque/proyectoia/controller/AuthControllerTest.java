package co.edu.unbosque.proyectoia.controller;

import co.edu.unbosque.proyectoia.dto.AuthResponse;
import co.edu.unbosque.proyectoia.dto.UsuarioDTO;
import co.edu.unbosque.proyectoia.entity.Usuario;
import co.edu.unbosque.proyectoia.security.JwtUtil;
import co.edu.unbosque.proyectoia.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(SecurityTestConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    void probarInicioSesionExitoso() throws Exception {
        String correo = "a@correo.com";
        String contrasena = "12345Aa.";

        List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority("USER"));
        User detallesUsuario = new User(correo, contrasena, roles);

        Authentication autenticacion = new UsernamePasswordAuthenticationToken(detallesUsuario, contrasena, roles);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(autenticacion);

        when(jwtUtil.generateToken(detallesUsuario)).thenReturn("tokenFalso123");

        String jsonEntrada = """
                {
                  "correo": "a@correo.com",
                  "contrasena": "12345Aa."
                }
                """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEntrada))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("tokenFalso123"));
    }

    @Test
    void probarCredencialesInvalidas() throws Exception {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new org.springframework.security.authentication.BadCredentialsException("Credenciales inválidas"));

        String jsonEntrada = """
                {
                  "correo": "a@correo.com",
                  "contrasena": "incorrecta"
                }
                """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEntrada))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Credenciales inválidas")));
    }

    @Test
    void probarCamposFaltantes() throws Exception {
        String jsonEntrada = "{}";
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEntrada))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El correo es obligatorio, La contraseña es obligatoria"));
    }


    @Test
    void probarRegistroExitoso() throws Exception {
        UsuarioDTO nuevoUsuario = new UsuarioDTO();
        nuevoUsuario.setCorreo("b@correo.com");
        nuevoUsuario.setContrasenia("12345Aa.");
        nuevoUsuario.setNombre("Kevin");

        when(usuarioService.create(any(UsuarioDTO.class))).thenReturn(0);

        String jsonEntrada = """
                {
                  "correo": "b@correo.com",
                  "contrasenia": "12345Aa.",
                  "nombre": "Kevin"
                }
                """;

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEntrada))
                .andExpect(status().isCreated())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Usuario registrado correctamente")));
    }

    @Test
    void probarRegistroUsuarioExistente() throws Exception {
        when(usuarioService.create(any(UsuarioDTO.class))).thenReturn(1);

        String jsonEntrada = """
                {
                  "correo": "b@correo.com",
                  "contrasenia": "12345Aa.",
                  "nombre": "Kevin"
                }
                """;

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEntrada))
                .andExpect(status().isConflict())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("El usuario ya existe")));
    }
}
