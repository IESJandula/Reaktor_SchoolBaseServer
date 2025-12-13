package es.iesjandula.reaktor.school_base_server.models;


import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioDesdoble;
import es.iesjandula.reaktor.school_base_server.models.ids.OcupaEspacioDesdobleId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import lombok.Data;

/**
 * Representa la entidad Ocupa que relaciona un curso etapa grupo con un espacio desdoble.
 * 
 * <p>Esta clase se utiliza para representar una ocupación de un espacio desdoble.</p>
 */
@Data
@Entity
public class OcupaEspacioDesdoble
{
	/** Clave primaria compuesta */
	@EmbeddedId
	private OcupaEspacioDesdobleId ocupaEspacioDesdobleId;
	
    /** Curso etapa grupo */
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "curso", referencedColumnName = "curso", insertable = false, updatable = false),
        @JoinColumn(name = "etapa", referencedColumnName = "etapa", insertable = false, updatable = false),
        @JoinColumn(name = "grupo", referencedColumnName = "grupo", insertable = false, updatable = false)
    })
	private CursoEtapaGrupo cursoEtapaGrupo;
    
    /** Espacio desdoble */
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "cursoAcademico", referencedColumnName = "cursoAcademico", insertable = false, updatable = false),
        @JoinColumn(name = "nombre", referencedColumnName = "nombre", insertable = false, updatable = false)
    })
	private EspacioDesdoble espacioDesdoble;
}
