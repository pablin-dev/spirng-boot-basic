package sistemas.online.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author pinnocenti
 */
@Entity
public class Sistema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    
    private int pablin;

    
    private String name;
    private String description;

    // JPA
    /**
     *
     */
    public Sistema() {
    }

    /**
     *
     * @param name
     * @param description
     */
    public Sistema(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     *
     * @param id
     * @param name
     * @param description
     */
    public Sistema(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sistema{" + "id=" + id + ", name=" + name + ", description=" + description + '}';
    }

    public int getPablin() {
        return pablin;
    }

    
    
}
