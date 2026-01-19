package es.iesjandula.reaktor.school_base_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.iesjandula.reaktor.school_base_server.dtos.EspacioFijoDto;
import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioFijo;
import es.iesjandula.reaktor.school_base_server.models.ids.EspacioId;

/**
 * Repositorio para la entidad EspacioFijo.
 * 
 * <p>
 * Este repositorio se utiliza para realizar operaciones de CRUD sobre la
 * entidad EspacioFijo.
 * </p>
 */
@Repository
public interface IEspacioFijoRepository extends JpaRepository<EspacioFijo, EspacioId>
{
	/**
	 * Obtiene todos los espacios fijos en formato DTO.
	 * 
	 * @param cursoAcademico El curso académico a obtener los espacios fijos.
	 * @return La lista de espacios fijos en formato DTO.
	 */
	@Query("""
			    SELECT new es.iesjandula.reaktor.school_base_server.dtos.EspacioFijoDto(
			        e.espacioId.cursoAcademico,
			        e.espacioId.nombre,
			        e.cursoEtapaGrupo.cursoEtapaGrupoId.curso,
			        e.cursoEtapaGrupo.cursoEtapaGrupoId.etapa,
			        e.cursoEtapaGrupo.cursoEtapaGrupoId.grupo
			    )
			    FROM EspacioFijo e
			    WHERE e.espacioId.cursoAcademico = :cursoAcademico
			""")
	List<EspacioFijoDto> buscarPorCursoAcademico(String cursoAcademico);
}
