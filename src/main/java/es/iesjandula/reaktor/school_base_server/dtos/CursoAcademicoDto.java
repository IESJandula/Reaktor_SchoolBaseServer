package es.iesjandula.reaktor.school_base_server.dtos;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class CursoAcademicoDto
{
    /** Curso académico */
    private String cursoAcademico;

    /** Seleccionado */
    private boolean seleccionado;
}
