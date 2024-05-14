package sistemas.online.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Remapea a "/sistemas"
 * @author pinnocenti
 */
@Controller
@RequestMapping("/")
public class IndexController {

    /**
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getIndexPage() {
        return "sistemas";
    }

}
