package co.edu.unbosque.proyectoia.entity;

import java.time.LocalDate;
import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa un archivo almacenado en el sistema.
 * Contiene información como el identificador único, nombre, tipo, ruta URL y fecha de subida.
 */
@Entity
@Table(name = "archivo")
public class Archivo {

    /** Identificador único del archivo. */
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    /** Nombre del archivo. */
    private String nombre;

    /** Tipo o extensión del archivo (ej: PDF, TXT, PNG). */
    private String tipo;

    /** Ruta URL donde se encuentra almacenado el archivo. */
    private String urlRuta;

    /** Fecha en la que se subió el archivo al sistema. */
    private LocalDate fechaSubido;

    /**
     * Constructor por defecto de la clase {@code Archivo}.
     */
    public Archivo() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor parametrizado para inicializar un objeto {@code Archivo}.
     *
     * @param id Identificador único del archivo.
     * @param nombre Nombre del archivo.
     * @param tipo Tipo o extensión del archivo.
     * @param urlRuta Ruta URL del archivo.
     * @param fechaSubido Fecha en la que se subió el archivo.
     */
    public Archivo(Long id, String nombre, String tipo, String urlRuta, LocalDate fechaSubido) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.urlRuta = urlRuta;
        this.fechaSubido = fechaSubido;
    }

    /**
     * Obtiene el identificador único del archivo.
     *
     * @return El identificador único.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del archivo.
     *
     * @param id Identificador único a asignar.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del archivo.
     *
     * @return El nombre del archivo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del archivo.
     *
     * @param nombre Nombre del archivo a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el tipo o extensión del archivo.
     *
     * @return El tipo del archivo.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo o extensión del archivo.
     *
     * @param tipo Tipo del archivo a asignar.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la ruta URL donde se encuentra almacenado el archivo.
     *
     * @return La ruta URL del archivo.
     */
    public String getUrlRuta() {
        return urlRuta;
    }

    /**
     * Establece la ruta URL donde se encuentra almacenado el archivo.
     *
     * @param urlRuta Ruta URL del archivo a asignar.
     */
    public void setUrlRuta(String urlRuta) {
        this.urlRuta = urlRuta;
    }

    /**
     * Obtiene la fecha en la que se subió el archivo al sistema.
     *
     * @return La fecha de subida del archivo.
     */
    public LocalDate getFechaSubido() {
        return fechaSubido;
    }

    /**
     * Establece la fecha en la que se subió el archivo al sistema.
     *
     * @param fechaSubido Fecha de subida del archivo a asignar.
     */
    public void setFechaSubido(LocalDate fechaSubido) {
        this.fechaSubido = fechaSubido;
    }

    /**
     * Genera un código hash para el objeto basado en sus atributos.
     *
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(fechaSubido, id, nombre, tipo, urlRuta);
    }

    /**
     * Compara este objeto con otro para determinar si son iguales.
     * Dos objetos son iguales si sus atributos {@code id}, {@code nombre}, {@code tipo}, {@code urlRuta} y {@code fechaSubido} son iguales.
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
        Archivo other = (Archivo) obj;
        return Objects.equals(fechaSubido, other.fechaSubido) && Objects.equals(id, other.id)
                && Objects.equals(nombre, other.nombre) && Objects.equals(tipo, other.tipo)
                && Objects.equals(urlRuta, other.urlRuta);
    }

    /**
     * Devuelve una representación en cadena del objeto.
     *
     * @return Cadena que representa el objeto, incluyendo sus atributos.
     */
    @Override
    public String toString() {
        return "Archivo [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", urlRuta=" + urlRuta + ", fechaSubido="
                + fechaSubido + "]";
    }
}
