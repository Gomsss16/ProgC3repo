package co.edu.unbosque.proyectoia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectoia.dto.UsuarioDTO;
import co.edu.unbosque.proyectoia.entity.Usuario;
import co.edu.unbosque.proyectoia.repository.UsuarioRepository;

@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO> {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CorreoService correoService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public int create(UsuarioDTO dto) {
		if (dto.getCorreo() == null || dto.getContrasenia() == null)
			return 2;

		Optional<Usuario> existente = usuarioRepository.findByCorreo(dto.getCorreo());
		if (existente.isPresent())
			return 1;

		String token = UUID.randomUUID().toString();
		Usuario nuevo = modelMapper.map(dto, Usuario.class);
		nuevo.setVerificado(false);
		nuevo.setToken(token);
		nuevo.setContrasenia(passwordEncoder.encode(dto.getContrasenia()));
		usuarioRepository.save(nuevo);

		String url = "http://localhost:8081/swagger-ui/index.html#/verificacion-controller/verificarUsuario?token="
				+ token;
		String html = """
				    <div style="font-family:Arial;text-align:center;padding:20px;">
				        <h2>¡Bienvenido/a %s a ProyectoIA! 👋</h2>
				        <p>Haz clic en el botón para verificar tu cuenta:</p>
				        <a href="%s"
				           style="background-color:#4CAF50;color:white;padding:12px 24px;
				                  text-decoration:none;border-radius:6px;">
				            Verificar mi cuenta
				        </a>
				        <p style="margin-top:15px;">Si el botón no funciona, copia este enlace en tu navegador:</p>
				        <p><a href="%s">%s</a></p>
				    </div>
				""".formatted(dto.getNombre(), url, url, url);

		correoService.enviarCorreoHTML(dto.getCorreo(), "Verifica tu cuenta en ProyectoIA", html);
		return 0;
	}

	@Override
	public List<UsuarioDTO> getAll() {
		List<Usuario> entityList = usuarioRepository.findAll();
		List<UsuarioDTO> dtoList = new ArrayList<>();
		entityList.forEach(entity -> dtoList.add(modelMapper.map(entity, UsuarioDTO.class)));
		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if (usuario.isPresent()) {
			usuarioRepository.delete(usuario.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int deleteByNombre(String nombre) {
		Optional<Usuario> usuario = usuarioRepository.findByNombre(nombre);
		if (usuario.isPresent()) {
			usuarioRepository.delete(usuario.get());
			return 0;
		}
		return 1;
	}

	@Override
	public int updateById(Long id, UsuarioDTO newData) {
		Optional<Usuario> opt = usuarioRepository.findById(id);
		if (opt.isPresent()) {
			Usuario u = opt.get();
			u.setNombre(newData.getNombre());
			u.setCorreo(newData.getCorreo());
			if (newData.getContrasenia() != null && !newData.getContrasenia().isEmpty()) {
				u.setContrasenia(passwordEncoder.encode(newData.getContrasenia()));
			}
			usuarioRepository.save(u);
			return 0;
		}
		return 1;
	}

	@Override
	public Long count() {
		return usuarioRepository.count();
	}

	@Override
	public boolean exist(Long id) {
		return usuarioRepository.existsById(id);
	}

	@Override
	public UsuarioDTO getById(Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		return usuario.map(u -> modelMapper.map(u, UsuarioDTO.class)).orElse(null);
	}

	public boolean verificarPorToken(String token) {
		Optional<Usuario> usuario = usuarioRepository.findByToken(token);
		if (usuario.isPresent()) {
			Usuario u = usuario.get();
			u.setVerificado(true);
			u.setToken(null);
			usuarioRepository.save(u);
			return true;
		}
		return false;
	}

	public String generarNuevoCodigo(String correo) {
		Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);
		if (usuario.isEmpty())
			return null;

		String nuevoToken = UUID.randomUUID().toString();
		Usuario u = usuario.get();
		u.setToken(nuevoToken);
		usuarioRepository.save(u);
		return nuevoToken;
	}

	public int authenticateUsuario(String correo, String contrasenia) {
		Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);
		if (usuario.isEmpty())
			return 1;
		Usuario u = usuario.get();
		if (!passwordEncoder.matches(contrasenia, u.getContrasenia()))
			return 2;
		if (!u.isVerificado())
			return 3;
		return 0;
	}

	/**
	 * Verifica si un nombre de usuario ya está en uso.
	 *
	 * @param username Nombre de usuario a verificar
	 * @return true si el nombre de usuario ya está en uso, false en caso contrario
	 */
	public boolean findUsernameAlreadyTaken(String username) {
		Optional<Usuario> found = usuarioRepository.findByNombre(username);
		return found.isPresent();
	}
}
