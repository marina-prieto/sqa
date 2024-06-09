package dissw24.sqa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Suma implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Sumando> sumandos = new ArrayList<>();

    public void add(Sumando sumando) {
        this.sumandos.add(sumando);
    }

    public void simplificar() {
        Map<String, Integer> terminos = new HashMap<>();

        for (Sumando sumando : sumandos) {
            String clave = sumando.toStringSinFactor();
            terminos.put(clave, terminos.getOrDefault(clave, 0) + sumando.getFactor());
        }

        sumandos.clear();
        for (Map.Entry<String, Integer> entry : terminos.entrySet()) {
            String clave = entry.getKey();
            int factor = entry.getValue();
            Sumando sumando = SumandoFactory.crear(clave, factor);
            sumandos.add(sumando);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Sumando sumando : sumandos) {
            if (sb.length() > 0) {
                sb.append(" + ");
            }
            sb.append(sumando.toString());
        }
        return sb.toString();
    }
}
