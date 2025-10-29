package co.edu.unbosque.proyectoia.entity;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


@Entity
@Table(name = "usuario")
//UserDetails es una interfaz propia de Spring Security que representa la información del usuario autenticado dentro del sistema de seguridad.
//Spring Security no sabe qué es un Usuario en tu aplicación (solo entiende su propio modelo de autenticación)
public class Usuario implements UserDetails {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private String nombre;
	private String correo;
	private String contrasenia;
	@Enumerated(EnumType.STRING)
	private Role role;
	@Transient
	private String codigoVerificacion;
	private boolean validarCodigo;

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(String nombre, String correo, String contrasenia, Role role, String codigoVerificacion,
			boolean validarCodigo) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.contrasenia = contrasenia;
		this.role = role;
		this.codigoVerificacion = codigoVerificacion;
		this.validarCodigo = validarCodigo;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getCodigoVerificacion() {
		return codigoVerificacion;
	}

	public void setCodigoVerificacion(String codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}

	public boolean isValidarCodigo() {
		return validarCodigo;
	}

	public void setValidarCodigo(boolean validarCodigo) {
		this.validarCodigo = validarCodigo;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", contrasenia=" + contrasenia
				+ ", codigoVerificacion=" + codigoVerificacion + ", validarCodigo=" + validarCodigo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoVerificacion, contrasenia, correo, id, nombre, validarCodigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(codigoVerificacion, other.codigoVerificacion)
				&& Objects.equals(contrasenia, other.contrasenia) && Objects.equals(correo, other.correo)
				&& Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& validarCodigo == other.validarCodigo;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
    public String getPassword() {
        return contrasenia;
    }

    @Override
    public String getUsername() {
        return correo;
    }

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	
	

}
