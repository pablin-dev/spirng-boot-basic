/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import sistemas.online.WebApplication;
import sistemas.online.model.Sistema;
import sistemas.online.model.SistemaRepository;

/**
 *
 * @author pinnocenti
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebApplication.class)
@WebAppConfiguration
public class SistemasRestControllerTest {

    private final static Logger LOGGER = LogManager.getLogger(SistemasRestControllerTest.class);

    // Seteamos el encoding UTF-8
    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("UTF-8"));

    // Mocking
    private MockMvc mockMvc;

    // Helper
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    // Lista de Sistemas que usamos para validar
    private final List<Sistema> sistemasList = new ArrayList<>();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SistemaRepository sistemaRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
        Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    /**
     *
     * @throws Exception
     */
    @Before
    public void setup() throws Exception {
        // Mock de la aplicacion
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        // limpiamos la tabla
        this.sistemaRepository.deleteAllInBatch();
        
        // salvamos los datos en la tabla SISTEMA y realizamos una copia en una lista privada para comparar
        this.sistemasList.add(this.sistemaRepository.save(new Sistema("Debian", "Universal operating system")));
        this.sistemasList.add(this.sistemaRepository.save(new Sistema("Windows", "Run from it")));
        this.sistemasList.add(this.sistemaRepository.save(new Sistema("Linux Mint", "rocks")));
    }

    //CRUD TESTS
    
    /**
     *
     * @throws Exception
     */
        
    @Test
    public void readSingleSistema() throws Exception {
        LOGGER.debug("ID del sistema: "+ this.sistemasList.get(0).getId());
        //GET /sistema/{id}
        ResultActions result = mockMvc.perform(get("/sistema/" + this.sistemasList.get(0).getId()));
        
        //debe responder Code 200
        result.andExpect(status().isOk());
        // application/json
        result.andExpect(content().contentType(contentType));
        // debe ser igual que el primer elemento en la tabla
        result.andExpect(jsonPath("$.id", is(this.sistemasList.get(0).getId().intValue())));
        result.andExpect(jsonPath("$.name", is("Debian")));
        result.andExpect(jsonPath("$.description", is("Universal operating system")));
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void readSistemas() throws Exception {
        mockMvc.perform(get("/sistema/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(this.sistemasList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].name", is("Debian")))
                .andExpect(jsonPath("$[0].description", is("Universal operating system")))
                .andExpect(jsonPath("$[1].id", is(this.sistemasList.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].name", is("Windows")))
                .andExpect(jsonPath("$[1].description", is("Run from it")))
                .andExpect(jsonPath("$[2].id", is(this.sistemasList.get(2).getId().intValue())))
                .andExpect(jsonPath("$[2].name", is("Linux Mint")))
                .andExpect(jsonPath("$[2].description", is("rocks")));
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void addSistema() throws Exception {
        String sistemaJson = json(new Sistema("Android", "No me gusta"));
        ResultActions result = this.mockMvc.perform(post("/sistema/").contentType(contentType).content(sistemaJson));
        result.andExpect(status().isCreated());
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    public void addSistemaAlreadyExists() throws Exception {
        String sistemaJson = json(sistemasList.get(0));
        ResultActions result = this.mockMvc.perform(post("/sistema/").contentType(contentType).content(sistemaJson));
        result.andExpect(status().isConflict());
    }
    
    //CRUD TESTS

    /**
     *
     * @throws Exception
     */
        @Test
    public void deleteSistemaOk() throws Exception {
        ResultActions result = this.mockMvc.perform(delete("/sistema/"+this.sistemasList.get(0).getId()));
        result.andExpect(status().isOk());
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    public void deleteSistemaFail() throws Exception {
        ResultActions result = this.mockMvc.perform(delete("/sistema/4"));
        result.andExpect(status().isNotFound());
    }
    
     //CRUD TESTS

    /**
     *
     * @throws Exception
     */
        @Test
    public void updateSistemaOk() throws Exception {
        String sistemaJson = json(this.sistemasList.get(0));
        ResultActions result = this.mockMvc.perform(put("/sistema/"+this.sistemasList.get(0).getId()).contentType(contentType).content(sistemaJson));
        result.andExpect(status().isOk());
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    public void updateSistemaFailWithOutId() throws Exception {
        String sistemaJson = json(new Sistema("Android", "No me gusta"));
        ResultActions result = this.mockMvc.perform(put("/sistema/1").contentType(contentType).content(sistemaJson));
        result.andExpect(status().isNotFound());
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    public void updateSistemaFailIdNotExist() throws Exception {
        Sistema sistema= new Sistema("Android", "No me gusta");
        sistema.setId(4L);
        String sistemaJson = json(sistema);
        ResultActions result = this.mockMvc.perform(put("/sistema/4").contentType(contentType).content(sistemaJson));
        result.andExpect(status().isConflict());
    }

    /**
     *
     * @param sistema
     * @return
     * @throws IOException
     */
    protected String json(Sistema sistema) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(sistema, MediaType.APPLICATION_JSON, mockHttpOutputMessage);        
        return mockHttpOutputMessage.getBodyAsString(Charset.forName("UTF-8"));
    }
}
