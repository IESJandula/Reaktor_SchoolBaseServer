package es.iesjandula.reaktor.school_base_server.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.reaktor.base.utils.BaseConstants;
import es.iesjandula.reaktor.school_base_server.dtos.DesdobleDto;
import es.iesjandula.reaktor.school_base_server.dtos.EspacioDto;
import es.iesjandula.reaktor.school_base_server.dtos.FijoDto;
import es.iesjandula.reaktor.school_base_server.dtos.SinDocenciaDto;
import es.iesjandula.reaktor.school_base_server.models.CursoAcademico;
import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioDesdoble;
import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioFijo;
import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioSinDocencia;
import es.iesjandula.reaktor.school_base_server.models.ids.EspacioId;
import es.iesjandula.reaktor.school_base_server.repository.IDesdobleRepository;
import es.iesjandula.reaktor.school_base_server.repository.ICursoAcademicoRepository;
import es.iesjandula.reaktor.school_base_server.repository.IFijoRepository;
import es.iesjandula.reaktor.school_base_server.repository.ISinDocenciaRepository;
import es.iesjandula.reaktor.school_base_server.utils.Constants;
import es.iesjandula.reaktor.school_base_server.utils.ReaktorSchoolBaseServerException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/school_base_server/admin")
public class AdminRestController
{
    /** Repositorio de cursos académicos */
	@Autowired
	private ICursoAcademicoRepository cursoAcademicoRepository;

    /** Repositorio de sin docencia */
	@Autowired
	private ISinDocenciaRepository sinDocenciaRepository;

    /** Repositorio de fijo */
    @Autowired
    private IFijoRepository fijoRepository;

    /** Repositorio de desdoble */
	@Autowired
	private IDesdobleRepository desdobleRepository;

    /**
     * Obtiene la lista de años académicos.
     * @return La respuesta HTTP con la lista de años académicos.
     */
    @PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
    @GetMapping(value = "/cursos_academicos")
    public ResponseEntity<?> obtenerCursosAcademicos()
    {
        try
        {
            // Obtenemos todos los cursos académicos de la BBDD
            return ResponseEntity.ok(this.cursoAcademicoRepository.findAll());
        }
        catch (Exception exception)
        {
            // Creamos la excepción genérica
            ReaktorSchoolBaseServerException reaktorSchoolBaseServerException = new ReaktorSchoolBaseServerException(Constants.ERROR_GENERICO_CODE, Constants.ERROR_GENERICO_MESSAGE);

            // Logueamos el error
            log.error("Error generico al obtener los cursos académicos: " + exception.getMessage(), exception);

            // Devolvemos la excepción genérica
            return ResponseEntity.status(Constants.ERROR_GENERICO_CODE).body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
    }

    /**
     * Asigna un año académico a los espacios.
     * @param anioAcademico El año académico a asignar.
     * @return La respuesta HTTP con el año académico asignado.
     * @throws ReaktorSchoolBaseServerException Si el año académico es nulo o vacío.
     */
    @PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
    @PostMapping(value = "/cursos_academicos")
    public ResponseEntity<?> seleccionarCursoAcademico(@RequestHeader(value = "cursoAcademico") String cursoAcademico)
    {
        try
        {
            // Validamos el curso académico
            CursoAcademico cursoAcademicoEntity = this.validarCursoAcademico(cursoAcademico);

            // Quitamos la selección de todos los cursos académicos
            this.cursoAcademicoRepository.deseleccionarTodosLosCursosAcademicos();

            // Seteamos el curso académico seleccionado
            cursoAcademicoEntity.setSeleccionado(true);

            // Guardamos el curso académico en la BBDD
            this.cursoAcademicoRepository.saveAndFlush(cursoAcademicoEntity);

            // Logueamos
            log.info("Curso académico seleccionado correctamente");

            // Devolvemos la respuesta
            return ResponseEntity.ok().build();
        }
        catch (ReaktorSchoolBaseServerException reaktorSchoolBaseServerException)
        {
            // Devolvemos la excepción en la respuesta
            return ResponseEntity.badRequest().body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
        catch (Exception exception)
        {
            // Creamos la excepción genérica
            ReaktorSchoolBaseServerException reaktorSchoolBaseServerException = new ReaktorSchoolBaseServerException(Constants.ERROR_GENERICO_CODE, Constants.ERROR_GENERICO_MESSAGE);

            // Logueamos el error
            log.error("Error generico al seleccionar el curso académico: " + exception.getMessage(), exception);

            // Devolvemos la excepción genérica
            return ResponseEntity.status(Constants.ERROR_GENERICO_CODE).body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
    }

    /**
     * Crea un espacio sin docencia a partir del DTO.
     * @param sinDocenciaDto El DTO del espacio sin docencia a crear.
     * @return La respuesta HTTP con el espacio creado.
     * @throws ReaktorSchoolBaseServerException Si el espacio es nulo o vacío.
     */
	@PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
	@PostMapping(value = "/espacios/sin_docencia", consumes = "application/json")
	public ResponseEntity<?> crearEspacioSinDocencia(@RequestBody SinDocenciaDto sinDocenciaDto)
	{
		try
		{
            // Validamos el DTO del espacio
			this.validarEspacioDto(sinDocenciaDto);
			
            // Validamos si ya existe el espacio en cualquiera de los tres repositorios
            EspacioId espacioId = this.validarSiExisteElEspacio(sinDocenciaDto);

            // Creamos el espacio a partir del DTO
            EspacioSinDocencia espacio = new EspacioSinDocencia();
            espacio.setEspacioId(espacioId);

            // Guardamos el espacio en el repositorio de sin docencia
            this.sinDocenciaRepository.saveAndFlush(espacio);

			// Logueamos
			log.info("Espacio sin docencia creado correctamente");

            // Devolvemos la respuesta
			return ResponseEntity.ok().build();

		} 
		catch (ReaktorSchoolBaseServerException reaktorSchoolBaseServerException)
		{
			// Devolvemos la excepción en la respuesta
			return ResponseEntity.badRequest().body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
		}
		catch (Exception exception) 
		{
            // Creamos la excepción genérica
			ReaktorSchoolBaseServerException reaktorSchoolBaseServerException = new ReaktorSchoolBaseServerException(Constants.ERROR_GENERICO_CODE, Constants.ERROR_GENERICO_MESSAGE);

            // Logueamos el error
            log.error("Error generico al crear el espacio sin docencia: " + exception.getMessage(), exception);

            // Devolvemos la excepción genérica
			return ResponseEntity.status(Constants.ERROR_GENERICO_CODE).body(reaktorSchoolBaseServerException.getBodyExceptionMessage()); 
		}
	}

    /**
     * Crea un espacio fijo a partir del DTO.
     * @param fijoDto El DTO del espacio fijo a crear.
     * @return La respuesta HTTP con el espacio creado.
     * @throws ReaktorSchoolBaseServerException Si el espacio es nulo o vacío.
     */
	@PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
	@PostMapping(value = "/espacios/fijo", consumes = "application/json")
	public ResponseEntity<?> crearEspacioFijo(@RequestBody FijoDto fijoDto)
	{
        try
        {
            // Validamos el DTO del espacio
            this.validarEspacioDto(fijoDto);
            
            // Validamos si ya existe el espacio en cualquiera de los tres repositorios
            EspacioId espacioId = this.validarSiExisteElEspacio(fijoDto);

            // Creamos el espacio a partir del DTO
            EspacioFijo espacio = new EspacioFijo();
            espacio.setEspacioId(espacioId);

            // Guardamos el espacio en el repositorio de fijo
            this.fijoRepository.saveAndFlush(espacio);

            // Logueamos
            log.info("Espacio fijo creado correctamente");

            return ResponseEntity.ok().build();

        } 
        catch (ReaktorSchoolBaseServerException reaktorSchoolBaseServerException)
        {
            // Devolvemos la excepción en la respuesta
            return ResponseEntity.badRequest().body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
        catch (Exception exception) 
        {
            // Creamos la excepción genérica
            ReaktorSchoolBaseServerException reaktorSchoolBaseServerException = new ReaktorSchoolBaseServerException(Constants.ERROR_GENERICO_CODE, Constants.ERROR_GENERICO_MESSAGE);

            // Logueamos el error
            log.error("Error generico al crear el espacio fijo: " + exception.getMessage(), exception);

            // Devolvemos la excepción genérica
            return ResponseEntity.status(Constants.ERROR_GENERICO_CODE).body(reaktorSchoolBaseServerException.getBodyExceptionMessage()); 
        }
    }

    /**
     * Crea un espacio desdoble a partir del DTO.
     * @param desdobleDto El DTO del espacio desdoble a crear.
     * @return La respuesta HTTP con el espacio creado.
     * @throws ReaktorSchoolBaseServerException Si el espacio es nulo o vacío.
     */
    @PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
    @PostMapping(value = "/espacios/desdoble", consumes = "application/json")
    public ResponseEntity<?> crearEspacioDesdoble(@RequestBody DesdobleDto desdobleDto)
    {
        try
        {
            // Validamos el DTO del espacio
            this.validarEspacioDto(desdobleDto);
            
            // Validamos si ya existe el espacio en cualquiera de los tres repositorios
            EspacioId espacioId = this.validarSiExisteElEspacio(desdobleDto);

            // Creamos el espacio a partir del DTO
            EspacioDesdoble espacio = new EspacioDesdoble();
            espacio.setEspacioId(espacioId);

            // Guardamos el espacio en el repositorio de desdoble
            this.desdobleRepository.saveAndFlush(espacio);

            // Logueamos
            log.info("Espacio desdoble creado correctamente");

            return ResponseEntity.ok().build();

        } 
        catch (ReaktorSchoolBaseServerException reaktorSchoolBaseServerException)
        {
            // Devolvemos la excepción en la respuesta
            return ResponseEntity.badRequest().body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
        catch (Exception exception) 
        {
            // Creamos la excepción genérica
            ReaktorSchoolBaseServerException reaktorSchoolBaseServerException = new ReaktorSchoolBaseServerException(Constants.ERROR_GENERICO_CODE, Constants.ERROR_GENERICO_MESSAGE);

            // Logueamos el error
            log.error("Error generico al crear el espacio desdoble: " + exception.getMessage(), exception);

            // Devolvemos la excepción genérica
            return ResponseEntity.status(Constants.ERROR_GENERICO_CODE).body(reaktorSchoolBaseServerException.getBodyExceptionMessage()); 
        }
    }
    
    /**
     * Valida que el DTO del espacio no sea nulo o vacío en cualquiera de sus campos.
     * @param espacioDto El DTO del espacio a validar.
     * @return El curso académico encontrado.
     * @throws ReaktorSchoolBaseServerException Si el espacio es nulo o vacío.
     */
    private CursoAcademico validarEspacioDto(EspacioDto espacioDto) throws ReaktorSchoolBaseServerException
    {
        // Validamos el curso académico
        CursoAcademico cursoAcademicoEntity = this.validarCursoAcademico(espacioDto.getCursoAcademico());

        // Validamos el nombre
        if (espacioDto.getNombre() == null || espacioDto.getNombre().isEmpty())
        {
            log.error(Constants.ERR_ESPACIO_NOMBRE_NULO_VACIO_MESSAGE);
            throw new ReaktorSchoolBaseServerException(Constants.ERR_ESPACIO_NOMBRE_NULO_VACIO_CODE, Constants.ERR_ESPACIO_NOMBRE_NULO_VACIO_MESSAGE);
        }

        // Devolvemos el curso académico encontrado
        return cursoAcademicoEntity;
    }

    /**
     * Valida que el curso académico no sea nulo o vacío.
     * @param cursoAcademico El curso académico a validar.
     * @return El curso académico encontrado.
     * @throws ReaktorSchoolBaseServerException Si el curso académico es nulo o vacío o no existe en la BBDD.
     */
    private CursoAcademico validarCursoAcademico(String cursoAcademico) throws ReaktorSchoolBaseServerException
    {
        if (cursoAcademico == null || cursoAcademico.isEmpty())
        {
            log.error(Constants.ERR_ESPACIO_CURSO_ACADEMICO_NULO_VACIO_MESSAGE);
            throw new ReaktorSchoolBaseServerException(Constants.ERR_ESPACIO_CURSO_ACADEMICO_NULO_VACIO_CODE, Constants.ERR_ESPACIO_CURSO_ACADEMICO_NULO_VACIO_MESSAGE);
        }

        // Buscamos el curso académico en la BBDD
        Optional<CursoAcademico> cursoAcademicoEntity = this.cursoAcademicoRepository.findById(cursoAcademico);

        // Validamos si el curso académico existe en la BBDD
        if (cursoAcademicoEntity.isEmpty())
        {
            log.error(Constants.ERR_CURSO_ACADEMICO_NO_EXISTE_MESSAGE);
            throw new ReaktorSchoolBaseServerException(Constants.ERR_CURSO_ACADEMICO_NO_EXISTE_CODE, Constants.ERR_CURSO_ACADEMICO_NO_EXISTE_MESSAGE);
        }

        // Devolvemos el curso académico encontrado
        return cursoAcademicoEntity.get();
    }

    /**
     * Valida si el espacio ya existe en cualquiera de los tres repositorios.
     * @param espacioDto El DTO del espacio a validar.
     * @return La clave primaria compuesta del espacio.
     * @throws ReaktorSchoolBaseServerException Si el espacio ya existe.
     */
    private EspacioId validarSiExisteElEspacio(EspacioDto espacioDto) throws ReaktorSchoolBaseServerException
    {
        // Creamos la clave primaria compuesta del espacio a partir del DTO
        EspacioId espacioId = new EspacioId();

        // Asignamos los valores de la clave primaria compuesta
        espacioId.setCursoAcademico(espacioDto.getCursoAcademico());
        espacioId.setNombre(espacioDto.getNombre());

        // Validamos si el espacio ya existe en el repositorio de sin docencia
        if (this.sinDocenciaRepository.existsById(espacioId))
        {
            log.error(Constants.ERR_ESPACIO_YA_EXISTE_EN_SIN_DOCENCIA_MESSAGE);
            throw new ReaktorSchoolBaseServerException(Constants.ERR_ESPACIO_YA_EXISTE_EN_SIN_DOCENCIA_CODE, Constants.ERR_ESPACIO_YA_EXISTE_EN_SIN_DOCENCIA_MESSAGE);
        }

        // Validamos si el espacio ya existe en el repositorio de fijo
        if (this.fijoRepository.existsById(espacioId))
        {
            log.error(Constants.ERR_ESPACIO_YA_EXISTE_EN_FIJO_MESSAGE);
            throw new ReaktorSchoolBaseServerException(Constants.ERR_ESPACIO_YA_EXISTE_EN_FIJO_CODE, Constants.ERR_ESPACIO_YA_EXISTE_EN_FIJO_MESSAGE);
        }

        // Validamos si el espacio ya existe en el repositorio de desdoble
        if (this.desdobleRepository.existsById(espacioId))
        {
            log.error(Constants.ERR_ESPACIO_YA_EXISTE_EN_DESDOBLE_MESSAGE);
            throw new ReaktorSchoolBaseServerException(Constants.ERR_ESPACIO_YA_EXISTE_EN_DESDOBLE_CODE, Constants.ERR_ESPACIO_YA_EXISTE_EN_DESDOBLE_MESSAGE);
        }

        return espacioId;
    }
}
