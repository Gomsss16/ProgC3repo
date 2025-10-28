package co.edu.unbosque.proyectoia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.proyectoia.entity.Usuario;



public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	public Optional<Usuario> findByCorreo(String usuario);
	public void deleteByCorreo(String usuario);
	public boolean existsByCorreo(String usuario);

}
