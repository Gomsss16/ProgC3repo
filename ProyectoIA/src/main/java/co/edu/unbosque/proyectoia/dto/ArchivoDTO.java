package co.edu.unbosque.proyectoia.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Clase DTO (Data Transfer Object) que representa la información de un archivo subido al sistema.
 * Contiene detalles como el nombre, tipo, ruta URL y fecha de subida del archivo.
 */
public class ArchivoDTO {

    /** Nombre del archivo. */
    private String nombre;

    /** Tipo o extensión del archivo (ej: PDF, TXT, PNG). */
    private String tipo;

    /** Ruta URL donde se encuentra almacenado el archivo. */
    private String urlRuta;

    /** Fecha en la que se subió el archivo al sistema. */
    private LocalDate fechaSubido;

    /**
     * Constructor por defecto de la clase {@code ArchivoDTO}.
     */
    public ArchivoDTO() {}

    /**
     * Constructor parametrizado para inicializar un objeto {@code ArchivoDTO}.
     *
     * @param nombre Nombre del archivo.
     * @param tipo Tipo o extensión del archivo.
     * @param urlRuta Ruta URL del archivo.
     * @param fechaSubido Fecha en la que se subió el archivo.
     */
    public ArchivoDTO(String nombre, String tipo, String urlRuta, LocalDate fechaSubido) {
        super();
        this.nombre = nombre;
        this.tipo = tipo;
        this.urlRuta = urlRuta;
        this.fechaSubido = fechaSubido;
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
        return Objects.hash(fechaSubido, nombre, tipo, urlRuta);
    }

    /**
     * Compara este objeto con otro para determinar si son iguales.
     * Dos objetos son iguales si sus atributos {@code nombre}, {@code tipo}, {@code urlRuta} y {@code fechaSubido} son iguales.
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
        ArchivoDTO other = (ArchivoDTO) obj;
        return Objects.equals(fechaSubido, other.fechaSubido) && Objects.equals(nombre, other.nombre)
                && Objects.equals(tipo, other.tipo) && Objects.equals(urlRuta, other.urlRuta);
    }

    /**
     * Devuelve una representación en cadena del objeto.
     *
     * @return Cadena que representa el objeto, incluyendo sus atributos.
     */
    @Override
    public String toString() {
        return "ArchivoDTO [nombre=" + nombre + ", tipo=" + tipo + ", urlRuta=" + urlRuta + ", fechaSubido="
                + fechaSubido + "]";
    }
}
