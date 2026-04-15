package es.iesjandula.reaktor.school_base_server.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.reaktor.base.utils.BaseConstants;
import es.iesjandula.reaktor.school_base_server.dtos.CursoEtapaGrupoDto;
import es.iesjandula.reaktor.school_base_server.repository.ICursoEtapaGrupoRepository;
import es.iesjandula.reaktor.school_base_server.utils.Constants;
import es.iesjandula.reaktor.school_base_server.utils.ReaktorSchoolBaseServerException;

@Slf4j
@RestController
@RequestMapping("/school_base_server/common")
public class CommonRestController
{
    /** Repositorio de curso etapa grupo */
	@Autowired
	private ICursoEtapaGrupoRepository cursoEtapaGrupoRepository;

	/**
	 * Obtiene la lista de cursos, etapas y grupos.
	 * 
	 * @return La respuesta HTTP con la lista de cursos, etapas y grupos.
	 */
	@PreAuthorize("hasRole('" + BaseConstants.ROLE_PROFESOR + "')")
	@GetMapping(value = "/cursos_etapas_grupos")
	public ResponseEntity<?> obtenerCursosEtapasGrupos()
	{
		try
		{
			// Obtenemos todos los cursos, etapas y grupos en formato DTO
			List<CursoEtapaGrupoDto> cursosEtapasGruposDto = this.cursoEtapaGrupoRepository.findAllDto();

			// Devolvemos la respuesta
			return ResponseEntity.ok(cursosEtapasGruposDto);
		} catch (Exception exception)
		{
			// Creamos la excepción genérica
			ReaktorSchoolBaseServerException reaktorSchoolBaseServerException = new ReaktorSchoolBaseServerException(
					Constants.ERROR_GENERICO_CODE, Constants.ERROR_GENERICO_MESSAGE);

			// Logueamos el error
			log.error("Error generico al obtener los cursos, etapas y grupos: " + exception.getMessage(), exception);

			// Devolvemos la excepción genérica
			return ResponseEntity.status(Constants.ERROR_GENERICO_CODE)
					.body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
		}
	}
}
