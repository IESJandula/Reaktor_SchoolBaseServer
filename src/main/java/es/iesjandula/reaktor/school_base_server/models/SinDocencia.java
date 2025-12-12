package es.iesjandula.reaktor.school_base_server.models;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa la entidad SinDocencia.
 * 
 * <p>Esta clase se utiliza para representar un espacio sin docencia.</p>
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class SinDocencia extends Espacio
{
	
}
