package dissw24.sqa.http;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dissw24.sqa.model.Hamiltoniano;
import dissw24.sqa.services.DWService;
import dissw24.sqa.services.EcuacionesService;
import dissw24.sqa.so.EjecutorPython;

@RestController
@RequestMapping("dwave")
@CrossOrigin("*")
public class DWaveController extends CommonController{

	@Autowired
	private EcuacionesService ecuacionesService;
	
	@Autowired
	private DWService dwaveService;
	
	@PutMapping("/generarCodigo")
	public String generarCodigo(HttpServletRequest req, @RequestBody int[][] matriz){
		String token = super.validarPeticion(req);
		
		try {
			String codigo = this.dwaveService.generarCodigo(matriz);
			super.saveCodigo(token, codigo);
			return codigo;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error " + e.getLocalizedMessage());
		}
	}
	
	//@PutMapping("/ejecutarCodigo")
	//public Map<String, Object> ejecutarCodigo(HttpServletRequest req, @RequestBody List<Map<String, Object>> ecuaciones){
		//String token = super.validarPeticion(req);
		
		//try {
			//Hamiltoniano h = this.ecuacionesService.generarHamiltoniano(ecuaciones);
			//String hFileName = super.save(token, h);
			//MatrizTriangular mt = this.ecuacionesService.generarMatrizTriangular(hFileName);
			//String mtFileName = super.save(token, mt);
			//String codigo = this.dwaveService.generarCodigo(mtFileName);
			//String codigoFileName = super.saveCodigo(token, codigo);
			
			//EjecutorPython ejecutor = new EjecutorPython();
			//Map<String, Object> resultado = new HashMap<>();
			//resultado.put("codigo", ejecutor.ejecuta(codigoFileName));
			//return resultado;
			
		//} catch (Exception e) {
			//throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error " + e.getLocalizedMessage());
		//}
	//}
}
