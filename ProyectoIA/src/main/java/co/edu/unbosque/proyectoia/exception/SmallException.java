package co.edu.unbosque.proyectoia.exception;

/**
 * Excepción que se lanza cuando una contraseña no tiene letras minúsculas.
 */
public class SmallException extends Exception {

    /**
     * Versión para la serialización.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Crea una excepción con un mensaje predeterminado.
     */
    public SmallException() {
        super("La contraseña debe contener al menos una letra minúscula.");
    }

    /**
     * Crea una excepción con un mensaje personalizado.
     *
     * @param mensaje Mensaje de error personalizado.
     */
    public SmallException(String mensaje) {
        super(mensaje);
    }
}
