package es.iesjandula.reaktor.school_base_server.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Clase de constantes utilizadas en el proyecto Espacios.
 * 
 * <p>Contiene mensajes de éxito y códigos/mensajes de error para los diferentes Espacios 
 * (Sin docencia, Fijo y Desdoble) y los cursos que los ocupan.</p>
 * 
 * <p>Estas constantes se utilizan en controladores, servicios y excepciones para
 * mantener consistencia en los mensajes y códigos de error en todo el proyecto.</p>
 */
public class Constants
{
    /******************************************/
    /*********** CURSOS ACADÉMICOS ************/
    /******************************************/

    /** Lista de cursos académicos */
    public static final List<String> CURSOS_ACADEMICOS = Arrays.asList("2025/2026", "2026/2027", "2027/2028", "2028/2029", 
                                                                       "2029/2030", "2030/2031", "2031/2032",
                                                                       "2032/2033", "2033/2034", "2034/2035",
                                                                       "2035/2036", "2036/2037", "2037/2038",
                                                                       "2038/2039", "2039/2040", "2040/2041",
                                                                       "2041/2042", "2042/2043", "2043/2044",
                                                                       "2044/2045", "2045/2046", "2046/2047",
                                                                       "2047/2048", "2048/2049", "2049/2050");
    /*******************************************/
    /*************** PROPIEDADES ***************/
    /*******************************************/
    
    /** Clave de la propiedad para el DDL Auto */
    public static final String SPRING_JPA_HIBERNATE_DDL_AUTO = "spring.jpa.hibernate.ddl-auto";

    /** Clave de la propiedad para el curso académico */
    public static final String VALOR_SPRING_JPA_HIBERNATE_DDL_AUTO = "create";

    /** Clave de la propiedad para el curso académico */
    public static final String PARAM_YAML_CURSO_ACADEMICO_SELECCIONADO = "reaktor.curso_academico";

    /** Valor de la propiedad para el curso académico */
    public static final String VALOR_PARAM_YAML_CURSO_ACADEMICO_SELECCIONADO = "2025/2026";

    /*******************************************/
    /************ ERRORES GENERICOS ************/
    /*******************************************/

    /** Código de error genérico */
    public static final Integer ERROR_GENERICO_CODE = 1;
    
    /** Mensaje de error genérico */
    public static final String ERROR_GENERICO_MESSAGE = "Error genérico";

    /*******************************************/
    /********* ERRORES EN CURSO ACADÉMICO ******/
    /*******************************************/

    /** Código de error para el curso académico nulo o vacío */
    public static final Integer ERR_CURSO_ACADEMICO_NULO_VACIO_CODE = 100;

    /** Mensaje de error para el curso académico nulo o vacío */
    public static final String ERR_CURSO_ACADEMICO_NULO_VACIO_MESSAGE = "El curso académico no puede ser nulo o vacío";

    /** Código de error para el curso académico no existe */
    public static final Integer ERR_CURSO_ACADEMICO_NO_EXISTE_CODE = 101;

    /** Mensaje de error para el curso académico no existe */
    public static final String ERR_CURSO_ACADEMICO_NO_EXISTE_MESSAGE = "El curso académico no existe";

    /*******************************************/
    /*********** ERRORES EN ESPACIOS ***********/
    /*******************************************/

    /** Código de error para el curso académico nulo o vacío  */
    public static final Integer ERR_ESPACIO_CURSO_ACADEMICO_NULO_VACIO_CODE   = 200;

    /** Mensaje de error para el curso académico nulo o vacío */
    public static final String ERR_ESPACIO_CURSO_ACADEMICO_NULO_VACIO_MESSAGE = "El curso académico no puede ser nulo o vacío";

    /** Código de error para el nombre nulo o vacío */
    public static final Integer ERR_ESPACIO_NOMBRE_NULO_VACIO_CODE = 201;

    /** Mensaje de error para el nombre nulo o vacío */
    public static final String ERR_ESPACIO_NOMBRE_NULO_VACIO_MESSAGE = "El nombre no puede ser nulo o vacío";

    /** Código de error para el espacio ya existe */
    public static final Integer ERR_ESPACIO_YA_EXISTE_EN_SIN_DOCENCIA_CODE = 202;

    /** Mensaje de error para el espacio ya existe */
    public static final String ERR_ESPACIO_YA_EXISTE_EN_SIN_DOCENCIA_MESSAGE = "El espacio ya existe en sin docencia";

    /** Código de error para el espacio ya existe en fijo */
    public static final Integer ERR_ESPACIO_YA_EXISTE_EN_FIJO_CODE = 203;

    /** Mensaje de error para el espacio ya existe en fijo */
    public static final String ERR_ESPACIO_YA_EXISTE_EN_FIJO_MESSAGE = "El espacio ya existe en fijo";

    /** Código de error para el espacio ya existe en desdoble */
    public static final Integer ERR_ESPACIO_YA_EXISTE_EN_DESDOBLE_CODE = 204;

    /** Mensaje de error para el espacio ya existe en desdoble */
    public static final String ERR_ESPACIO_YA_EXISTE_EN_DESDOBLE_MESSAGE = "El espacio ya existe en desdoble";
}
