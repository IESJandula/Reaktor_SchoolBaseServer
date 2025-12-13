package es.iesjandula.reaktor.school_base_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import es.iesjandula.reaktor.school_base_server.models.CursoAcademico;

@Repository
public interface ICursoAcademicoRepository extends JpaRepository<CursoAcademico, String>
{
    /**
     * Deselecciona todos los cursos académicos.
     */
    @Transactional
    @Modifying
    @Query("UPDATE CursoAcademico SET seleccionado = false")
    void deseleccionarTodosLosCursosAcademicos();
}
