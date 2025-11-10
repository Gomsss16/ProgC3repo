package co.edu.unbosque.proyectoia.dto;

import java.util.Objects;

/**
 * Clase DTO (Data Transfer Object) que encapsula la respuesta de autenticación.
 * Contiene el token generado tras un proceso de autenticación exitoso.
 */
public class AuthResponse {

    /** Token de autenticación generado para el usuario. */
    private String token;

    /**
     * Constructor parametrizado para inicializar un objeto {@code AuthResponse}.
     *
     * @param token Token de autenticación a asignar.
     */
    public AuthResponse(String token) {
        super();
        this.token = token;
    }

    /**
     * Obtiene el token de autenticación.
     *
     * @return El token de autenticación.
     */
    public String getToken() {
        return token;
    }

    /**
     * Establece el token de autenticación.
     *
     * @param token Token de autenticación a asignar.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Devuelve una representación en cadena del objeto.
     *
     * @return Cadena que representa el objeto, incluyendo el token.
     */
    @Override
    public String toString() {
        return "TokenResponse [token=" + token + "]";
    }

    /**
     * Genera un código hash para el objeto basado en su token.
     *
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    /**
     * Compara este objeto con otro para determinar si son iguales.
     * Dos objetos son iguales si sus tokens son iguales.
     *
     * @param obj Objeto a comparar.
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AuthResponse other = (AuthResponse) obj;
        return Objects.equals(token, other.token);
    }
}
