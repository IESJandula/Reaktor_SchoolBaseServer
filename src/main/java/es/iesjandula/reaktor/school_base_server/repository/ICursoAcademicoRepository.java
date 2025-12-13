package es.iesjandula.reaktor.school_base_server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import es.iesjandula.reaktor.school_base_server.dtos.CursoAcademicoDto;
import es.iesjandula.reaktor.school_base_server.models.CursoAcademico;

@Repository
public interface ICursoAcademicoRepository extends JpaRepository<CursoAcademico, Long>
{
    /**
     * Obtiene un curso académico por su curso académico.
     * @param cursoAcademico El curso académico a buscar.
     * @return El curso académico encontrado.
     */
    @Query("SELECT c FROM CursoAcademico c WHERE c.cursoAcademico = :cursoAcademico")
    Optional<CursoAcademico> findByCursoAcademico(String cursoAcademico);

    /**
     * Obtiene todos los cursos académicos en formato DTO.
     * @return La lista de cursos académicos en formato DTO.
     */
    @Query("SELECT new es.iesjandula.reaktor.school_base_server.dtos.CursoAcademicoDto(c.cursoAcademico, c.seleccionado) " +
           "FROM CursoAcademico c " +
           "ORDER BY c.cursoAcademico DESC")
    List<CursoAcademicoDto> findAllDto();

    /**
     * Deselecciona todos los cursos académicos.
     */
    @Transactional
    @Modifying
    @Query("UPDATE CursoAcademico SET seleccionado = false")
    void deseleccionarTodosLosCursosAcademicos();
}
