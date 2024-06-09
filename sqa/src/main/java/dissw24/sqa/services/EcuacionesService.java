package dissw24.sqa.services;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import dissw24.sqa.model.Ecuacion;
import dissw24.sqa.model.Hamiltoniano;

@Service
public class EcuacionesService {

    public Hamiltoniano generarHamiltoniano(List<Map<String, Object>> ecuaciones) {
        Hamiltoniano hamiltoniano = new Hamiltoniano();
        for (Map<String, Object> ecuacionMap : ecuaciones) {
            String eq = (String) ecuacionMap.get("eq");
            int lambda = (int) ecuacionMap.get("lambda");
            Ecuacion ecuacion = new Ecuacion(eq, lambda);
            if (eq.contains("=")) {
                hamiltoniano.addRestriccion(ecuacion);
            } else {
                hamiltoniano.setFuncionObjetivo(ecuacion);
            }
            hamiltoniano.addEcuacion(ecuacion);
        }
        return hamiltoniano;
    }
}
