package sistemas.online.controllers;

import java.util.List;
// Import log4j classes.
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import sistemas.online.model.Sistema;
import sistemas.online.model.SistemaRepository;

/**
 *
 * @author pinnocenti
 */
@RestController
public class SistemaRestController {

    private final static Logger LOGGER = LogManager.getLogger(SistemaRestController.class);

    @Autowired
    private SistemaRepository sistemaRepository;

    @Autowired
    //Constructor del controller
    SistemaRestController(SistemaRepository sistemaRepository) {
        this.sistemaRepository = sistemaRepository;
    }

    /**
     * Obtiene todos los sistemas
     *
     * @return
     */
    @RequestMapping(value = "/sistema/", method = RequestMethod.GET)
    public ResponseEntity<List<Sistema>> readSistemas() {
        // Si se desea paginado (pagina 0, 10 resultados): Page<Sistema> pageResult = sistemaRepository.findAll(new PageRequest(0, 10));

        List<Sistema> sistemas = sistemaRepository.findAll();
        if (sistemas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(sistemas, HttpStatus.OK);
    }

    /**
     * Obtiene un unico sistema a partir del id
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/sistema/{id}", method = RequestMethod.GET)
    public ResponseEntity<Sistema> getSistema(@PathVariable("id") long id) {
        LOGGER.debug("Fetching Sistema with id " + id);
        // Tambien puede ser : sistemaRepository.findOne(id);        
        Sistema sistema = sistemaRepository.findById(id);
        if (sistema == null) {
            LOGGER.debug("Sistema with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sistema, HttpStatus.OK);
    }

    /**
     * Crea un sistema
     *
     * @param sistema
     * @param ucBuilder
     * @return
     */
    @RequestMapping(value = "/sistema/", method = RequestMethod.POST)
    public ResponseEntity<Void> createSistema(@RequestBody Sistema sistema, UriComponentsBuilder ucBuilder) {
        LOGGER.info("Creating Sistema " + sistema.getName());

        Sistema sistemaBuscado = sistemaRepository.findByName(sistema.getName());
        if (sistemaBuscado != null) {
            LOGGER.info("A Sistema with name " + sistema.getName() + " already exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        sistemaRepository.save(sistema);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/sistema/{id}").buildAndExpand(sistema.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Update un sistema
     *
     * @param sistema
     * @param ucBuilder
     * @return
     */
    @RequestMapping(value = "/sistema/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateSistema(@RequestBody Sistema sistema, UriComponentsBuilder ucBuilder) {
        LOGGER.info("Updating Sistema " + sistema.getName());
        if (sistema.getId() != null) {
            Sistema sistemaBuscado = sistemaRepository.findById(sistema.getId());
            LOGGER.info(sistemaBuscado);
            if (sistemaBuscado != null) {
                LOGGER.info("A Sistema with name " + sistema.getName() + " does exist");
                sistemaBuscado.setName(sistema.getName());
                sistemaBuscado.setDescription(sistema.getDescription());
                sistemaRepository.save(sistemaBuscado);
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(ucBuilder.path("/sistema/{id}").buildAndExpand(sistema.getId()).toUri());
                return new ResponseEntity<>(headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Borra un Sistema
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/sistema/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Sistema> deleteSistema(@PathVariable("id") long id) {
        LOGGER.info("Fetching & Deleting Sistema with id " + id);

        Sistema sistema = sistemaRepository.findById(id);
        if (sistema == null) {
            LOGGER.info("Unable to delete Sistema with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        sistemaRepository.delete(sistema);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
