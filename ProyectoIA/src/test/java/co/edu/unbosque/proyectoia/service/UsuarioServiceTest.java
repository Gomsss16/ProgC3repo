package co.edu.unbosque.proyectoia.service;

import co.edu.unbosque.proyectoia.dto.UsuarioDTO;
import co.edu.unbosque.proyectoia.entity.Usuario;
import co.edu.unbosque.proyectoia.entity.Role;
import co.edu.unbosque.proyectoia.repository.UsuarioRepository;
import co.edu.unbosque.proyectoia.util.CorreoUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas unitarias para el servicio {@link UsuarioService}.
 *
 * Se validan los principales métodos de lógica de negocio como creación,
 * actualización, eliminación y verificación de usuarios.
 */
@DataJpaTest
class UsuarioServiceTest {

	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	TestEntityManager testEntityManager;

	@BeforeEach
	void setUp() {
		Usuario usuario = new Usuario("aarevalodoblado@gmail.com", "12345678Kk%", "ADMIN", null, "ASDDD2", true);
		testEntityManager.persist(usuario);
	}

	@Test
	public void findUsuarioByCorreoFound() {
		Optional<Usuario> usuario = usuarioRepository.findByCorreo("aarevalodoblado@gmail.com");
		assertTrue(usuario.isPresent());
		assertEquals("aarevalodoblado@gmail.com", usuario.get().getCorreo());
	}

	@Test
	public void findUsuarioByCorreoNotFound() {
		Optional<Usuario> usuario = usuarioRepository.findByCorreo("Cinema");
		assertTrue(usuario.isEmpty());
	}
}
