package es.iesjandula.reaktor.school_base_server.dtos;

import lombok.Data;

@Data
public class CursoAcademicoDto
{
    /** Curso académico */
    private String cursoAcademico;

    /** Seleccionado */
    private boolean seleccionado;
}
