package es.iesjandula.reaktor.school_base_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioSinDocencia;
import es.iesjandula.reaktor.school_base_server.models.ids.EspacioId;

/**
 * Repositorio para la entidad EspacioSinDocencia.
 * 
 * <p>Este repositorio se utiliza para realizar operaciones de CRUD sobre la entidad EspacioSinDocencia.</p>
 */
@Repository
public interface ISinDocenciaRepository extends JpaRepository<EspacioSinDocencia, EspacioId>
{

}
