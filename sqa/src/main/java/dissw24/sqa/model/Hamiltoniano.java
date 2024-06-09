package dissw24.sqa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hamiltoniano implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Ecuacion> ecuaciones = new ArrayList<>();
    private List<Ecuacion> restricciones = new ArrayList<>();
    private Ecuacion funcionObjetivo;

    public void addEcuacion(Ecuacion ecuacion) {
        this.ecuaciones.add(ecuacion);
    }

    public void setFuncionObjetivo(Ecuacion funcionObjetivo) {
        this.funcionObjetivo = funcionObjetivo;
    }

    public void addRestriccion(Ecuacion restriccion) {
        this.restricciones.add(restriccion);
    }

    public String calcularHamiltoniano() {
        Map<String, Integer> terminos = new HashMap<>();

        // Función a minimizar
        if (funcionObjetivo != null) {
            for (Sumando sumando : funcionObjetivo.getSumandos()) {
                String key = "x" + sumando.getIndex() + "^2";
                terminos.put(key, terminos.getOrDefault(key, 0) + 2 * funcionObjetivo.getLambda() * sumando.getFactor());
            }
        }

        // Restricciones
        for (Ecuacion restriccion : restricciones) {
            int lambda = restriccion.getLambda();
            List<Sumando> sumandos = restriccion.getSumandos();
            int constante = restriccion.getConstante();

            // Expandir la restricción al cuadrado
            for (int i = 0; i < sumandos.size(); i++) {
                Sumando sumando1 = sumandos.get(i);

                // Términos cuadráticos
                String keyCuadratico = "x" + sumando1.getIndex() + "^2";
                terminos.put(keyCuadratico, terminos.getOrDefault(keyCuadratico, 0) + lambda * sumando1.getFactor() * sumando1.getFactor());

                for (int j = i + 1; j < sumandos.size(); j++) {
                    Sumando sumando2 = sumandos.get(j);
                    String keyCruzado = "x" + sumando1.getIndex() + "x" + sumando2.getIndex();
                    terminos.put(keyCruzado, terminos.getOrDefault(keyCruzado, 0) + 2 * lambda * sumando1.getFactor() * sumando2.getFactor());
                }

                // Términos lineales convertidos a términos cuadráticos
                String keyLineal = "x" + sumando1.getIndex();
                int existingValue = terminos.getOrDefault(keyLineal, 0);
                terminos.put(keyLineal, existingValue - lambda * sumando1.getFactor());
                terminos.put(keyCuadratico, terminos.getOrDefault(keyCuadratico, 0) + lambda * sumando1.getFactor() * sumando1.getFactor());
            }

            // Término constante
            String keyConstante = "constante";
            terminos.put(keyConstante, terminos.getOrDefault(keyConstante, 0) + lambda * constante * constante);
        }

        // Crear el resultado del Hamiltoniano
        StringBuilder hamiltoniano = new StringBuilder();
        for (Map.Entry<String, Integer> entry : terminos.entrySet()) {
            if (entry.getValue() != 0) {
                hamiltoniano.append(entry.getValue()).append(entry.getKey()).append(" + ");
            }
        }

        // Eliminar el último " + "
        if (hamiltoniano.length() > 0) {
            hamiltoniano.setLength(hamiltoniano.length() - 3);
        }

        return hamiltoniano.toString();
    }

    @Override
    public String toString() {
        return calcularHamiltoniano();
    }
}
