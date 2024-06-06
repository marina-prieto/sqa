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

@RestController
@RequestMapping("dwave")
@CrossOrigin("*")
public class DWaveController extends CommonController{

	@Autowired
	private DWService service;
	
	@PutMapping("/generarCodigo")
	public String generarCodigo(HttpServletRequest req, @RequestBody int[][] matriz){
		String token = super.validarPeticion(req);
		
		try {
			String codigo = this.service.generarCodigo(matriz);
			super.saveCodigo(token, codigo);
			return codigo;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error " + e.getLocalizedMessage());
		}
	}
	
	@PutMapping("/ejecutarCodigo")
	public String ejecutararCodigo(HttpServletRequest req, List<Map<String, Object>> ecuaciones){
		String token = super.validarPeticion(req);
		try {
			Hamiltoniano h = this.ecuacionesService.generarHamiltoniano(token, ecuaciones);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error " + e.getLocalizedMessage());
		}
	}
}
