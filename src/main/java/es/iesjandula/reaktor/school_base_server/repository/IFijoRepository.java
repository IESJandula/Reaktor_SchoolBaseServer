package es.iesjandula.reaktor.school_base_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesjandula.reaktor.school_base_server.models.Fijo;
import es.iesjandula.reaktor.school_base_server.models.ids.EspacioId;

/**
 * Repositorio para la entidad Fijo.
 * 
 * <p>Este repositorio se utiliza para realizar operaciones de CRUD sobre la entidad Fijo.</p>
 */
@Repository
public interface IFijoRepository extends JpaRepository<Fijo, EspacioId>
{

}
