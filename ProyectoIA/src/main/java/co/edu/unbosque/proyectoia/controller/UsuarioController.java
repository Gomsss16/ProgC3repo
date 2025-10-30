package co.edu.unbosque.proyectoia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import co.edu.unbosque.proyectoia.dto.UsuarioDTO;
import co.edu.unbosque.proyectoia.entity.Usuario;
import co.edu.unbosque.proyectoia.entity.Usuario.Role;
import co.edu.unbosque.proyectoia.service.UsuarioService;

@RestController
@RequestMapping("/Usuario")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081" })
@Transactional
@Tag(name = "Gestión de Usuarios", description = "Endpoints para administrar usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioServ;

	@Autowired
	private ModelMapper modelMapper;

	@Operation(summary = "Crear usuario con JSON")
	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> createNewWithJSON(@RequestBody UsuarioDTO newUsuario) {
		if (newUsuario.getNombre().contains("<") || newUsuario.getNombre().contains(">")) {
			return new ResponseEntity<>("Solicitud con caracteres inválidos", HttpStatus.BAD_REQUEST);
		}

		if (newUsuario.getCorreo() == null || newUsuario.getCorreo().isBlank()) {
			return new ResponseEntity<>("El correo es obligatorio", HttpStatus.BAD_REQUEST);
		}

		Usuario entity = modelMapper.map(newUsuario, Usuario.class);
		UsuarioDTO dtoMapped = modelMapper.map(entity, UsuarioDTO.class);

		int status = usuarioServ.create(dtoMapped);

		if (status == 0) {
			return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el usuario, posiblemente el nombre o correo ya está en uso",
					HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@Operation(summary = "Crear usuario")
	@PostMapping(path = "/create")
	ResponseEntity<String> createNew(@RequestParam String usuarioname,
			@RequestParam String password,
			@RequestParam String correo,
			@RequestParam Role role) {

		if (correo == null || correo.isBlank()) {
			return new ResponseEntity<>("El correo es obligatorio", HttpStatus.BAD_REQUEST);
		}

		UsuarioDTO newUsuario = new UsuarioDTO(null, usuarioname, correo, password, null, false, role);
		if (role != null) {
			newUsuario.setRole(role);
		}

		int status = usuarioServ.create(newUsuario);

		if (status == 0) {
			return new ResponseEntity<>("Usuario creado exitosamente", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el usuario, posiblemente el nombre o correo ya está en uso",
					HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@Operation(summary = "Obtener todos los usuarios")
	@GetMapping("/getall")
	ResponseEntity<List<UsuarioDTO>> getAll() {
		List<UsuarioDTO> usuarios = usuarioServ.getAll();
		if (usuarios.isEmpty()) {
			return new ResponseEntity<>(usuarios, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(usuarios, HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Contar usuarios")
	@GetMapping("/count")
	ResponseEntity<Long> countAll() {
		Long count = usuarioServ.count();
		if (count == 0) {
			return new ResponseEntity<>(count, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(count, HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Verificar existencia de usuario")
	@GetMapping("/exists/{id}")
	ResponseEntity<Boolean> exists(@PathVariable Long id) {
		boolean found = usuarioServ.exist(id);
		return found ? new ResponseEntity<>(true, HttpStatus.ACCEPTED)
				: new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
	}

	@Operation(summary = "Obtener usuario por ID")
	@GetMapping("/getbyid/{id}")
	ResponseEntity<UsuarioDTO> getById(@PathVariable Long id) {
		UsuarioDTO found = usuarioServ.getById(id);
		if (found != null) {
			return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(new UsuarioDTO(), HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Actualizar usuario (JSON)")
	@PutMapping(path = "/updatejson", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> updateNewWithJSON(@RequestParam Long id, @RequestBody UsuarioDTO newUsuario) {
		int status = usuarioServ.updateById(id, newUsuario);

		return switch (status) {
		case 0 -> new ResponseEntity<>("Usuario actualizado exitosamente", HttpStatus.ACCEPTED);
		case 1 -> new ResponseEntity<>("El nuevo nombre de usuario ya está en uso", HttpStatus.IM_USED);
		case 2 -> new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
		default -> new ResponseEntity<>("Error al actualizar", HttpStatus.BAD_REQUEST);
		};
	}

	@Operation(summary = "Eliminar usuario por ID")
	@DeleteMapping("/deletebyid/{id}")
	ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = usuarioServ.deleteById(id);
		return status == 0 ? new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.ACCEPTED)
				: new ResponseEntity<>("Error al eliminar", HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Eliminar usuario por nombre")
	@DeleteMapping("/deletebyname")
	ResponseEntity<String> deleteByName(@RequestParam String name) {
		int status = usuarioServ.deleteByNombre(name);
		return status == 0 ? new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.ACCEPTED)
				: new ResponseEntity<>("Error al eliminar", HttpStatus.NOT_FOUND);
	}
}
