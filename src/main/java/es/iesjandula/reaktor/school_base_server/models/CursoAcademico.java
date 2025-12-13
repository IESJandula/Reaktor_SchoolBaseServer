package es.iesjandula.reaktor.school_base_server.models;

import java.util.List;

import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioDesdoble;
import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioFijo;
import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioSinDocencia;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la entidad CursoAcademico.
 * 
 * <p>Esta clase se utiliza para representar un curso académico.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CursoAcademico
{
	/** Curso académico */
	@Id
	@Column(length = 30)
	private String cursoAcademico ; 
	
    /** Seleccionado */
	@Column
	private Boolean seleccionado ;

	/** Lista de espacios sin docencia del curso académico. */
	@OneToMany(mappedBy = "cursoAcademico")
	private List<EspacioSinDocencia> espaciosSinDocencia;

	/** Lista de espacios fijos del curso académico. */
	@OneToMany(mappedBy = "cursoAcademico")
	private List<EspacioFijo> espaciosFijos;

	/** Lista de espacios desdobles del curso académico. */
	@OneToMany(mappedBy = "cursoAcademico")
	private List<EspacioDesdoble> espaciosDesdobles;
}
