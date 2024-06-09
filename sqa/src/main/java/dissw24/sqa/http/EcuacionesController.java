package dissw24.sqa.http;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import dissw24.sqa.model.Hamiltoniano;
import dissw24.sqa.services.EcuacionesService;

@RestController
@RequestMapping("ecuaciones")
@CrossOrigin("*")
public class EcuacionesController extends CommonController {

    @Autowired
    private EcuacionesService service;

    @PutMapping("/generarHamiltoniano")
    public String generarHamiltoniano(HttpServletRequest req, @RequestBody List<Map<String, Object>> ecuaciones) {
        String token = super.validarPeticion(req);

        try {
            Hamiltoniano hamiltoniano = service.generarHamiltoniano(ecuaciones);
            return "Hamiltoniano generado correctamente: " + hamiltoniano.toString();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error generando Hamiltoniano: " + e.getMessage());
        }
    }
}
