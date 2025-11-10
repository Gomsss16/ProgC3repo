package co.edu.unbosque.proyectoia.entity;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa el resultado de un análisis de IA realizado sobre un archivo.
 * Contiene información como el identificador único, el identificador del archivo analizado,
 * el identificador del analizador de IA utilizado y el porcentaje resultante del análisis.
 */
@Entity
@Table(name = "resultadoia")
public class ResultadoIa {

    /** Identificador único del resultado del análisis. */
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    /** Identificador del archivo analizado. */
    private Long idArchivo;

    /** Identificador del analizador de IA utilizado. */
    private Long idAnalizadorIa;

    /** Porcentaje resultante del análisis. */
    private Double porcentaje;

    /**
     * Constructor por defecto de la clase {@code ResultadoIa}.
     */
    public ResultadoIa() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor parametrizado para inicializar un objeto {@code ResultadoIa}.
     *
     * @param id Identificador único del resultado.
     * @param idArchivo Identificador del archivo analizado.
     * @param idAnalizadorIa Identificador del analizador de IA utilizado.
     * @param porcentaje Porcentaje resultante del análisis.
     */
    public ResultadoIa(Long id, Long idArchivo, Long idAnalizadorIa, Double porcentaje) {
        super();
        this.id = id;
        this.idArchivo = idArchivo;
        this.idAnalizadorIa = idAnalizadorIa;
        this.porcentaje = porcentaje;
    }

    /**
     * Obtiene el identificador único del resultado del análisis.
     *
     * @return El identificador único.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del resultado del análisis.
     *
     * @param id Identificador único a asignar.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el identificador del archivo analizado.
     *
     * @return El identificador del archivo.
     */
    public Long getIdArchivo() {
        return idArchivo;
    }

    /**
     * Establece el identificador del archivo analizado.
     *
     * @param idArchivo Identificador del archivo a asignar.
     */
    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

    /**
     * Obtiene el identificador del analizador de IA utilizado.
     *
     * @return El identificador del analizador de IA.
     */
    public Long getIdAnalizadorIa() {
        return idAnalizadorIa;
    }

    /**
     * Establece el identificador del analizador de IA utilizado.
     *
     * @param idAnalizadorIa Identificador del analizador de IA a asignar.
     */
    public void setIdAnalizadorIa(Long idAnalizadorIa) {
        this.idAnalizadorIa = idAnalizadorIa;
    }

    /**
     * Obtiene el porcentaje resultante del análisis.
     *
     * @return El porcentaje del análisis.
     */
    public Double getPorcentaje() {
        return porcentaje;
    }

    /**
     * Establece el porcentaje resultante del análisis.
     *
     * @param porcentaje Porcentaje del análisis a asignar.
     */
    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     * Genera un código hash para el objeto basado en sus atributos.
     *
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, idAnalizadorIa, idArchivo, porcentaje);
    }

    /**
     * Compara este objeto con otro para determinar si son iguales.
     * Dos objetos son iguales si sus atributos {@code id}, {@code idArchivo}, {@code idAnalizadorIa} y {@code porcentaje} son iguales.
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
        ResultadoIa other = (ResultadoIa) obj;
        return Objects.equals(id, other.id) && Objects.equals(idAnalizadorIa, other.idAnalizadorIa)
                && Objects.equals(idArchivo, other.idArchivo) && Objects.equals(porcentaje, other.porcentaje);
    }

    /**
     * Devuelve una representación en cadena del objeto.
     *
     * @return Cadena que representa el objeto, incluyendo sus atributos.
     */
    @Override
    public String toString() {
        return "ResultadoIa [id=" + id + ", idArchivo=" + idArchivo + ", idAnalizadorIa=" + idAnalizadorIa
                + ", porcentaje=" + porcentaje + "]";
    }
}
