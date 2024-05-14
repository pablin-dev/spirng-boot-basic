package sistemas.online.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author pinnocenti
 */
public interface SistemaRepository extends JpaRepository<Sistema, Long> {
    
    /**
     *
     * @param name
     * @return
     */
    // Se puede hacer como Sistema findByNameLike(String name), pero mejor explicitar la query
    @Query("select u from Sistema u where u.name like :name")
    Sistema findByName(@Param("name") String name);

    /**
     *
     * @param id
     * @return
     */
    @Query("select u from Sistema u where u.id = ?1")
    Sistema findById(long idSistema);

}
