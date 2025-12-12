package es.iesjandula.reaktor.school_base_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesjandula.reaktor.school_base_server.models.ids.OcupaId;
import es.iesjandula.reaktor.school_base_server.models.Ocupa;

/**
 * Repositorio para la entidad Ocupa.
 * 
 * <p>Este repositorio se utiliza para realizar operaciones de CRUD sobre la entidad Ocupa.</p>
 */
@Repository
public interface IOcupaRepository extends JpaRepository<Ocupa, OcupaId>
{

}
