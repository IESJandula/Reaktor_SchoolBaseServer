package es.iesjandula.reaktor.school_base_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.iesjandula.reaktor.school_base_server.dtos.CursoEtapaGrupoDto;
import es.iesjandula.reaktor.school_base_server.models.CursoEtapaGrupo;
import es.iesjandula.reaktor.school_base_server.models.ids.CursoEtapaGrupoId;

/**
 * Repositorio para la entidad CursoEtapaGrupo.
 * 
 * <p>Este repositorio se utiliza para realizar operaciones de CRUD sobre la entidad CursoEtapaGrupo.</p>
 */
@Repository
public interface ICursoEtapaGrupoRepository extends JpaRepository<CursoEtapaGrupo, CursoEtapaGrupoId>
{
    /**
     * Obtiene todos los cursos, etapas y grupos en formato DTO.
     * @return La lista de cursos, etapas y grupos en formato DTO.
     */
    @Query("SELECT new es.iesjandula.reaktor.school_base_server.dtos.CursoEtapaGrupoDto(c.cursoEtapaGrupoId.curso, c.cursoEtapaGrupoId.etapa, c.cursoEtapaGrupoId.grupo) " + 
           "FROM CursoEtapaGrupo c " +
           "ORDER BY c.cursoEtapaGrupoId.curso ASC, c.cursoEtapaGrupoId.etapa ASC, c.cursoEtapaGrupoId.grupo ASC")
    List<CursoEtapaGrupoDto> findAllDto();
}
