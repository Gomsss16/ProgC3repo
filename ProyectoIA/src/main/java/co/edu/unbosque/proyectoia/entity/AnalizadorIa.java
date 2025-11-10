package co.edu.unbosque.proyectoia.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa un analizador de IA en la base de datos.
 * Contiene información como el nombre, la URL de la API y la clave de acceso.
 */
@Entity
@Table(name = "analizadoria")
public class AnalizadorIa {

    /** Identificador único del analizador de IA. */
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    /** Nombre descriptivo del analizador de IA. */
    private String nombre;

    /** URL de la API del analizador de IA. */
    private String apiUrl;

    /** Clave de acceso para autenticación en la API. */
    private String apiKey;

    /**
     * Constructor por defecto de la clase {@code AnalizadorIa}.
     */
    public AnalizadorIa() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor parametrizado para inicializar un objeto {@code AnalizadorIa}.
     *
     * @param id Identificador único del analizador.
     * @param nombre Nombre descriptivo del analizador.
     * @param apiUrl URL de la API del analizador.
     * @param apiKey Clave de acceso para la API.
     */
    public AnalizadorIa(Long id, String nombre, String apiUrl, String apiKey) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    /**
     * Obtiene el identificador único del analizador.
     *
     * @return El identificador único.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del analizador.
     *
     * @param id Identificador único a asignar.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre descriptivo del analizador.
     *
     * @return El nombre del analizador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre descriptivo del analizador.
     *
     * @param nombre Nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la URL de la API del analizador.
     *
     * @return La URL de la API.
     */
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * Establece la URL de la API del analizador.
     *
     * @param apiUrl URL de la API a asignar.
     */
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    /**
     * Obtiene la clave de acceso para la API.
     *
     * @return La clave de acceso.
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Establece la clave de acceso para la API.
     *
     * @param apiKey Clave de acceso a asignar.
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Genera un código hash para el objeto basado en sus atributos.
     *
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(apiKey, apiUrl, id, nombre);
    }

    /**
     * Compara este objeto con otro para determinar si son iguales.
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
        AnalizadorIa other = (AnalizadorIa) obj;
        return Objects.equals(apiKey, other.apiKey) && Objects.equals(apiUrl, other.apiUrl)
                && Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
    }

    /**
     * Devuelve una representación en cadena del objeto.
     *
     * @return Cadena que representa el objeto.
     */
    @Override
    public String toString() {
        return "AnalizadorIa [id=" + id + ", nombre=" + nombre + ", apiUrl=" + apiUrl + ", apiKey=" + apiKey + "]";
    }
}
