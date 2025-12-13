package es.iesjandula.reaktor.school_base_server.models;


import java.util.List;

import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioFijo;
import es.iesjandula.reaktor.school_base_server.models.ids.CursoEtapaGrupoId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;


/**
 * Representa la entidad CursoEtapaGrupo.
 * 
 * <p>Esta clase se utiliza para representar un curso etapa grupo.</p>
 */
@Data
@Entity
public class CursoEtapaGrupo
{
    /** Clave primaria compuesta del curso etapa grupo. */
	@EmbeddedId
    private CursoEtapaGrupoId cursoEtapaGrupoId;
	
	/** Lista de ocupas de espacio desdoble del curso etapa grupo. */
	@OneToMany(mappedBy = "cursoEtapaGrupo")
	private List<OcupaEspacioDesdoble> ocupasEspacioDesdoble;
	
	/** Fijo del curso etapa grupo. */
	@OneToOne(mappedBy = "cursoEtapaGrupo")
	private EspacioFijo espacioFijo;
}
