package co.edu.unbosque.proyectoia.configuration;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unbosque.proyectoia.repository.UsuarioRepository;


@Configuration
public class LoadDatabase {
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

//	@Bean
//	CommandLineRunner initDatabase(UsuarioRepository userRepo) {
//
//		return args -> {
//			// Optional<User> found = userRepo.findByUsername(AESUtil.encrypt("admin"));
//			Optional<User> found = userRepo.findByUsername("admin");
//			if (found.isPresent()) {
//				log.info("Admin already exists,  skipping admin creating  ...");
//			} else {
//				// userRepo.save(new User(AESUtil.encrypt("admin"),
//				// AESUtil.encrypt("1234567890")));
//				userRepo.save(new User("admin", "1234567890"));
//				log.info("Preloading admin user");
//			}
//		};
//	}

}
