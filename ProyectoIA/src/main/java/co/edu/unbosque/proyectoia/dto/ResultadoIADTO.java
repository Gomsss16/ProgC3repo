package co.edu.unbosque.proyectoia.dto;

import java.util.Map;
import java.util.Objects;

/**
 * Clase DTO (Data Transfer Object) que encapsula los resultados de un análisis de IA.
 * Contiene los porcentajes de análisis, el texto analizado y el promedio general del resultado.
 */
public class ResultadoIADTO {

    /** Mapa de porcentajes resultantes del análisis, donde la clave es la categoría y el valor es el porcentaje. */
    private Map<String, Double> porcentajes;

    /** Texto que fue analizado por la IA. */
    private String textoAnalizado;

    /** Promedio general del análisis realizado. */
    private Double promedioGeneral;

    /**
     * Constructor por defecto de la clase {@code ResultadoIADTO}.
     */
    public ResultadoIADTO() {}

    /**
     * Constructor parametrizado para inicializar un objeto {@code ResultadoIADTO}.
     *
     * @param porcentajes Mapa de porcentajes resultantes del análisis.
     * @param textoAnalizado Texto que fue analizado.
     * @param promedioGeneral Promedio general del análisis.
     */
    public ResultadoIADTO(Map<String, Double> porcentajes, String textoAnalizado, Double promedioGeneral) {
        this.porcentajes = porcentajes;
        this.textoAnalizado = textoAnalizado;
        this.promedioGeneral = promedioGeneral;
    }

    /**
     * Obtiene el mapa de porcentajes resultantes del análisis.
     *
     * @return Mapa de porcentajes.
     */
    public Map<String, Double> getPorcentajes() {
        return porcentajes;
    }

    /**
     * Establece el mapa de porcentajes resultantes del análisis.
     *
     * @param porcentajes Mapa de porcentajes a asignar.
     */
    public void setPorcentajes(Map<String, Double> porcentajes) {
        this.porcentajes = porcentajes;
    }

    /**
     * Obtiene el texto que fue analizado.
     *
     * @return Texto analizado.
     */
    public String getTextoAnalizado() {
        return textoAnalizado;
    }

    /**
     * Establece el texto que fue analizado.
     *
     * @param textoAnalizado Texto analizado a asignar.
     */
    public void setTextoAnalizado(String textoAnalizado) {
        this.textoAnalizado = textoAnalizado;
    }

    /**
     * Obtiene el promedio general del análisis.
     *
     * @return Promedio general del análisis.
     */
    public Double getPromedioGeneral() {
        return promedioGeneral;
    }

    /**
     * Establece el promedio general del análisis.
     *
     * @param promedioGeneral Promedio general a asignar.
     */
    public void setPromedioGeneral(Double promedioGeneral) {
        this.promedioGeneral = promedioGeneral;
    }

    /**
     * Genera un código hash para el objeto basado en sus atributos.
     *
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(porcentajes, promedioGeneral, textoAnalizado);
    }

    /**
     * Compara este objeto con otro para determinar si son iguales.
     * Dos objetos son iguales si sus atributos {@code porcentajes}, {@code textoAnalizado} y {@code promedioGeneral} son iguales.
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
        ResultadoIADTO other = (ResultadoIADTO) obj;
        return Objects.equals(porcentajes, other.porcentajes) &&
               Objects.equals(promedioGeneral, other.promedioGeneral) &&
               Objects.equals(textoAnalizado, other.textoAnalizado);
    }

    /**
     * Devuelve una representación en cadena del objeto.
     *
     * @return Cadena que representa el objeto, incluyendo sus atributos.
     */
    @Override
    public String toString() {
        return "ResultadoIADTO [porcentajes=" + porcentajes +
               ", textoAnalizado=" + textoAnalizado +
               ", promedioGeneral=" + promedioGeneral + "]";
    }

	public void setMensaje(String string) {
		// TODO Auto-generated method stub
		
	}

	
}
