package co.edu.unbosque.proyectoia.configuration;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import co.edu.unbosque.proyectoia.entity.Usuario;
import co.edu.unbosque.proyectoia.entity.Role;
import co.edu.unbosque.proyectoia.repository.UsuarioRepository;
import co.edu.unbosque.proyectoia.util.AESUtil;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository userRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            Optional<Usuario> found = userRepo.findByCorreo(AESUtil.encrypt("admin"));
            if (found.isPresent()) {
                log.info("Admin already exists, skipping admin creating...");
            } else {
                Usuario admin = new Usuario();
                admin.setNombre(AESUtil.encrypt("Admin"));  // ✅ ESTO DEBE ESTAR
                admin.setCorreo(AESUtil.encrypt("admin"));
                admin.setContrasenia(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                admin.setValidarCodigo(true);
                admin.setHistorialJson("[]");  // ✅ ESTO TAMBIÉN
                
                userRepo.save(admin);
                log.info("Preloading admin user");
            }
        };
    }


    public LoadDatabase() {
    }
}
