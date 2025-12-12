package es.iesjandula.reaktor.school_base_server.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa la entidad Fijo.
 * 
 * <p>Esta clase se utiliza para representar un fijo.</p>
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Fijo extends Espacio
{
	/** Curso etapa grupo del fijo. */
	@OneToOne
	private CursoEtapaGrupo cursoEtapaGrupo;
}
