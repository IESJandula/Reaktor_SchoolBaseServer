package es.iesjandula.reaktor.school_base_server.models.ids;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

/**
 * Representa la clave primaria compuesta de la entidad Ocupa.
 * 
 * <p>Esta clase se utiliza para identificar de manera única los cursos que ocupan el espacio 
 * de desdoble.</p>
 * 
 * <p>Al implementar Serializable, puede ser utilizada como clave embebida en JPA.</p>
 */
@Data
@Embeddable
public class OcupaId implements Serializable
{
    /**
     * Serial version UID para garantizar la compatibilidad durante la serialización.
     */

	private static final long serialVersionUID = -669397589408569809L;

    /**
     * Clave primaria compuesta de la entidad CursoEtapaGrupo.
     */
    private CursoEtapaGrupoId cursoEtapaGrupoId;

    /**
     * Clave primaria compuesta de la entidad Espacio.
     */
    private EspacioId espacioId;
}