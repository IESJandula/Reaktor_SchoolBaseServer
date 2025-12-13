package es.iesjandula.reaktor.school_base_server.dtos;

import lombok.Data;

/**
 * Representa el DTO de la entidad Espacio.
 * 
 * <p>Esta clase se utiliza para representar un espacio.</p>
 */
@Data
public abstract class EspacioDto
{
    /**
     * Curso academico del espacio.
     */
    private String cursoAcademico;

    /**
     * Nombre del espacio.
     */
    private String nombre;
}
