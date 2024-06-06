package dissw24.sqa.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import dissw24.sqa.model.Ecuacion;
import dissw24.sqa.model.Hamiltoniano;

@Service
public class EcuacionesService {

	public String contarIncognitas(String eq) {
		int cont = 0;
		for (int i = 0; i < eq.length(); i++)
			if (eq.charAt(i) == 'x')
				cont++;
		return "Hay " + cont + " incógnitas";
	}
	
	public int calcular(String eq, List<Integer> x) {
		//hacer el metodo que haga la cuenta. pilla la ecuacion y reemplaza cada x (x1, x2, etc) con los x de la lista de integers
		int cont = 0;
		for (int i = 0; i < eq.length(); i++)
			if (eq.charAt(i) == 'x')
				cont++;
		return cont;
	}
	
	public Hamiltoniano generarHamiltoniano(List<Map<String, Object>> ecuaciones) {
		Hamiltoniano h = new Hamiltoniano();
		for (Map<String, Object> ecuacion : ecuaciones) {
			String eq = (String) ecuacion.get("eq");
			//int lambda = (int) ecuacion.get("lambda");
			Ecuacion equation = new Ecuacion(eq);
			//equation.setLambda(lambda);
			h.add(equation);
		}
		return Hamiltoniano.defecto();
	}
}
 