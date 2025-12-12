package es.iesjandula.reaktor.school_base_server.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa la entidad Desdoble.
 * 
 * <p>Esta clase se utiliza para representar un desdoble.</p>
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Desdoble extends Espacio
{
	/** Lista de ocupas del desdoble. */
	@OneToMany(mappedBy = "desdoble")
	private List<Ocupa> ocupas;	
}
