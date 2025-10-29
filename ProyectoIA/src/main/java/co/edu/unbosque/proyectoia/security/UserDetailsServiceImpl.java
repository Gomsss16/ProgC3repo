package co.edu.unbosque.proyectoia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectoia.entity.Usuario;
import co.edu.unbosque.proyectoia.repository.UsuarioRepository;
import co.edu.unbosque.proyectoia.util.AESUtil;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository userRepository;
    

    public UserDetailsServiceImpl(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // 🔹 1. UserDetailsService — le dice a Spring cómo encontrar el usuario

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
    	
    	String correoEncriptado = AESUtil.encrypt(correo);
    	Usuario usuario = userRepository.findByCorreo(correoEncriptado)
    	            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    	
    	System.out.println("correo: " + correo);
    	System.out.println("correoEncriptado: " + correoEncriptado);
//        return userRepository.findByCorreo(correoEncriptado)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + correo));
  
    	return usuario;
    }
}