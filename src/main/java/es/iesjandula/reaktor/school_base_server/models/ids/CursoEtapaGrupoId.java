package es.iesjandula.reaktor.school_base_server.models.ids;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * Representa la clave primaria compuesta de la entidad CursoEtapaGrupo.
 * 
 * <p>Esta clase se utiliza para identificar de manera única un curso mediante
 * el curso, la etapa y el grupo al que pertenecen.</p>
 * 
 * <p>Al implementar Serializable, puede ser utilizada como clave embebida en JPA.</p>
 */
@Data
@Embeddable
public class CursoEtapaGrupoId implements Serializable
{
    /**
     * Serial version UID para garantizar la compatibilidad durante la serialización.
     */
	private static final long serialVersionUID = -4095092860551435646L;
	
	/**
     * Número de curso.
     * Forma parte de la clave primaria compuesta.
     */
	@Column
	private Integer curso;
	
	/**
     * Etapa del curso.
     * Forma parte de la clave primaria compuesta.
     */
	@Column(length = 30)
    private String etapa;
    
    /**
     * Grupo al que pertenece el curso
     * Forma parte de la clave primaria compuesta.
     */
	@Column(length = 2)
    private String grupo;

}
