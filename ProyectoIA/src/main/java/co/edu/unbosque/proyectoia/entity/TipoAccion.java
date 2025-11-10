package co.edu.unbosque.proyectoia.entity;

/**
 * Enumeración que define los tipos de acciones que pueden ser registradas
 * en el historial de actividades de los usuarios del sistema.
 * 
 * Cada constante representa una acción específica que puede ser auditada
 * para fines de seguridad, análisis de uso y cumplimiento normativo.
 * 
 * @author Equipo ProyectoIA
 * @version 1.0
 */
public enum TipoAccion {
    /**
     * Acción de inicio de sesión exitoso en el sistema.
     */
    LOGIN,
    
    /**
     * Acción de registro de un nuevo usuario en el sistema.
     */
    REGISTRO,
    
    /**
     * Acción de análisis de un texto mediante IA.
     */
    ANALIZAR_TEXTO,
    
    /**
     * Acción de análisis de una imagen mediante IA.
     */
    ANALIZAR_IMAGEN,
    
    /**
     * Acción de análisis de un video mediante IA.
     */
    ANALIZAR_VIDEO,
    
    /**
     * Acción de análisis de un archivo genérico mediante IA.
     */
    ANALIZAR_ARCHIVO
}
