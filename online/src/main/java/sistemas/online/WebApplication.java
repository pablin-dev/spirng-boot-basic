/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.online;

import java.util.Arrays;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import sistemas.online.model.Sistema;
import sistemas.online.model.SistemaRepository;
import org.h2.server.web.WebServlet;

/**
 * SpringBootApplication
 *
 * @author pinnocenti
 */
@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class WebApplication extends SpringBootServletInitializer {

    private final static Logger LOGGER = LogManager.getLogger(WebApplication.class);

    @Bean
    // Instanciamos la base con valores por default (carga inicial del sistema)
    CommandLineRunner init(SistemaRepository sistemaRepository) {
        LOGGER.info("Instanciando algunos datos demo");
        List<String> lista = Arrays.asList("Debian|Universal operating system,Windows|Mejor huye,Linux Mint|Siiii :)".split(","));
        lista.forEach(
                a -> {

                    String[] sistemaInfo = a.split("\\|");
                    sistemaRepository.save(new Sistema(sistemaInfo[0], sistemaInfo[1]));
                });
        return (evt) -> Arrays.asList(lista.toArray());

    }

    //H2 console via web
    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        return registration;
    }

    /**
     *
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class);
    }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebApplication.class, args);
    }
}
