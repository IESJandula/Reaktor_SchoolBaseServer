package es.iesjandula.reaktor.school_base_server.models.espacios;

import es.iesjandula.reaktor.school_base_server.models.CursoAcademico;
import es.iesjandula.reaktor.school_base_server.models.CursoEtapaGrupo;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa la entidad EspacioFijo.
 * 
 * <p>Esta clase se utiliza para representar un espacio fijo.</p>
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class EspacioFijo extends Espacio
{
	/** Curso académico del espacio. */
	@ManyToOne
	@JoinColumn(name = "cursoAcademico", referencedColumnName = "cursoAcademico", insertable = false, updatable = false)
	private CursoAcademico cursoAcademico;

	/** Curso etapa grupo del fijo. */
	@OneToOne
	@JoinColumns({
	    @JoinColumn(name = "curso", referencedColumnName = "curso"),
	    @JoinColumn(name = "etapa", referencedColumnName = "etapa"),
	    @JoinColumn(name = "grupo", referencedColumnName = "grupo")
	})
	private CursoEtapaGrupo cursoEtapaGrupo;
}
