package es.iesjandula.reaktor.school_base_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.iesjandula.reaktor.school_base_server.dtos.EspacioSinDocenciaDto;
import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioSinDocencia;
import es.iesjandula.reaktor.school_base_server.models.ids.EspacioId;

/**
 * Repositorio para la entidad EspacioSinDocencia.
 * 
 * <p>Este repositorio se utiliza para realizar operaciones de CRUD sobre la entidad EspacioSinDocencia.</p>
 */
@Repository
public interface IEspacioSinDocenciaRepository extends JpaRepository<EspacioSinDocencia, EspacioId>
{
    /**
     * Obtiene todos los espacios sin docencia en formato DTO.
     * @return La lista de espacios sin docencia en formato DTO.
     */
    @Query("SELECT new es.iesjandula.reaktor.school_base_server.dtos.EspacioSinDocenciaDto(e.espacioId.cursoAcademico, e.espacioId.nombre) " +
           "FROM EspacioSinDocencia e")
    List<EspacioSinDocenciaDto> findAllDto();
}
