package es.iesjandula.reaktor.school_base_server.models;

import es.iesjandula.reaktor.school_base_server.models.ids.EspacioId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;


/**
 * Representa la entidad Espacio.
 * 
 * <p>Esta clase se utiliza para representar un espacio.</p>
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Espacio
{
	/** Clave primaria compuesta del espacio. */
	@EmbeddedId
	private EspacioId espacioId;
}
