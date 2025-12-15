package es.iesjandula.reaktor.school_base_server.dtos;

/**
 * Representa el DTO de la entidad Fijo.
 * 
 * <p>Esta clase se utiliza para representar un espacio fijo.</p>
 */
public class EspacioFijoDto extends EspacioDto
{
    /**
     * Constructor de la clase.
     * @param cursoAcademico El curso académico del espacio.
     * @param nombre El nombre del espacio.
     */
    public EspacioFijoDto(String cursoAcademico, String nombre)
    {
        super(cursoAcademico, nombre);
    }

    /**
     * Override del método toString.
     * @return El string con el nombre del espacio.
     */
    @Override
    public String toString()
    {
        return super.toString() ;
    }

    /**
     * Override del método equals.
     * @param object El objeto a comparar.
     * @return true si el objeto es igual, false en caso contrario.
     */
    @Override
    public boolean equals(Object object)
    {
        return (this == object) || (super.equals(object) && (object instanceof EspacioFijoDto));
    }

    /**
     * Override del método hashCode.
     * @return El hashcode del objeto.
     */
    @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}
