package sistemas.online;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 *
 * @author pinnocenti
 */
public class SistemaInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     *
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SistemaConfiguration.class};
    }

    /**
     *
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     *
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
        Filter[] singleton = {new CORSFilter()};
        return singleton;
    }

}
