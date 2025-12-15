package es.iesjandula.reaktor.school_base_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.iesjandula.reaktor.school_base_server.dtos.EspacioDesdobleDto;
import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioDesdoble;
import es.iesjandula.reaktor.school_base_server.models.ids.EspacioId;

/**
 * Repositorio para la entidad EspacioDesdoble.
 * 
 * <p>Este repositorio se utiliza para realizar operaciones de CRUD sobre la entidad EspacioDesdoble.</p>
 */
@Repository
public interface IEspacioDesdobleRepository extends JpaRepository<EspacioDesdoble, EspacioId>
{
    /**
     * Obtiene todos los espacios desdobles en formato DTO.
     * @param cursoAcademico El curso académico a obtener los espacios desdobles.
     * @return La lista de espacios desdobles en formato DTO.
     */
    @Query("SELECT new es.iesjandula.reaktor.school_base_server.dtos.EspacioDesdobleDto(e.espacioId.cursoAcademico, e.espacioId.nombre) " +
           "FROM EspacioDesdoble e " +
           "WHERE e.espacioId.cursoAcademico = :cursoAcademico")
    List<EspacioDesdobleDto> buscarPorCursoAcademico(String cursoAcademico);
}
