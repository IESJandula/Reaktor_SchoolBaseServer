package es.iesjandula.reaktor.school_base_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesjandula.reaktor.school_base_server.models.Desdoble;
import es.iesjandula.reaktor.school_base_server.models.ids.EspacioId;

/**
 * Repositorio para la entidad Desdoble.
 * 
 * <p>Este repositorio se utiliza para realizar operaciones de CRUD sobre la entidad Desdoble.</p>
 */
@Repository
public interface IDesdobleRepository extends JpaRepository<Desdoble, EspacioId>
{

}
