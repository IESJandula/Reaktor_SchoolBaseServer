package es.iesjandula.reaktor.school_base_server.models.ids;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * Representa la clave primaria compuesta de la entidad Espacio.
 * 
 * <p>Esta clase se utiliza para identificar de manera única el espacio 
 * mediante el curso académico y el nombre.</p>
 * 
 * <p>Al implementar Serializable, puede ser utilizada como clave embebida en JPA.</p>
 */
@Data
@Embeddable
public class EspacioId implements Serializable
{
    /**
     * Serial version UID para garantizar la compatibilidad durante la serialización.
     */

	private static final long serialVersionUID = 8937202302598004694L;
	
	
    /**
     * Curso academico del espacio de desdoble.
     * Forma parte de la clave primaria compuesta.
     */
	@Column(length = 30)
	private String cursoAcademico;
	
	/**
     * Nombre del espacio de desdoble.
     * Forma parte de la clave primaria compuesta.
     */
	@Column(length = 100)
    private String nombre;
}
