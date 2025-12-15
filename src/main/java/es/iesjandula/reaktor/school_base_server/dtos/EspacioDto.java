package es.iesjandula.reaktor.school_base_server.dtos;

import java.util.Objects;

/**
 * Representa el DTO de la entidad Espacio.
 * 
 * <p>Esta clase se utiliza para representar un espacio.</p>
 */
public abstract class EspacioDto
{
    /**
     * Curso academico del espacio.
     */
    private String cursoAcademico;

    /**
     * Nombre del espacio.
     */
    private String nombre;

    /**
     * Constructor de la clase.
     * @param cursoAcademico El curso académico del espacio.
     * @param nombre El nombre del espacio.
     */
    public EspacioDto(String cursoAcademico, String nombre)
    {
        this.cursoAcademico = cursoAcademico;
        this.nombre = nombre;
    }

    /**
     * Getter del curso académico.
     * @return El curso académico.
     */
    public String getCursoAcademico()
    {
        return this.cursoAcademico;
    }
    
    /**
     * Getter del nombre.
     * @return El nombre.
     */
    public String getNombre()
    {
        return this.nombre;
    }

    /**
     * Setter del curso académico.
     * @param cursoAcademico El curso académico.
     */
    public void setCursoAcademico(String cursoAcademico)
    {
        this.cursoAcademico = cursoAcademico;
    }
    
    /**
     * Setter del nombre.
     * @param nombre El nombre.
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * Override del método toString.
     * @return El string con el nombre del espacio.
     */
    @Override
    public String toString()
    {
        return "EspacioDto [cursoAcademico=" + this.cursoAcademico + ", nombre=" + this.nombre + "]";
    }

    /**
     * Override del método equals.
     * @param object El objeto a comparar.
     * @return true si el objeto es igual, false en caso contrario.
     */
    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        else if (object == null)
        {
            return false;
        }
        else if (!(object instanceof EspacioDto))
        {
            return false;
        }

        // Hacemos casting para poder comparar los atributos del objeto.
        final EspacioDto other = (EspacioDto) object;

        // Comparamos los atributos del objeto y devolvemos el resultado.
        return this.cursoAcademico.equals(other.cursoAcademico) && this.nombre.equals(other.nombre);
    }

    /**
     * Override del método hashCode.
     * @return El hashcode del objeto.
     */
    @Override
    public int hashCode()
    {
        // Devolvemos el hashcode de los atributos del objeto.
        return Objects.hash(this.cursoAcademico, this.nombre);
    }
}
