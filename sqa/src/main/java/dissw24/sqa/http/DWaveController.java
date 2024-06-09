package dissw24.sqa.http;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dissw24.sqa.services.DWService;
import dissw24.sqa.services.EcuacionesService;
import dissw24.sqa.so.EjecutorPython;

@RestController
@RequestMapping("dwave")
@CrossOrigin("*")
public class DWaveController extends CommonController {

    @Autowired
    private EcuacionesService ecuacionesService;
    
    @Autowired
    private DWService dwaveService;

    @PutMapping("/generarCodigo")
    public String generarCodigo(HttpServletRequest req, @RequestBody List<Map<String, Object>> ecuaciones) {
        String token = super.validarPeticion(req);
        
        try {
            int[][] matriz = this.ecuacionesService.generarMatrizTriangular(ecuaciones);
            String codigo = this.dwaveService.generarCodigo(matriz);
            String fileName = super.saveCodigo(token, codigo);
            return "El archivo generado se encuentra disponible en " + fileName;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error " + e.getLocalizedMessage());
        }
    }

    @PutMapping("/ejecutarCodigo")
    public String ejecutarCodigo(HttpServletRequest req, @RequestBody List<Map<String, Object>> ecuaciones) {
        String token = super.validarPeticion(req);
        try {
            String codigoFileName = super.getName(token) + "dwavefile.py";
            EjecutorPython ejecutor = new EjecutorPython();
            String resultado = ejecutor.ejecuta(codigoFileName);
            return resultado;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error " + e.getLocalizedMessage());
        }
    }
}
