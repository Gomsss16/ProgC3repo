package co.edu.unbosque.proyectoia.dto;

import java.util.Objects;

import co.edu.unbosque.proyectoia.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class UsuarioDTO {
	
	private Long id;
	private String nombre;
	private String correo;
	private String contrasenia;
	private Role role;
	private String codigoVerificacion;
	private boolean validarCodigo;

	public UsuarioDTO() {
		// TODO Auto-generated constructor stub
	}

	public UsuarioDTO(String nombre, String correo, String codigoVerificacion, boolean validarCodigo) {
		super();
		this.nombre = nombre;
		this.correo = correo;
//		this.contrasenia = contrasenia;
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
	
	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", correo=" + correo
				+ ", codigoVerificacion=" + codigoVerificacion + ", validarCodigo=" + validarCodigo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoVerificacion, correo, id, nombre, validarCodigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDTO other = (UsuarioDTO) obj;
		return Objects.equals(codigoVerificacion, other.codigoVerificacion)
				 && Objects.equals(correo, other.correo)
				&& Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& validarCodigo == other.validarCodigo;
	}

	

	
	
	
	

}
