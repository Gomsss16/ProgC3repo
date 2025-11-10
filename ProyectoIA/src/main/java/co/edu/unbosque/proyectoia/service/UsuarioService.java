package co.edu.unbosque.proyectoia.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import co.edu.unbosque.proyectoia.dto.UsuarioDTO;
import co.edu.unbosque.proyectoia.entity.TipoAccion;
import co.edu.unbosque.proyectoia.entity.Usuario;
import co.edu.unbosque.proyectoia.repository.UsuarioRepository;
import co.edu.unbosque.proyectoia.util.AESUtil;
import co.edu.unbosque.proyectoia.util.CorreoUtil;

@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO> {

    private Map<String, String> codigosEnMemoria = new HashMap<>();

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private CorreoUtil correoUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public UsuarioService() {
    }

    @Override
    public int create(UsuarioDTO data) {
        try {
            if (data.getCorreo() == null || data.getCorreo().trim().isEmpty()) {
                return 2;
            }
            if (data.getContrasenia() == null || data.getContrasenia().trim().isEmpty()) {
                return 2;
            }
            String codigo = generarCodigo(6);
            Usuario usuario = new Usuario();
            usuario.setNombre(AESUtil.encrypt(data.getNombre()));
            usuario.setCorreo(AESUtil.encrypt(data.getCorreo()));
            usuario.setContrasenia(passwordEncoder.encode(data.getContrasenia()));
            usuario.setValidarCodigo(false);

            if (data.getRole() != null) {
                usuario.setRole(data.getRole());
            }
            if (findUsernameAlreadyTaken(usuario)) {
                return 1;
            } else {
                codigosEnMemoria.put(data.getCorreo(), codigo);
                usuarioRepo.save(usuario);
                String cuerpo = String.format("Hola %s,\n\n"
                        + "Gracias por registrarte en la aplicación Detector de IA.\n"
                        + "Para completar tu registro y activar tu cuenta, utiliza el siguiente código de verificación:\n\n"
                        + "   CÓDIGO: %s\n\n" + "El equipo de Detector de IA", data.getNombre(), codigo);
                correoUtil.enviarCorreo(data.getCorreo(), "Código de verificación de la app detector de IA", cuerpo);
                return 0;
            }
        } catch (Exception e) {
            return 2;
        }
    }

    @Override
    public List<UsuarioDTO> getAll() {
        List<Usuario> entityList = usuarioRepo.findAll();
        List<UsuarioDTO> dtoList = new ArrayList<>();
        
        for (Usuario entity : entityList) {
            try {
                UsuarioDTO dto = new UsuarioDTO();
                dto.setId(entity.getId());
                
                if (entity.getNombre() != null && !entity.getNombre().isEmpty()) {
                    dto.setNombre(AESUtil.decrypt(entity.getNombre()));
                } else {
                    dto.setNombre("Sin nombre");
                }
                
                if (entity.getCorreo() != null && !entity.getCorreo().isEmpty()) {
                    dto.setCorreo(AESUtil.decrypt(entity.getCorreo()));
                } else {
                    dto.setCorreo("Sin correo");
                }
                
                dto.setRole(entity.getRole());
                dtoList.add(dto);
            } catch (Exception e) {
                System.err.println("Error desencriptando usuario ID " + entity.getId() + ": " + e.getMessage());
                continue;
            }
        }
        
        return dtoList;
    }

    @Override
    public UsuarioDTO getById(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepo.findById(id);
        if (optionalUsuario.isEmpty()) {
            return null;
        }
        Usuario entity = optionalUsuario.get();
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entity.getId());
        dto.setNombre(AESUtil.decrypt(entity.getNombre()));
        dto.setCorreo(AESUtil.decrypt(entity.getCorreo()));
        dto.setRole(entity.getRole());
        return dto;
    }

    @Override
    public int deleteById(Long id) {
        if (!usuarioRepo.existsById(id)) {
            return 1;
        }
        usuarioRepo.deleteById(id);
        return 0;
    }

    @Override
    public int updateById(Long id, UsuarioDTO newData) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findById(id);
        if (usuarioOpt.isEmpty()) {
            return 1;
        }

        try {
            Usuario usuario = usuarioOpt.get();
            if (newData.getNombre() != null) {
                usuario.setNombre(AESUtil.encrypt(newData.getNombre()));
            }
            if (newData.getCorreo() != null) {
                usuario.setCorreo(AESUtil.encrypt(newData.getCorreo()));
            }
            if (newData.getContrasenia() != null) {
                usuario.setContrasenia(passwordEncoder.encode(newData.getContrasenia()));
            }
            if (newData.getRole() != null) {
                usuario.setRole(newData.getRole());
            }
            usuarioRepo.save(usuario);
            return 0;
        } catch (Exception e) {
            return 2;
        }
    }

    @Override
    public long count() {
        return usuarioRepo.count();
    }

    @Override
    public boolean exist(Long id) {
        return usuarioRepo.existsById(id);
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

    public int autenticarUsuario(String correo, String password) {
        try {
            if (correo == null || correo.trim().isEmpty()) {
                return 1;
            }

            if (password == null || password.trim().isEmpty()) {
                return 2;
            }

            String encryptedUsername = AESUtil.encrypt(correo);
            Optional<Usuario> usuarioFound = usuarioRepo.findByCorreo(encryptedUsername);

            if (usuarioFound.isEmpty()) {
                return 1;
            }

            Usuario usuario = usuarioFound.get();
            String decryptedPassword = AESUtil.decrypt(usuario.getContrasenia());

            if (password.equals(decryptedPassword)) {
                return 0;
            } else {
                return 2;
            }

        } catch (Exception e) {
            return 3;
        }
    }

    @Override
    public boolean findUsernameAlreadyTaken(Usuario newUser) {
        try {
            Optional<Usuario> found = usuarioRepo.findByCorreo(newUser.getCorreo());
            return found.isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean generarNuevoCodigo(String correo) {
        String codigo = generarCodigo(6);
        codigosEnMemoria.put(correo, codigo);
        correoUtil.enviarCorreo(correo, "Nuevo código", "Tu nuevo código es: " + codigo);
        return true;
    }

    public int verificarCodigo(String correo, String codigo) {
        String codigoEsperado = codigosEnMemoria.get(correo);

        if (codigoEsperado == null) {
            return 1;
        }
        if (!codigoEsperado.trim().equals(codigo.trim())) {
            return 2;
        }
        Usuario usuario = usuarioRepo.findByCorreo(AESUtil.encrypt(correo)).orElse(null);
        if (usuario == null) {
            return 1;
        }
        usuario.setValidarCodigo(true);
        usuarioRepo.save(usuario);
        codigosEnMemoria.remove(correo);
        return 0;
    }

    @Override
    public boolean encontrarCorreoExitente(String username) {
        Optional<Usuario> found = usuarioRepo.findByCorreo(AESUtil.encrypt(username));
        return found.isPresent();
    }

    public Usuario obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepo.findByCorreo(AESUtil.encrypt(correo)).orElse(null);
    }

    public boolean isVerified(String correo) {
        Usuario usuario = obtenerUsuarioPorCorreo(correo);
        return usuario != null && usuario.isValidarCodigo();
    }

    public void registrarAccion(String correo, String tipo, String detalles, String ip) {
        try {
            Usuario usuario = obtenerUsuarioPorCorreo(correo);
            if (usuario != null) {
                List<Map<String, Object>> historial = objectMapper.readValue(
                    usuario.getHistorialJson(), 
                    new TypeReference<List<Map<String, Object>>>() {}
                );
                
                Map<String, Object> accion = new HashMap<>();
                accion.put("tipo", tipo);
                accion.put("detalles", detalles);
                accion.put("ip", ip);
                accion.put("fecha", LocalDateTime.now().toString());
                
                historial.add(0, accion);
                
                usuario.setHistorialJson(objectMapper.writeValueAsString(historial));
                usuarioRepo.save(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> obtenerHistorial(String correo) {
        try {
            Usuario usuario = obtenerUsuarioPorCorreo(correo);
            if (usuario != null) {
                return objectMapper.readValue(
                    usuario.getHistorialJson(), 
                    new TypeReference<List<Map<String, Object>>>() {}
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Agrega una acción al historial del usuario usando el enum TipoAccion.
     * 
     * @param correo correo del usuario
     * @param tipo tipo de acción realizada (enum TipoAccion)
     * @param detalles descripción de la acción
     * @param ip dirección IP desde donde se realizó la acción
     */
    public void agregarAccion(String correo, TipoAccion tipo, String detalles, String ip) {
        registrarAccion(correo, tipo.name(), detalles, ip);
    }
}
