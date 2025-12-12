package es.iesjandula.reaktor.school_base_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Clase principal que inicia la aplicación Spring Boot.
 * 
 * <p>Esta clase es la entrada principal de la aplicación y se encarga de configurar y ejecutar la aplicación.</p>
 */
/**
 * Componente que indica el paquete base para la escaneo de componentes.
 * 
 * <p>Este componente se encarga de escanear los paquetes base para la aplicación y de buscar los componentes que se encuentran en ellos.</p>
*/
@SpringBootApplication
@ComponentScan(basePackages = {"es.iesjandula"})
public class ReaktorSchoolBaseServerApplication
{
	/**
	 * Método principal que inicia la aplicación Spring Boot.
	 * 
	 * @param args Argumentos de la línea de comandos.
	 */
	public static void main(String[] args) 
	{
		SpringApplication.run(ReaktorSchoolBaseServerApplication.class, args);
	}

}
