package co.edu.unbosque.proyectoia.exception;

/**
 * Clase la cual es publica para ser llamada dentro de otras clases la cual es
 * heredada de Exception
 */
public class EqualPasswordException extends Exception {

	/** Versión serial para la serialización de la excepción. */
	private static final long serialVersionUID = 1L;

	/**
	 * Se crea un constructor el cual no recibe ningun tipo de parametro se llama el
	 * super como constructor de la clase madre recibiendo un texto.
	 */
	public EqualPasswordException() {
		super("Las contraseñas deben coincidir.");
	}
}
