package co.edu.unbosque.proyectoia.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.proyectoia.entity.Usuario;
import co.edu.unbosque.proyectoia.repository.UsuarioRepository;

@RestController
public class VerificacionController {

	@Autowired
	private UsuarioRepository usuarioRepo;

	@GetMapping("/verificarcorreo")
	public String verificarUsuario(@RequestParam String token) {
		Optional<Usuario> usuarioOpt = usuarioRepo.findByToken(token);
		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();
			usuario.setVerificado(true);
			usuario.setToken(null); 
			usuarioRepo.save(usuario);
			return "Usuario verificado correctamente!";
		} else {
			return "Código de verificación inválido o expirado.";
		}
	}

}
