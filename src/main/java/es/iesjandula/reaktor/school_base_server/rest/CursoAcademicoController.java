package es.iesjandula.reaktor.school_base_server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.reaktor.base.utils.BaseConstants;
import es.iesjandula.reaktor.school_base_server.repository.ICursoAcademicoRepository;
import es.iesjandula.reaktor.school_base_server.utils.Constants;
import es.iesjandula.reaktor.school_base_server.utils.ReaktorSchoolBaseServerException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/school_base_server/curso_academico")
public class CursoAcademicoController
{
    @Autowired
    private ICursoAcademicoRepository cursoAcademicoRepository;

    @GetMapping("/")
    @PreAuthorize("hasRole('" + BaseConstants.ROLE_APLICACION_SCHOOL_BASE + "')")
    public ResponseEntity<?> obtenerCursoAcademicoSeleccionado()
    {
        try
        {
            // Obtenemos el curso académico seleccionado
            String cursoAcademico = this.cursoAcademicoRepository.obtenerCursoAcademicoSeleccionado();
            
            // Si no hay ningún curso académico seleccionado, lanzamos una excepción
            if (cursoAcademico == null || cursoAcademico.isEmpty())
            {                
                log.error(Constants.ERR_NO_CURSO_ACADEMICO_SELECCIONADO_MESSAGE);
                throw new ReaktorSchoolBaseServerException(Constants.ERR_NO_CURSO_ACADEMICO_SELECCIONADO_CODE, Constants.ERR_NO_CURSO_ACADEMICO_SELECCIONADO_MESSAGE);
            }            

            // Devolvemos el curso académico seleccionado
            return ResponseEntity.ok().body(cursoAcademico);
        }
        catch (ReaktorSchoolBaseServerException reaktorSchoolBaseServerException)
        {
			// Devolvemos la excepción en la respuesta
			return ResponseEntity.badRequest().body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
        catch (Exception exception)
        {
            // Creamos la excepción genérica
            ReaktorSchoolBaseServerException reaktorSchoolBaseServerException = 
                new ReaktorSchoolBaseServerException(Constants.ERROR_GENERICO_CODE, Constants.ERROR_GENERICO_MESSAGE, exception);

            // Logueamos el error
            log.error("Error generico al obtener el curso académico seleccionado: " + exception.getMessage(), exception);

            // Devolvemos la excepción genérica
            return ResponseEntity.status(Constants.ERROR_GENERICO_CODE).body(reaktorSchoolBaseServerException.getBodyExceptionMessage());
        }
    }
}
