package co.edu.unbosque.proyectoia.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.artemisa.entity.Administrador;
import co.edu.unbosque.artemisa.util.AESUtil;

import co.edu.unbosque.proyectoia.dto.UsuarioDTO;
import co.edu.unbosque.proyectoia.entity.Usuario;
import co.edu.unbosque.proyectoia.repository.UsuarioRepository;

@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO> {


	private Map<String, String> codigosEnMemoria = new HashMap<>();

	@Autowired
	private UsuarioRepository usuarioRepo;

	@Autowired
	private CorreoService correoService;

   
	@Override
	public int create(UsuarioDTO data) {

		try {

			if (data.getCorreo() == null || data.getCorreo().trim().isEmpty()) {
				System.out.println("ERROR: Usuario vacío");
				return 2;
			}

			if (data.getContrasenia() == null || data.getContrasenia().trim().isEmpty()) {
				System.out.println("ERROR: Contraseña vacía");
				return 2;
			}

			String codigo = generarCodigo(6);

			Usuario usuario = new Usuario();
			usuario.setNombre(AESUtil.encrypt(data.getNombre()));
			usuario.setCorreo(AESUtil.encrypt(data.getCorreo()));
			usuario.setContrasenia(AESUtil.encrypt(data.getContrasenia()));
			usuario.setValidarCodigo(false);

			if (findUsernameAlreadyTaken(usuario)) {
				System.out.println("ERROR: Usuario ya existe - " + data.getCorreo());
				return 1; // Usuario ya existe
			} else {
				usuarioRepo.save(usuario);
				String cuerpo = String.format("Hola %s,\n\n"
						+ "Gracias por registrarte en la aplicación Detector de IA.\n"
						+ "Para completar tu registro y activar tu cuenta, utiliza el siguiente código de verificación:\n\n"
						+ "   CÓDIGO: %s\n\n" + "El equipo de Detector de IA", data.getNombre(), codigo);
				correoService.enviarCorreo(data.getCorreo(), "Código de verificación de la app detector de IA", cuerpo);
				System.out.println("SUCCESS: usuario creado - " + data.getCorreo());
				return 0; // Éxito
			}
		} catch (Exception e) {
			System.err.println("ERROR en creación: " + e.getMessage());
			e.printStackTrace();
			return 2; // Error
		}
	}

	@Override
	public List getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateById(Long id, UsuarioDTO newData) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean exist(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	public String generarCodigo(int longitud) {
		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder codigo = new StringBuilder();
		Random rnd = new Random();

		for (int i = 0; i < longitud; i++) {
			int index = rnd.nextInt(caracteres.length());
			codigo.append(caracteres.charAt(index));
		}

		return codigo.toString();
	}
	
	public int authenticateUsuario(String correo, String password) {
        System.out.println("=== LOGIN ADMINISTRADOR ===");
        System.out.println("Usuario: " + correo);
        System.out.println("Password length: " + (password != null ? password.length() : 0));
        
        try {
            // Validaciones básicas
            if (correo == null || correo.trim().isEmpty()) {
                System.out.println("ERROR: Username vacío");
                return 1;
            }
            
            if (password == null || password.trim().isEmpty()) {
                System.out.println("ERROR: Password vacío");
                return 2;
            }
            
            // Encriptar username para buscar en BD
            String encryptedUsername = AESUtil.encrypt(correo);
            System.out.println("Username encriptado: " + encryptedUsername);
            
            // Buscar en la base de datos
            Optional<Usuario> usuarioFound = usuarioRepo.findByCorreo(encryptedUsername);
            System.out.println("Usuario encontrado: " + usuarioFound.isPresent());
            
            if (usuarioFound.isEmpty()) {
                System.out.println("ERROR: Usuario no encontrado - " + correo);
                return 1; // Usuario no encontrado
            }
            
            // Desencriptar contraseña almacenada
            Usuario usuario = usuarioFound.get();
            String decryptedPassword = AESUtil.decrypt(usuario.getContrasenia());
            System.out.println("Password almacenado desencriptado obtenido");
            System.out.println("Passwords coinciden: " + password.equals(decryptedPassword));
            
            if (password.equals(decryptedPassword)) {
                System.out.println("SUCCESS: Login exitoso - " + correo);
                return 0; // Login exitoso
            } else {
                System.out.println("ERROR: Contraseña incorrecta - " + correo);
                return 2; // Contraseña incorrecta
            }
            
        } catch (Exception e) {
            System.err.println("ERROR en login: " + e.getMessage());
            e.printStackTrace();
            return 3; // Error de sistema
        }
    }

	// ESTO SIRVE PARA CUANDO SE TENGA LA PARTE VISUAL, SE GENERA UN NUEVO CODIGO AL
	// USUARIO REGISTRADO PERO NO DEBE NI PONER EL ID O EL CORRO YA QUE EL SISTEMA
	// SE LO ENVIA A QUIEN SE ESTE LOGUEANDO

//	public boolean generarNuevoCodigoParaUsuario(Long usuarioId) {
//	    // Buscar usuario por ID
//	    Optional<Usuario> usuarioOpt = usuarioRepo.findById(usuarioId);
//	    if (usuarioOpt.isEmpty()) {
//	        return false; // Usuario no encontrado
//	    }
//
//	    Usuario usuario = usuarioOpt.get();
//
//	    // Generar un nuevo código
//	    String codigo = generarCodigo(6);
//	    usuario.setCodigoVerificacion(codigo); // solo en memoria, si está anotado con @Transient
//	    // No hace falta guardar en la base de datos si solo quieres que esté en memoria
//	    // usuarioRepo.save(usuario); // opcional
//
//	    // Enviar correo usando el correo que ya está en la base de datos
//	    correoService.enviarCorreo(AESUtil.decrypt(usuario.getCorreo()), 
//	                               "Nuevo código", "Tu nuevo código es: " + codigo);
//
//	    System.out.println("Código generado para " + AESUtil.decrypt(usuario.getCorreo()) + ": " + codigo);
//	    return true;
//	}

	public boolean findUsernameAlreadyTaken(Usuario newUser) {
		try {
			Optional<Usuario> found = usuarioRepo.findByCorreo(newUser.getCorreo());
			boolean exists = found.isPresent();
			System.out.println("Usuario duplicado: " + exists);
			return exists;
		} catch (Exception e) {
			System.err.println("Error verificando duplicado: " + e.getMessage());
			return false;
		}
	}

	public boolean generarNuevoCodigo(String correo) {
		String codigo = generarCodigo(6);
		codigosEnMemoria.put(correo, codigo); // Guardar en memoria

		correoService.enviarCorreo(correo, "Nuevo código", "Tu nuevo código es: " + codigo);

		System.out.println("Código generado para " + correo + ": " + codigo);
		return true;
	}

	public int verificarCodigo(String correo, String codigo) {
		String codigoEsperado = codigosEnMemoria.get(correo);
		System.out.println("codigo esperado:" + codigoEsperado);
		
		if (codigoEsperado == null) {
			System.out.println("entra aqui 2");
			return 1; // código no generado
		}
		if (!codigoEsperado.equals(codigo)) {
			return 2; // Código incorrecto
		}
		Usuario usuario = usuarioRepo.findByCorreo(AESUtil.encrypt(correo)).orElse(null);

		if (usuario == null) {
			System.out.println("entra aqui");
			return 1; // No existe el usuario con ese correo
		}

		System.out.println("ANTES" + usuario.toString());

	    usuario.setValidarCodigo(true);
	    usuarioRepo.save(usuario); 

	    System.out.println("DESPUES" + usuario.toString());

	    codigosEnMemoria.remove(correo); // Opcional: borrar al validar
	    return 0; // Código correcto
	}
	
	

}
