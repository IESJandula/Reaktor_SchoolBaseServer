package es.iesjandula.reaktor.school_base_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesjandula.reaktor.school_base_server.models.ids.OcupaEspacioDesdobleId;
import es.iesjandula.reaktor.school_base_server.models.OcupaEspacioDesdoble;

/**
 * Repositorio para la entidad OcupaEspacioDesdoble.
 * 
 * <p>Este repositorio se utiliza para realizar operaciones de CRUD sobre la entidad OcupaEspacioDesdoble.</p>
 */
@Repository
public interface IOcupaEspacioDesdobleRepository extends JpaRepository<OcupaEspacioDesdoble, OcupaEspacioDesdobleId>
{

}
