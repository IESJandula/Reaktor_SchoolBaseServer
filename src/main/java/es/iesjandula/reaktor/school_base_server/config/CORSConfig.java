package es.iesjandula.reaktor.school_base_server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de CORS para el servidor de base de datos de Reaktor.
 * 
 * <p>Esta clase se utiliza para habilitar las llamadas CORS desde diferentes dominios
 * permitiendo que las peticiones HTTP se realicen desde diferentes fuentes.</p>
 */
@Configuration
@EnableWebMvc
public class CORSConfig implements WebMvcConfigurer
{
	/** URL permitidas de CORS */
	@Value("${reaktor.urlCors}")
	private String[] urlCors;
	
	/**
	 * Método para agregar las configuraciones de CORS a la aplicación.
	 * @param registry Información del Cors Registry.
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry)
	{
		registry.addMapping("/**")
				.allowedOrigins(urlCors)
				.allowedMethods("GET","POST","PUT","DELETE", "OPTIONS")
				.allowedHeaders("*");
	}
}
