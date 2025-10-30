package co.edu.unbosque.proyectoia.dto;

import java.util.Objects;
import co.edu.unbosque.proyectoia.entity.Usuario.Role;

public class UsuarioDTO {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nombre;

	private String correo;

	private String contrasenia;

	private String token;

	private boolean verificado;

	private String validarCodigo;

	private Role role;

	public UsuarioDTO() {
	}
	

	public UsuarioDTO(Long id, String nombre, String correo, String contrasenia, String token, boolean verificado, Role role) {
	    this.id = id;
	    this.nombre = nombre;
	    this.correo = correo;
	    this.contrasenia = contrasenia;
	    this.token = token;
	    this.verificado = verificado;
	    this.role = role;
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


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public boolean isVerificado() {
		return verificado;
	}


	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}


	public String getValidarCodigo() {
		return validarCodigo;
	}


	public void setValidarCodigo(String validarCodigo) {
		this.validarCodigo = validarCodigo;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public int hashCode() {
		return Objects.hash(contrasenia, correo, id, nombre, role, token, validarCodigo, verificado);
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
		return Objects.equals(contrasenia, other.contrasenia) && Objects.equals(correo, other.correo)
				&& Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre) && role == other.role
				&& Objects.equals(token, other.token) && Objects.equals(validarCodigo, other.validarCodigo)
				&& verificado == other.verificado;
	}


	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", contrasenia=" + contrasenia
				+ ", token=" + token + ", verificado=" + verificado + ", validarCodigo=" + validarCodigo + ", role="
				+ role + "]";
	}
	

}
