package co.edu.unbosque.proyectoia.dto;

import co.edu.unbosque.proyectoia.entity.Role;

/**
 * Clase DTO (Data Transfer Object) que representa la información de un usuario.
 * Contiene detalles como el identificador, nombre, correo, contraseña, rol,
 * código de verificación y estado de validación del código.
 */
public class UsuarioDTO {

	/** Identificador único del usuario. */
	private Long id;

	/** Nombre del usuario. */
	private String nombre;

	/** Correo electrónico del usuario. */
	private String correo;

	/** Contraseña del usuario. */
	private String contrasenia;

	/** Rol asignado al usuario. */
	private Role role;

	/** Código de verificación para validar la cuenta del usuario. */
	private String codigoVerificacion;

	/** Estado que indica si el código de verificación ha sido validado. */
	private boolean validarCodigo;

	/**
	 * Constructor por defecto de la clase {@code UsuarioDTO}.
	 */
	public UsuarioDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor que permite crear un objeto UsuarioDTO con todos sus atributos.
	 *
	 * @param id                 Identificador único del usuario.
	 * @param nombre             Nombre del usuario.
	 * @param correo             Correo electrónico del usuario.
	 * @param contrasenia        Contraseña asociada al usuario.
	 * @param role               Rol asignado al usuario dentro del sistema.
	 * @param codigoVerificacion Código utilizado para la verificación de cuenta o
	 *                           recuperación.
	 * @param validarCodigo      Valor booleano que indica si el código de
	 *                           verificación ha sido validado correctamente.
	 */
	public UsuarioDTO(Long id, String nombre, String correo, String contrasenia, Role role, String codigoVerificacion,
			boolean validarCodigo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.contrasenia = contrasenia;
		this.role = role;
		this.codigoVerificacion = codigoVerificacion;
		this.validarCodigo = validarCodigo;
	}

	/**
	 * Constructor parametrizado para inicializar un objeto {@code UsuarioDTO}.
	 *
	 * @param nombre             Nombre del usuario.
	 * @param correo             Correo electrónico del usuario.
	 * @param codigoVerificacion Código de verificación del usuario.
	 * @param validarCodigo      Estado de validación del código.
	 */
	public UsuarioDTO(String nombre, String correo, String codigoVerificacion, boolean validarCodigo) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.codigoVerificacion = codigoVerificacion;
		this.validarCodigo = validarCodigo;
	}

	/**
	 * Obtiene el identificador único del usuario.
	 *
	 * @return El identificador único.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del usuario.
	 *
	 * @param id Identificador único a asignar.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del usuario.
	 *
	 * @return El nombre del usuario.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del usuario.
	 *
	 * @param nombre Nombre del usuario a asignar.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el correo electrónico del usuario.
	 *
	 * @return El correo electrónico del usuario.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo electrónico del usuario.
	 *
	 * @param correo Correo electrónico a asignar.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene el código de verificación del usuario.
	 *
	 * @return El código de verificación.
	 */
	public String getCodigoVerificacion() {
		return codigoVerificacion;
	}

	/**
	 * Establece el código de verificación del usuario.
	 *
	 * @param codigoVerificacion Código de verificación a asignar.
	 */
	public void setCodigoVerificacion(String codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}

	/**
	 * Indica si el código de verificación ha sido validado.
	 *
	 * @return {@code true} si el código ha sido validado, {@code false} en caso
	 *         contrario.
	 */
	public boolean isValidarCodigo() {
		return validarCodigo;
	}

	/**
	 * Establece el estado de validación del código de verificación.
	 *
	 * @param validarCodigo Estado de validación a asignar.
	 */
	public void setValidarCodigo(boolean validarCodigo) {
		this.validarCodigo = validarCodigo;
	}

	/**
	 * Obtiene la contraseña del usuario.
	 *
	 * @return La contraseña del usuario.
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * Establece la contraseña del usuario.
	 *
	 * @param contrasenia Contraseña a asignar.
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * Obtiene el rol asignado al usuario.
	 *
	 * @return El rol del usuario.
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Establece el rol del usuario.
	 *
	 * @param role Rol a asignar.
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * Devuelve una representación en cadena del objeto.
	 *
	 * @return Cadena que representa el objeto, incluyendo sus atributos
	 *         principales.
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", codigoVerificacion="
				+ codigoVerificacion + ", validarCodigo=" + validarCodigo + "]";
	}
}
