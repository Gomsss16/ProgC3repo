package co.edu.unbosque.proyectoia.exception;

import java.util.InputMismatchException;

/**
 * Clase {@code ExceptionCheker}. Proporciona métodos para validar entradas
 * según diferentes criterios, como contraseñas, correos electrónicos, números,
 * y otros formatos específicos.
 */
public class ExceptionCheker {

    /**
     * Verifica que una contraseña cumpla con todos los criterios de validación:
     * al menos 8 caracteres, una letra mayúscula, una letra minúscula,
     * un número y un símbolo especial.
     *
     * @param contrasena La contraseña a validar.
     * @throws CharacterException Si no tiene al menos 8 caracteres.
     * @throws CapitalException   Si no contiene al menos una letra mayúscula.
     * @throws SmallException     Si no contiene al menos una letra minúscula.
     * @throws NumberException    Si no contiene al menos un número.
     * @throws SymbolException    Si no contiene al menos un símbolo especial.
     */
    public static void checkerPassword(String contrasena)
            throws CharacterException, CapitalException, SmallException, NumberException, SymbolException {

        checkerCharacter(contrasena);
        checkerCapital(contrasena);
        checkerSmall(contrasena);
        checkerNumber(contrasena);
        checkerSymbol(contrasena);
    }

    /**
     * Verifica que un correo electrónico tenga un formato válido.
     *
     * @param correo Correo electrónico a validar.
     * @throws InputMismatchException Si el formato del correo no es válido.
     * @throws MailException          Si el correo no cumple con los requisitos específicos.
     */
    public static void checkerMail(String correo) throws InputMismatchException, MailException {
        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new MailException();
        }
    }

    /**
     * Verifica que la contraseña tenga al menos 8 caracteres.
     *
     * @param contrasena La contraseña a validar.
     * @throws CharacterException Si la longitud es menor a 8 caracteres.
     */
    public static void checkerCharacter(String contrasena) throws CharacterException {
        if (contrasena.length() < 8) {
            throw new CharacterException();
        }
    }

    /**
     * Verifica que la contraseña contenga al menos una letra mayúscula.
     *
     * @param contrasena La contraseña a validar.
     * @throws CapitalException Si no contiene letras mayúsculas.
     */
    public static void checkerCapital(String contrasena) throws CapitalException {
        if (!contrasena.matches(".*[A-Z].*")) {
            throw new CapitalException();
        }
    }

    /**
     * Verifica que la contraseña contenga al menos una letra minúscula.
     *
     * @param contrasena La contraseña a validar.
     * @throws SmallException Si no contiene letras minúsculas.
     */
    public static void checkerSmall(String contrasena) throws SmallException {
        if (!contrasena.matches(".*[a-z].*")) {
            throw new SmallException();
        }
    }

    /**
     * Verifica que dos contraseñas sean iguales.
     *
     * @param contrasena1 Primera contraseña.
     * @param contrasena2 Segunda contraseña.
     * @throws EqualPasswordException Si las contraseñas no son iguales.
     */
    public static void checkerEqualPassword(String contrasena1, String contrasena2) throws EqualPasswordException {
        if (!contrasena1.equals(contrasena2)) {
            throw new EqualPasswordException();
        }
    }

    /**
     * Verifica que la contraseña contenga al menos un número.
     *
     * @param contrasena La contraseña a validar.
     * @throws NumberException Si no contiene números.
     */
    public static void checkerNumber(String contrasena) throws NumberException {
        if (!contrasena.matches(".*\\d.*")) {
            throw new NumberException();
        }
    }

    /**
     * Verifica que la contraseña contenga al menos un símbolo especial.
     *
     * @param contrasena La contraseña a validar.
     * @throws SymbolException Si no contiene símbolos especiales.
     */
    public static void checkerSymbol(String contrasena) throws SymbolException {
        if (!contrasena.matches(".*[^A-Za-z0-9].*")) {
            throw new SymbolException();
        }
    }


    /**
     * Verifica que una cadena sea un número válido.
     * <p>
     * <b>Nota:</b> Este método siempre lanza una excepción, ya que está diseñado
     * para validar cadenas, no enteros. Revisa su implementación.
     *
     * @param numero Número a validar.
     * @throws InputMismatchException Si la entrada no es un número válido.
     */
    public static void checkerNumber(int numero) throws InputMismatchException {
        throw new InputMismatchException("Revisa la implementación de este método.");
    }
    
    /** Constructor */
    public ExceptionCheker() {
		// TODO Auto-generated constructor stub
	}
}
