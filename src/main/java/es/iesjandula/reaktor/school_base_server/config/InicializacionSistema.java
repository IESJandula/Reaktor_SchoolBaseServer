package es.iesjandula.reaktor.school_base_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.iesjandula.reaktor.school_base_server.models.CursoAcademico;
import es.iesjandula.reaktor.school_base_server.repository.ICursoAcademicoRepository;
import es.iesjandula.reaktor.school_base_server.utils.Constants;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InicializacionSistema
{
	@Autowired
	private ICursoAcademicoRepository cursoAcademicoRepository;

    @Value("${" + Constants.SPRING_JPA_HIBERNATE_DDL_AUTO + "}")
	private String ddlAuto;

	@Value("${" + Constants.PARAM_YAML_CURSO_ACADEMICO_SELECCIONADO + "}")
	private String cursoAcademicoSeleccionado;

	/**
	 * Este método se encarga de inicializar el sistema ya sea en el entorno de desarrollo o ejecutando JAR
	 */
	@PostConstruct
	public void inicializarSistema()
	{
        if (!this.ddlAuto.equals(Constants.VALOR_SPRING_JPA_HIBERNATE_DDL_AUTO))
		{
            // Inicializamos el curso académico
            this.inicializarCursoAcademico();
        }
	}

	/**
	 * Este método se encarga de inicializar el curso académico siempre
	 * que estemos creando la base de datos ya sea en el entorno de desarrollo o
	 * ejecutando JAR
	 */
	private void inicializarCursoAcademico()
	{
		// Borramos los cursos académicos
		this.cursoAcademicoRepository.deleteAll();

		// Iteramos por todos los cursos académicos disponibles
		for (String cursoAcademicoString : Constants.CURSOS_ACADEMICOS)
		{
			// Creamos un nuevo curso académico
			CursoAcademico cursoAcademicoEntity = new CursoAcademico();

            // Seteamos el curso académico y si está seleccionado
			cursoAcademicoEntity.setCursoAcademico(cursoAcademicoString);
			cursoAcademicoEntity.setSeleccionado(cursoAcademicoString.equals(this.cursoAcademicoSeleccionado));

			// Guardamos el curso académico
			this.cursoAcademicoRepository.save(cursoAcademicoEntity);
		}
	}
}
