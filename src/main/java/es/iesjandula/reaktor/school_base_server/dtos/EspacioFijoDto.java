package es.iesjandula.reaktor.school_base_server.dtos;

/**
 * Representa el DTO de la entidad Fijo.
 * 
 * <p>
 * Esta clase se utiliza para representar un espacio fijo.
 * </p>
 */
public class EspacioFijoDto extends EspacioDto
{
	/** Curso del grupo asociado */
	private Integer curso;

	/** Etapa del grupo asociado */
	private String etapa;

	/** Grupo asociado */
	private String grupo;

	// CONSTRUCTOR VACÍO
	public EspacioFijoDto()
	{
		super();
	}

	/**
	 * Constructor de la clase.
	 * 
	 * @param cursoAcademico El curso académico del espacio.
	 * @param nombre         El nombre del espacio.
	 */
	public EspacioFijoDto(String cursoAcademico, String nombre)
	{
		super(cursoAcademico, nombre);
	}

	/**
	 * Constructor con etapa, curso y grupo.
	 * 
	 * @param cursoAcademico Curso académico
	 * @param nombre         Nombre del espacio
	 * @param curso          Curso del grupo
	 * @param etapa          Etapa del grupo
	 * @param grupo          Grupo
	 */
	public EspacioFijoDto(String cursoAcademico, String nombre, Integer curso, String etapa, String grupo)
	{
		super(cursoAcademico, nombre);
		this.curso = curso;
		this.etapa = etapa;
		this.grupo = grupo;
	}

	public Integer getCurso()
	{
		return curso;
	}

	public void setCurso(Integer curso)
	{
		this.curso = curso;
	}

	public String getEtapa()
	{
		return etapa;
	}

	public void setEtapa(String etapa)
	{
		this.etapa = etapa;
	}

	public String getGrupo()
	{
		return grupo;
	}

	public void setGrupo(String grupo)
	{
		this.grupo = grupo;
	}

	/**
	 * Override del método toString.
	 * 
	 * @return El string con el nombre del espacio.
	 */
	@Override
	public String toString()
	{
		return super.toString();
	}

	/**
	 * Override del método equals.
	 * 
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
	 * 
	 * @return El hashcode del objeto.
	 */
	@Override
	public int hashCode()
	{
		return super.hashCode();
	}
}
