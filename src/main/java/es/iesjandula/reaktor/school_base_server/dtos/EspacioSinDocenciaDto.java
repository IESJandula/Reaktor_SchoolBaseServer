package es.iesjandula.reaktor.school_base_server.dtos;

/**
 * Representa el DTO de la entidad SinDocencia.
 * 
 * <p>Esta clase se utiliza para representar un espacio de sin docencia.</p>
 */
public class EspacioSinDocenciaDto extends EspacioDto
{
    /**
     * Constructor de la clase.
     * @param cursoAcademico El curso académico del espacio.
     * @param nombre El nombre del espacio.
     */
    public EspacioSinDocenciaDto(String cursoAcademico, String nombre)
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
        return (this == object) || (super.equals(object) && (object instanceof EspacioSinDocenciaDto));
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
