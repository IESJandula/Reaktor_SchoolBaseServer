package es.iesjandula.reaktor.school_base_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioFijo;
import es.iesjandula.reaktor.school_base_server.models.ids.EspacioId;

/**
 * Repositorio para la entidad EspacioFijo.
 * 
 * <p>Este repositorio se utiliza para realizar operaciones de CRUD sobre la entidad EspacioFijo.</p>
 */
@Repository
public interface IFijoRepository extends JpaRepository<EspacioFijo, EspacioId>
{

}
