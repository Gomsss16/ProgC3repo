package co.edu.unbosque.proyectoia.dto;

import java.util.Objects;

/**
 * Clase DTO (Data Transfer Object) que encapsula los datos necesarios para el análisis de archivos mediante IA.
 * Contiene el texto, el tipo de archivo y la imagen codificada en Base64.
 */
public class AnalizadorIADTO {

    /** Texto asociado al archivo o análisis. */
    private String texto;

    /** Tipo de archivo (ej: PDF, TXT, PNG, JPG). */
    private String tipoArchivo;

    /** Imagen codificada en Base64 para su procesamiento. */
    private String imagenBase64;

    /**
     * Constructor por defecto de la clase {@code AnalizadorIADTO}.
     */
    public AnalizadorIADTO() {}

    /**
     * Constructor parametrizado para inicializar un objeto {@code AnalizadorIADTO}.
     *
     * @param texto Texto asociado al archivo o análisis.
     * @param tipoArchivo Tipo de archivo procesado.
     * @param imagenBase64 Imagen codificada en Base64.
     */
    public AnalizadorIADTO(String texto, String tipoArchivo, String imagenBase64) {
        this.texto = texto;
        this.tipoArchivo = tipoArchivo;
        this.imagenBase64 = imagenBase64;
    }

    /**
     * Obtiene el texto asociado al archivo o análisis.
     *
     * @return El texto del análisis.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Establece el texto asociado al archivo o análisis.
     *
     * @param texto Texto a asignar.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Obtiene el tipo de archivo procesado.
     *
     * @return El tipo de archivo.
     */
    public String getTipoArchivo() {
        return tipoArchivo;
    }

    /**
     * Establece el tipo de archivo procesado.
     *
     * @param tipoArchivo Tipo de archivo a asignar.
     */
    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    /**
     * Obtiene la imagen codificada en Base64.
     *
     * @return La imagen en formato Base64.
     */
    public String getImagenBase64() {
        return imagenBase64;
    }

    /**
     * Establece la imagen codificada en Base64.
     *
     * @param imagenBase64 Imagen en formato Base64 a asignar.
     */
    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    /**
     * Genera un código hash para el objeto basado en sus atributos.
     *
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(imagenBase64, texto, tipoArchivo);
    }

    /**
     * Compara este objeto con otro para determinar si son iguales.
     * Dos objetos son iguales si sus atributos {@code texto}, {@code tipoArchivo} y {@code imagenBase64} son iguales.
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
        AnalizadorIADTO other = (AnalizadorIADTO) obj;
        return Objects.equals(imagenBase64, other.imagenBase64) && Objects.equals(texto, other.texto)
                && Objects.equals(tipoArchivo, other.tipoArchivo);
    }

    /**
     * Devuelve una representación en cadena del objeto.
     *
     * @return Cadena que representa el objeto, incluyendo sus atributos.
     */
    @Override
    public String toString() {
        return "AnalizadorIADTO [texto=" + texto + ", tipoArchivo=" + tipoArchivo + ", imagenBase64=" + imagenBase64 + "]";
    }
}
