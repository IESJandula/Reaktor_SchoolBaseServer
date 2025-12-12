package es.iesjandula.reaktor.school_base_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

}
