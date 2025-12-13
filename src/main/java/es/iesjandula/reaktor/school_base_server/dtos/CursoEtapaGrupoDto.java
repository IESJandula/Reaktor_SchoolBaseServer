package es.iesjandula.reaktor.school_base_server.dtos;

import lombok.Data;

@Data
public class CursoEtapaGrupoDto
{
    /** Curso. */
    private Integer curso;

    /** Etapa. */
    private String etapa;
    
    /** Grupo. */
    private String grupo;
}
