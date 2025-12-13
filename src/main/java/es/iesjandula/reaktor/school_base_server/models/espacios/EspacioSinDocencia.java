package es.iesjandula.reaktor.school_base_server.models.espacios;

import es.iesjandula.reaktor.school_base_server.models.CursoAcademico;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa la entidad EspacioSinDocencia.
 * 
 * <p>Esta clase se utiliza para representar un espacio sin docencia.</p>
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class EspacioSinDocencia extends Espacio
{
    /** Curso académico del espacio. */
    @ManyToOne
    @JoinColumn(name = "cursoAcademico", referencedColumnName = "cursoAcademico", insertable = false, updatable = false)
    private CursoAcademico cursoAcademico;
}
