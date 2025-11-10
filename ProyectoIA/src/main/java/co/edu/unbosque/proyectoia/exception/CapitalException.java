package co.edu.unbosque.proyectoia.exception;

/**
 * Excepción que se lanza cuando una contraseña no contiene letras mayúsculas.
 */
public class CapitalException extends Exception {

    /**
     * Versión para la serialización.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Crea una excepción con un mensaje predeterminado.
     */
    public CapitalException() {
        super("La contraseña debe contener al menos una letra mayúscula.");
    }

    /**
     * Crea una excepción con un mensaje personalizado.
     *
     * @param mensaje Mensaje de error personalizado.
     */
    public CapitalException(String mensaje) {
        super(mensaje);
    }
}
