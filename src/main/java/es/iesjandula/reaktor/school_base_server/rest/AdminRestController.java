package es.iesjandula.reaktor.school_base_server.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.reaktor.base.utils.BaseConstants;
import es.iesjandula.reaktor.school_base_server.dtos.CursoAcademicoDto;
import es.iesjandula.reaktor.school_base_server.dtos.CursoEtapaGrupoDto;
import es.iesjandula.reaktor.school_base_server.dtos.EspacioDesdobleDto;
import es.iesjandula.reaktor.school_base_server.dtos.EspacioDto;
import es.iesjandula.reaktor.school_base_server.dtos.EspacioFijoDto;
import es.iesjandula.reaktor.school_base_server.dtos.EspacioSinDocenciaDto;
import es.iesjandula.reaktor.school_base_server.models.CursoAcademico;
import es.iesjandula.reaktor.school_base_server.models.CursoEtapaGrupo;
import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioDesdoble;
import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioFijo;
import es.iesjandula.reaktor.school_base_server.models.espacios.EspacioSinDocencia;
import es.iesjandula.reaktor.school_base_server.models.ids.CursoEtapaGrupoId;
import es.iesjandula.reaktor.school_base_server.models.ids.EspacioId;
import es.iesjandula.reaktor.school_base_server.repository.IEspacioDesdobleRepository;
import es.iesjandula.reaktor.school_base_server.repository.ICursoAcademicoRepository;
import es.iesjandula.reaktor.school_base_server.repository.ICursoEtapaGrupoRepository;
import es.iesjandula.reaktor.school_base_server.repository.IEspacioFijoRepository;
import es.iesjandula.reaktor.school_base_server.repository.IEspacioSinDocenciaRepository;
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

    /** Repositorio de curso etapa grupo */
	@Autowired
	private ICursoEtapaGrupoRepository cursoEtapaGrupoRepository;

    /** Repositorio de sin docencia */
	@Autowired
	private IEspacioSinDocenciaRepository espacioSinDocenciaRepository;

    /** Repositorio de fijo */
    @Autowired
    private IEspacioFijoRepository espacioFijoRepository;

    /** Repositorio de desdoble */
	@Autowired
	private IEspacioDesdobleRepository espacioDesdobleRepository;

    /**
     * Obtiene la lista de cursos académicos.
     * @return La respuesta HTTP con la lista de cursos académicos.
     */
    @PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
    @GetMapping(value = "/cursos_academicos")
    public ResponseEntity<?> obtenerCursosAcademicos()
    {
        try
        {
            // Obtenemos todos los cursos académicos en formato DTO
            List<CursoAcademicoDto> cursosAcademicosDto = this.cursoAcademicoRepository.findAllDto();

            // Devolvemos la respuesta
            return ResponseEntity.ok(cursosAcademicosDto);
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
     * Asigna un curso académico a los espacios.
     * @param cursoAcademico El curso académico a asignar.
     * @return La respuesta HTTP con el curso académico asignado.
     * @throws ReaktorSchoolBaseServerException Si el curso académico es nulo o vacío.
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
     * Crea un curso, etapa y grupo de espacio sin docencia a partir del DTO.
     * @param cursoEtapaGrupoDto El DTO del curso, etapa y grupo a crear.
     * @return La respuesta HTTP con el curso, etapa y grupo de espacio sin docencia creado.
     * @throws ReaktorSchoolBaseServerException Si el curso, etapa y grupo es nulo o vacío.
     */
	@PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
	@PostMapping(value = "/cursos_etapas_grupos", consumes = "application/json")
	public ResponseEntity<?> crearCursoEtapaGrupo(@RequestBody CursoEtapaGrupoDto cursoEtapaGrupoDto)
	{
		try
		{
            // Validamos el DTO del curso, etapa y grupo
			this.validarCursoAcademicoEtapaGrupoDto(cursoEtapaGrupoDto);
			
            // Validamos si ya existe el curso, etapa y grupo en el repositorio de curso etapa grupo
            CursoEtapaGrupoId cursoEtapaGrupoId = this.validarCreacionCursoEtapaGrupo(cursoEtapaGrupoDto);

            // Creamos el curso, etapa y grupo a partir del DTO
            CursoEtapaGrupo cursoEtapaGrupo = new CursoEtapaGrupo();
            cursoEtapaGrupo.setCursoEtapaGrupoId(cursoEtapaGrupoId);

            // Guardamos el curso, etapa y grupo en el repositorio de curso etapa grupo
            this.cursoEtapaGrupoRepository.saveAndFlush(cursoEtapaGrupo);

			// Logueamos
			log.info("Curso, etapa y grupo creado correctamente");

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
            log.error("Error generico al crear el curso, etapa y grupo: " + exception.getMessage(), exception);

            // Devolvemos la excepción genérica
			return ResponseEntity.status(Constants.ERROR_GENERICO_CODE).body(reaktorSchoolBaseServerException.getBodyExceptionMessage()); 
		}
	}

    /**
     * Valida si el curso, etapa y grupo ya existe en cualquiera de los tres repositorios.
     * @param cursoEtapaGrupoDto El DTO del curso, etapa y grupo a validar.
     * @return El curso, etapa y grupo encontrado o null si no existe.
     * @throws ReaktorSchoolBaseServerException Si el curso, etapa y grupo ya existe.
     */
    private CursoEtapaGrupoId validarCreacionCursoEtapaGrupo(CursoEtapaGrupoDto cursoEtapaGrupoDto) throws ReaktorSchoolBaseServerException
    {
        // Creamos la clave primaria compuesta del curso, etapa y grupo a partir del DTO
        CursoEtapaGrupoId cursoEtapaGrupoId = new CursoEtapaGrupoId();

        // Asignamos los valores de la clave primaria compuesta
        cursoEtapaGrupoId.setCurso(cursoEtapaGrupoDto.getCurso());
        cursoEtapaGrupoId.setEtapa(cursoEtapaGrupoDto.getEtapa());
        cursoEtapaGrupoId.setGrupo(cursoEtapaGrupoDto.getGrupo());

        // Buscamos el curso, etapa y grupo en el repositorio de curso etapa grupo
        if (this.cursoEtapaGrupoRepository.existsById(cursoEtapaGrupoId))
        {
            log.error(Constants.ERR_CURSO_ETAPA_GRUPO_YA_EXISTE_MESSAGE);
            throw new ReaktorSchoolBaseServerException(Constants.ERR_CURSO_ETAPA_GRUPO_YA_EXISTE_CODE, Constants.ERR_CURSO_ETAPA_GRUPO_YA_EXISTE_MESSAGE);
        }

        // Devolvemos el curso, etapa y grupo encontrado o null si no existe
        return cursoEtapaGrupoId;
    }

    /**
     * Obtiene la lista de cursos, etapas y grupos.
     * @return La respuesta HTTP con la lista de cursos, etapas y grupos.
     */
    @PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
    @GetMapping(value = "/cursos_etapas_grupos")
    public ResponseEntity<?> obtenerCursosEtapasGrupos()
    {
        try
        {
            // Obtenemos todos los cursos, etapas y grupos en formato DTO
            List<CursoEtapaGrupoDto> cursosEtapasGruposDto = this.cursoEtapaGrupoRepository.findAllDto();

            // Devolvemos la respuesta
            return ResponseEntity.ok(cursosEtapasGruposDto);
        }
        catch (Exception exception)
        {
            // Creamos la excepción genérica
            ReaktorSchoolBaseServerException reaktorSchoolBaseServerException = new ReaktorSchoolBaseServerException(Constants.ERROR_GENERICO_CODE, Constants.ERROR_GENERICO_MESSAGE);

            // Logueamos el error
            log.error("Error generico al obtener los cursos, etapas y grupos: " + exception.getMessage(), exception);

            // Devolvemos la excepción genérica
            return ResponseEntity.status(Constants.ERROR_GENERICO_CODE).body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
    }

    /**
     * Borra un curso, etapa y grupo a partir del DTO.
     * @param cursoEtapaGrupoDto El DTO del curso, etapa y grupo a borrar.
     * @return La respuesta HTTP con el curso, etapa y grupo borrado.
     * @throws ReaktorSchoolBaseServerException Si el curso, etapa y grupo es nulo o vacío.
     */
    @PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
    @DeleteMapping(value = "/cursos_etapas_grupos", consumes = "application/json")
    public ResponseEntity<?> borrarCursoEtapaGrupo(@RequestBody CursoEtapaGrupoDto cursoEtapaGrupoDto)
    {
        try
        {
            // Validamos el DTO del curso, etapa y grupo
            this.validarCursoAcademicoEtapaGrupoDto(cursoEtapaGrupoDto);

            // Creamos la clave primaria compuesta del curso, etapa y grupo a partir del DTO
            CursoEtapaGrupoId cursoEtapaGrupoId = new CursoEtapaGrupoId();

            // Asignamos los valores de la clave primaria compuesta
            cursoEtapaGrupoId.setCurso(cursoEtapaGrupoDto.getCurso());
            cursoEtapaGrupoId.setEtapa(cursoEtapaGrupoDto.getEtapa());
            cursoEtapaGrupoId.setGrupo(cursoEtapaGrupoDto.getGrupo());

            // Validamos si el curso, etapa y grupo ya existe en el repositorio de curso etapa grupo
            if (!this.cursoEtapaGrupoRepository.existsById(cursoEtapaGrupoId))
            {
                log.error(Constants.ERR_CURSO_ETAPA_GRUPO_NO_EXISTE_MESSAGE);
                throw new ReaktorSchoolBaseServerException(Constants.ERR_CURSO_ETAPA_GRUPO_NO_EXISTE_CODE, Constants.ERR_CURSO_ETAPA_GRUPO_NO_EXISTE_MESSAGE);
            }

            // Borramos el curso, etapa y grupo en el repositorio de curso etapa grupo
            this.cursoEtapaGrupoRepository.deleteById(cursoEtapaGrupoId);

            // Logueamos
            log.info("Curso, etapa y grupo borrado correctamente");

            // Devolvemos la respuesta
            return ResponseEntity.noContent().build();
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
            log.error("Error generico al borrar el curso, etapa y grupo: " + exception.getMessage(), exception);

            // Devolvemos la excepción genérica
            return ResponseEntity.status(Constants.ERROR_GENERICO_CODE).body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
    }
    
    /**
     * Valida que el DTO del curso, etapa y grupo no sea nulo o vacío.
     * @param cursoEtapaGrupoDto El DTO del curso, etapa y grupo a validar.
     * @throws ReaktorSchoolBaseServerException Si el curso, etapa y grupo es nulo o vacío.
     */
    private void validarCursoAcademicoEtapaGrupoDto(CursoEtapaGrupoDto cursoEtapaGrupoDto) throws ReaktorSchoolBaseServerException
    {
        // Validamos el curso
        if (cursoEtapaGrupoDto.getCurso() == null)
        {
            log.error(Constants.ERR_CURSO_NULO_VACIO_MESSAGE);
            throw new ReaktorSchoolBaseServerException(Constants.ERR_CURSO_ACADEMICO_NULO_VACIO_CODE, Constants.ERR_CURSO_ACADEMICO_NULO_VACIO_MESSAGE);
        }
        // Validamos la etapa
        if (cursoEtapaGrupoDto.getEtapa() == null || cursoEtapaGrupoDto.getEtapa().isEmpty())
        {
            log.error(Constants.ERR_ETAPA_NULO_VACIO_MESSAGE);
            throw new ReaktorSchoolBaseServerException(Constants.ERR_ETAPA_NULO_VACIO_CODE, Constants.ERR_ETAPA_NULO_VACIO_MESSAGE);
        }
        // Validamos el grupo
        if (cursoEtapaGrupoDto.getGrupo() == null || cursoEtapaGrupoDto.getGrupo().isEmpty())
        {
            log.error(Constants.ERR_GRUPO_NULO_VACIO_MESSAGE);
            throw new ReaktorSchoolBaseServerException(Constants.ERR_GRUPO_NULO_VACIO_CODE, Constants.ERR_GRUPO_NULO_VACIO_MESSAGE);
        }
    }

    /**
     * Crea un espacio sin docencia a partir del DTO.
     * @param espacioSinDocenciaDto El DTO del espacio sin docencia a crear.
     * @return La respuesta HTTP con el espacio creado.
     * @throws ReaktorSchoolBaseServerException Si el espacio es nulo o vacío.
     */
	@PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
	@PostMapping(value = "/espacios/sin_docencia", consumes = "application/json")
	public ResponseEntity<?> crearEspacioSinDocencia(@RequestBody EspacioSinDocenciaDto espacioSinDocenciaDto)
	{
		try
		{
            // Validamos el DTO del espacio
			this.validarEspacioDto(espacioSinDocenciaDto);
			
            // Validamos si ya existe el espacio en cualquiera de los tres repositorios
            EspacioId espacioId = this.validarCreacionEspacio(espacioSinDocenciaDto);

            // Creamos el espacio a partir del DTO
            EspacioSinDocencia espacio = new EspacioSinDocencia();
            espacio.setEspacioId(espacioId);

            // Guardamos el espacio en el repositorio de sin docencia
            this.espacioSinDocenciaRepository.saveAndFlush(espacio);

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
     * Obtiene la lista de espacios sin docencia.
     * @return La respuesta HTTP con la lista de espacios sin docencia.
     */
    @PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
    @GetMapping(value = "/espacios/sin_docencia")
    public ResponseEntity<?> obtenerEspaciosSinDocencia()
    {
        try
        {
            // Obtenemos todos los espacios sin docencia en formato DTO
            List<EspacioSinDocenciaDto> espaciosSinDocenciaDto = this.espacioSinDocenciaRepository.findAllDto();

            // Devolvemos la respuesta
            return ResponseEntity.ok(espaciosSinDocenciaDto);
        }
        catch (Exception exception)
        {
            // Creamos la excepción genérica
            ReaktorSchoolBaseServerException reaktorSchoolBaseServerException = new ReaktorSchoolBaseServerException(Constants.ERROR_GENERICO_CODE, Constants.ERROR_GENERICO_MESSAGE);

            // Logueamos el error
            log.error("Error generico al obtener los espacios sin docencia: " + exception.getMessage(), exception);

            // Devolvemos la excepción genérica
            return ResponseEntity.status(Constants.ERROR_GENERICO_CODE).body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
    }

    /***
     * Borrar un espacio sin docencia
     * @param espacioSinDocenciaDto El DTO del espacio sin docencia a borrar.
     * @return ResponseEntity con el resultado de la borrada
     */
    @PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
    @DeleteMapping(value = "/espacios/sin_docencia", consumes = "application/json")
    public ResponseEntity<?> borrarEspacioSinDocencia(@RequestBody EspacioSinDocenciaDto espacioSinDocenciaDto)
    {
        try
        {
            // Validamos el DTO del espacio
            this.validarEspacioDto(espacioSinDocenciaDto);

            // Creamos la clave primaria compuesta del espacio a partir del DTO
            EspacioId espacioId = new EspacioId();

            // Asignamos los valores de la clave primaria compuesta
            espacioId.setCursoAcademico(espacioSinDocenciaDto.getCursoAcademico());
            espacioId.setNombre(espacioSinDocenciaDto.getNombre());

            // Validamos si el espacio ya existe en el repositorio de sin docencia
            if (!this.espacioSinDocenciaRepository.existsById(espacioId))
            {
                log.error(Constants.ERR_ESPACIO_NO_EXISTE_EN_SIN_DOCENCIA_MESSAGE);
                throw new ReaktorSchoolBaseServerException(Constants.ERR_ESPACIO_NO_EXISTE_EN_SIN_DOCENCIA_CODE, Constants.ERR_ESPACIO_NO_EXISTE_EN_SIN_DOCENCIA_MESSAGE);
            }
    
            // Borrar el espacio en el repositorio de sin docencia
            this.espacioSinDocenciaRepository.deleteById(espacioId);

            // Logueamos
            log.info("Espacio sin docencia borrado correctamente");
    
            // Devolvemos la respuesta correcta
            return ResponseEntity.noContent().build();
        }
        catch (ReaktorSchoolBaseServerException reaktorSchoolBaseServerException)
        {
            return ResponseEntity.badRequest().body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
        catch (Exception exception)
        {
            ReaktorSchoolBaseServerException reaktorSchoolBaseServerException = new ReaktorSchoolBaseServerException(Constants.ERROR_GENERICO_CODE, Constants.ERROR_GENERICO_MESSAGE);

            log.error("Error generico al borrar el espacio sin docencia: " + exception.getMessage(), exception);
            return ResponseEntity.status(Constants.ERROR_GENERICO_CODE).body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
    }

    /**
     * Crea un espacio fijo a partir del DTO.
     * @param espacioFijoDto El DTO del espacio fijo a crear.
     * @return La respuesta HTTP con el espacio creado.
     * @throws ReaktorSchoolBaseServerException Si el espacio es nulo o vacío.
     */
	@PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
	@PostMapping(value = "/espacios/fijo", consumes = "application/json")
	public ResponseEntity<?> crearEspacioFijo(@RequestBody EspacioFijoDto espacioFijoDto)
	{
        try
        {
            // Validamos el DTO del espacio
            this.validarEspacioDto(espacioFijoDto);
            
            // Validamos si ya existe el espacio en cualquiera de los tres repositorios
            EspacioId espacioId = this.validarCreacionEspacio(espacioFijoDto);

            // Creamos el espacio a partir del DTO
            EspacioFijo espacio = new EspacioFijo();
            espacio.setEspacioId(espacioId);

            // Guardamos el espacio en el repositorio de fijo
            this.espacioFijoRepository.saveAndFlush(espacio);

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
     * Obtiene la lista de espacios fijo.
     * @return La respuesta HTTP con la lista de espacios fijo.
     */
    @PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
    @GetMapping(value = "/espacios/fijo")
    public ResponseEntity<?> obtenerEspaciosFijo()
    {
        try
        {
            // Obtenemos todos los espacios fijo en formato DTO
            List<EspacioFijoDto> espaciosFijoDto = this.espacioFijoRepository.findAllDto();

            // Devolvemos la respuesta
            return ResponseEntity.ok(espaciosFijoDto);
        }
        catch (Exception exception)
        {
            // Creamos la excepción genérica
            ReaktorSchoolBaseServerException reaktorSchoolBaseServerException = new ReaktorSchoolBaseServerException(Constants.ERROR_GENERICO_CODE, Constants.ERROR_GENERICO_MESSAGE);

            // Logueamos el error
            log.error("Error generico al obtener los espacios fijo: " + exception.getMessage(), exception);

            // Devolvemos la excepción genérica
            return ResponseEntity.status(Constants.ERROR_GENERICO_CODE).body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
    }

    /***
     * Borrar un espacio fijo
     * @param espacioFijoDto El DTO del espacio fijo a borrar.
     * @return ResponseEntity con el resultado de la borrada
     */
    @PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
    @DeleteMapping(value = "/espacios/fijo", consumes = "application/json")
    public ResponseEntity<?> borrarEspacioFijo(@RequestBody EspacioFijoDto espacioFijoDto)
    {
        try
        {
            // Validamos el DTO del espacio
			this.validarEspacioDto(espacioFijoDto);

            // Creamos la clave primaria compuesta del espacio a partir del DTO
            EspacioId espacioId = new EspacioId();

            // Asignamos los valores de la clave primaria compuesta
            espacioId.setCursoAcademico(espacioFijoDto.getCursoAcademico());
            espacioId.setNombre(espacioFijoDto.getNombre());

            // Validamos si el espacio ya existe en el repositorio de fijo
            if (!this.espacioFijoRepository.existsById(espacioId))
            {
                log.error(Constants.ERR_ESPACIO_NO_EXISTE_EN_FIJO_MESSAGE);
                throw new ReaktorSchoolBaseServerException(Constants.ERR_ESPACIO_NO_EXISTE_EN_FIJO_CODE, Constants.ERR_ESPACIO_NO_EXISTE_EN_FIJO_MESSAGE);
            }
    
            // Borrar el espacio en el repositorio de fijo
            this.espacioFijoRepository.deleteById(espacioId);

            // Logueamos
            log.info("Espacio fijo borrado correctamente");
    
            // Devolvemos la respuesta correcta
            return ResponseEntity.noContent().build();
        }
        catch (ReaktorSchoolBaseServerException reaktorSchoolBaseServerException)
        {
            return ResponseEntity.badRequest().body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
        catch (Exception exception)
        {
            ReaktorSchoolBaseServerException reaktorSchoolBaseServerException = new ReaktorSchoolBaseServerException(Constants.ERROR_GENERICO_CODE, Constants.ERROR_GENERICO_MESSAGE);

            log.error("Error generico al borrar el espacio fijo: " + exception.getMessage(), exception);
            return ResponseEntity.status(Constants.ERROR_GENERICO_CODE).body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
    }

    /**
     * Crea un espacio desdoble a partir del DTO.
     * @param espacioDesdobleDto El DTO del espacio desdoble a crear.
     * @return La respuesta HTTP con el espacio creado.
     * @throws ReaktorSchoolBaseServerException Si el espacio es nulo o vacío.
     */
    @PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
    @PostMapping(value = "/espacios/desdoble", consumes = "application/json")
    public ResponseEntity<?> crearEspacioDesdoble(@RequestBody EspacioDesdobleDto espacioDesdobleDto)
    {
        try
        {
            // Validamos el DTO del espacio
            this.validarEspacioDto(espacioDesdobleDto);
            
            // Validamos si ya existe el espacio en cualquiera de los tres repositorios
            EspacioId espacioId = this.validarCreacionEspacio(espacioDesdobleDto);

            // Creamos el espacio a partir del DTO
            EspacioDesdoble espacio = new EspacioDesdoble();
            espacio.setEspacioId(espacioId);

            // Guardamos el espacio en el repositorio de desdoble
            this.espacioDesdobleRepository.saveAndFlush(espacio);

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
     * Obtiene la lista de espacios desdoble.
     * @return La respuesta HTTP con la lista de espacios desdoble.
     */
    @PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
    @GetMapping(value = "/espacios/desdoble")
    public ResponseEntity<?> obtenerEspaciosDesdoble()
    {
        try
        {
            // Obtenemos todos los espacios desdoble en formato DTO
            List<EspacioDesdobleDto> espaciosDesdobleDto = this.espacioDesdobleRepository.findAllDto();

            // Devolvemos la respuesta
            return ResponseEntity.ok(espaciosDesdobleDto);
        }
        catch (Exception exception)
        {
            // Creamos la excepción genérica
            ReaktorSchoolBaseServerException reaktorSchoolBaseServerException = new ReaktorSchoolBaseServerException(Constants.ERROR_GENERICO_CODE, Constants.ERROR_GENERICO_MESSAGE);

            // Logueamos el error
            log.error("Error generico al obtener los espacios desdoble: " + exception.getMessage(), exception);

            // Devolvemos la excepción genérica
            return ResponseEntity.status(Constants.ERROR_GENERICO_CODE).body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
    }

    /***
     * Borrar un espacio desdoble   
     * @param espacioDesdobleDto El DTO del espacio desdoble a borrar.
     * @return ResponseEntity con el resultado de la borrada
     */
    @PreAuthorize("hasRole('" + BaseConstants.ROLE_ADMINISTRADOR + "')")
    @DeleteMapping(value = "/espacios/desdoble", consumes = "application/json")
    public ResponseEntity<?> borrarEspacioDesdoble(@RequestBody EspacioDesdobleDto espacioDesdobleDto)
    {
        try
        {
            // Validamos el DTO del espacio
            this.validarEspacioDto(espacioDesdobleDto);

            // Creamos la clave primaria compuesta del espacio a partir del DTO
            EspacioId espacioId = new EspacioId();

            // Asignamos los valores de la clave primaria compuesta
            espacioId.setCursoAcademico(espacioDesdobleDto.getCursoAcademico());
            espacioId.setNombre(espacioDesdobleDto.getNombre());

            // Validamos si el espacio ya existe en el repositorio de desdoble
            if (!this.espacioDesdobleRepository.existsById(espacioId))
            {
                log.error(Constants.ERR_ESPACIO_NO_EXISTE_EN_DESDOBLE_MESSAGE);
                throw new ReaktorSchoolBaseServerException(Constants.ERR_ESPACIO_NO_EXISTE_EN_DESDOBLE_CODE, Constants.ERR_ESPACIO_NO_EXISTE_EN_DESDOBLE_MESSAGE);
            }
    
            // Borrar el espacio en el repositorio de desdoble
            this.espacioDesdobleRepository.deleteById(espacioId);

            // Logueamos
            log.info("Espacio desdoble borrado correctamente");
    
            // Devolvemos la respuesta correcta
            return ResponseEntity.noContent().build();
        }
        catch (ReaktorSchoolBaseServerException reaktorSchoolBaseServerException)
        {
            return ResponseEntity.badRequest().body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
        catch (Exception exception)
        {
            ReaktorSchoolBaseServerException reaktorSchoolBaseServerException = new ReaktorSchoolBaseServerException(Constants.ERROR_GENERICO_CODE, Constants.ERROR_GENERICO_MESSAGE);

            log.error("Error generico al borrar el espacio desdoble: " + exception.getMessage(), exception);
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
        Optional<CursoAcademico> cursoAcademicoEntity = this.cursoAcademicoRepository.findByCursoAcademico(cursoAcademico);

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
    private EspacioId validarCreacionEspacio(EspacioDto espacioDto) throws ReaktorSchoolBaseServerException
    {
        // Creamos la clave primaria compuesta del espacio a partir del DTO
        EspacioId espacioId = new EspacioId();

        // Asignamos los valores de la clave primaria compuesta
        espacioId.setCursoAcademico(espacioDto.getCursoAcademico());
        espacioId.setNombre(espacioDto.getNombre());

        // Validamos si el espacio ya existe en el repositorio de sin docencia
        if (this.espacioSinDocenciaRepository.existsById(espacioId))
        {
            log.error(Constants.ERR_ESPACIO_YA_EXISTE_EN_SIN_DOCENCIA_MESSAGE);
            throw new ReaktorSchoolBaseServerException(Constants.ERR_ESPACIO_YA_EXISTE_EN_SIN_DOCENCIA_CODE, Constants.ERR_ESPACIO_YA_EXISTE_EN_SIN_DOCENCIA_MESSAGE);
        }

        // Validamos si el espacio ya existe en el repositorio de fijo
        if (this.espacioFijoRepository.existsById(espacioId))
        {
            log.error(Constants.ERR_ESPACIO_YA_EXISTE_EN_FIJO_MESSAGE);
            throw new ReaktorSchoolBaseServerException(Constants.ERR_ESPACIO_YA_EXISTE_EN_FIJO_CODE, Constants.ERR_ESPACIO_YA_EXISTE_EN_FIJO_MESSAGE);
        }

        // Validamos si el espacio ya existe en el repositorio de desdoble
        if (this.espacioDesdobleRepository.existsById(espacioId))
        {
            log.error(Constants.ERR_ESPACIO_YA_EXISTE_EN_DESDOBLE_MESSAGE);
            throw new ReaktorSchoolBaseServerException(Constants.ERR_ESPACIO_YA_EXISTE_EN_DESDOBLE_CODE, Constants.ERR_ESPACIO_YA_EXISTE_EN_DESDOBLE_MESSAGE);
        }

        return espacioId;
    }
}
