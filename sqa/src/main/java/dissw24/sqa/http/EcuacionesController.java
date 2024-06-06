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
import dissw24.sqa.services.EcuacionesService;

@RestController
@RequestMapping("ecuaciones")
@CrossOrigin("*")
public class EcuacionesController extends CommonController{

	@Autowired
	private EcuacionesService service;
	
	@GetMapping("/recibir")
	public String recibir(@RequestParam String eq ) {
		if (eq.trim().length() == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
					"La ecuación no puede estar vacía");
		}
		return this.service.contarIncognitas(eq);
	}
	
	@SuppressWarnings("unused")
	@PutMapping("/recibir")
	public String recibirPorPut(@RequestBody Map<String, Object> info) {
		String eq = info.get("eq").toString();
		int lambda = (int) info.get("lambda");
		
		return this.recibir(eq);
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@PutMapping("/calcular")
	public int calcular(@RequestBody Map<String, Object> info) {
		String eq = info.get("eq").toString();
		int lambda = (int) info.get("lambda");
		List<Integer> x = (List<Integer>) info.get("x");
		
		return this.service.calcular(eq, x);
	}
	
	@PutMapping("/generarHamiltoniano")
	public Hamiltoniano generarHamiltoniano(HttpServletRequest req, @RequestBody List<Map<String, Object>> ecuaciones){
		String token = super.validarPeticion(req);
		
		Hamiltoniano h = this.service.generarHamiltoniano(ecuaciones);
		try {
			super.save(token, h);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error de disco: " + e.getMessage());
		}
		return h;
	}
}
