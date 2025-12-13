package es.iesjandula.reaktor.school_base_server.models.espacios;

import java.util.List;

import es.iesjandula.reaktor.school_base_server.models.CursoAcademico;
import es.iesjandula.reaktor.school_base_server.models.OcupaEspacioDesdoble;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa la entidad EspacioDesdoble.
 * 
 * <p>Esta clase se utiliza para representar un espacio desdoble.</p>
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class EspacioDesdoble extends Espacio
{
	/** Curso académico del espacio. */
	@ManyToOne
	@JoinColumn(name = "cursoAcademico", referencedColumnName = "cursoAcademico", insertable = false, updatable = false)
	private CursoAcademico cursoAcademico;

	/** Lista de ocupas de espacio desdoble */
	@OneToMany(mappedBy = "espacioDesdoble")
	private List<OcupaEspacioDesdoble> ocupaEspacioDesdoble;	
}
