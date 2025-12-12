package es.iesjandula.reaktor.school_base_server.models;


import es.iesjandula.reaktor.school_base_server.models.ids.OcupaId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import lombok.Data;

/**
 * Representa la entidad Ocupa.
 * 
 * <p>Esta clase se utiliza para representar una ocupación.</p>
 */
@Data
@Entity
public class Ocupa
{
	/** Clave primaria compuesta de la ocupación. */
	@EmbeddedId
	private OcupaId ocupaId;
	
    /** Curso etapa grupo de la ocupación. */
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "curso", referencedColumnName = "curso", insertable = false, updatable = false),
        @JoinColumn(name = "etapa", referencedColumnName = "etapa", insertable = false, updatable = false),
        @JoinColumn(name = "grupo", referencedColumnName = "grupo", insertable = false, updatable = false)
    })
	private CursoEtapaGrupo cursoEtapaGrupo;
    
    /** Desdoble de la ocupación. */
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "cursoAcademico", referencedColumnName = "cursoAcademico", insertable = false, updatable = false),
        @JoinColumn(name = "nombre", referencedColumnName = "nombre", insertable = false, updatable = false)
    })
	private Desdoble desdoble;
}
