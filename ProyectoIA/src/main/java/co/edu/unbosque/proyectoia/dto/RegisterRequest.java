package co.edu.unbosque.proyectoia.dto;

public class RegisterRequest {

	private String nombre;
	private String correo;
	private String contrasenia;
	
	public RegisterRequest(String nombre, String correo, String contrasenia) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.contrasenia = contrasenia;
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
	
}
