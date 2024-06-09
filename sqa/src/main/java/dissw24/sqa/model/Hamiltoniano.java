package dissw24.sqa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // Expandir restricciones y multiplicar por su lambda
        for (Ecuacion restriccion : restricciones) {
            int lambda = restriccion.getLambda();
            List<Sumando> sumandos = restriccion.getSumandos();
            int constante = restriccion.getConstante();

            for (int i = 0; i < sumandos.size(); i++) {
                Sumando sumando1 = sumandos.get(i);
                String keyCuadratico = "x" + sumando1.getIndex() + "^2";
                terminos.put(keyCuadratico, terminos.getOrDefault(keyCuadratico, 0) + lambda * sumando1.getFactor() * sumando1.getFactor());

                for (int j = i + 1; j < sumandos.size(); j++) {
                    Sumando sumando2 = sumandos.get(j);
                    String keyCruzado = "x" + sumando1.getIndex() + "x" + sumando2.getIndex();
                    terminos.put(keyCruzado, terminos.getOrDefault(keyCruzado, 0) + 2 * lambda * sumando1.getFactor() * sumando2.getFactor());
                }

                String keyLineal = "x" + sumando1.getIndex();
                terminos.put(keyLineal, terminos.getOrDefault(keyLineal, 0) - 2 * lambda * sumando1.getFactor() * constante);
                terminos.put(keyCuadratico, terminos.getOrDefault(keyCuadratico, 0) + lambda * sumando1.getFactor() * sumando1.getFactor());
            }

            String keyConstante = "constante";
            terminos.put(keyConstante, terminos.getOrDefault(keyConstante, 0) + lambda * constante * constante);
        }

        // Convertir términos lineales a términos cuadráticos
        for (String key : new ArrayList<>(terminos.keySet())) {
            if (key.matches("x\\d+$")) {
                int index = Integer.parseInt(key.substring(1));
                String keyCuadratico = "x" + index + "^2";
                int factor = terminos.get(key);
                terminos.put(keyCuadratico, terminos.getOrDefault(keyCuadratico, 0) + factor);
                terminos.remove(key);
            }
        }

        // Agrupar términos similares
        Map<String, Integer> terminosAgrupados = new HashMap<>();
        for (Map.Entry<String, Integer> entry : terminos.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            terminosAgrupados.put(key, terminosAgrupados.getOrDefault(key, 0) + value);
        }

        // Crear el resultado del Hamiltoniano
        StringBuilder hamiltoniano = new StringBuilder();
        for (Map.Entry<String, Integer> entry : terminosAgrupados.entrySet()) {
            if (entry.getValue() != 0 && !entry.getKey().equals("constante")) {
                hamiltoniano.append(entry.getValue()).append(entry.getKey()).append(" + ");
            }
        }

        // Eliminar el último " + "
        if (hamiltoniano.length() > 0) {
            hamiltoniano.setLength(hamiltoniano.length() - 3);
        }

        return hamiltoniano.toString();
    }

    public int[][] calcularMatrizTriangular() {
        List<Sumando> sumandos = new ArrayList<>();

        // Obtener todos los sumandos del Hamiltoniano
        for (Ecuacion ecuacion : ecuaciones) {
            sumandos.addAll(ecuacion.getSumandos());
        }

        // Determinar el tamaño de la matriz triangular
        int size = 0;
        for (Sumando sumando : sumandos) {
            size = Math.max(size, sumando.getIndex() + 1);
        }

        int[][] matrizTriangular = new int[size][size];

        String hamiltonianoStr = this.calcularHamiltoniano();
        Pattern pattern = Pattern.compile("([+-]?\\d+)(x\\d+)(x\\d+)?(\\^2)?");
        Matcher matcher = pattern.matcher(hamiltonianoStr);

        while (matcher.find()) {
            int factor = Integer.parseInt(matcher.group(1));
            int index1 = Integer.parseInt(matcher.group(2).substring(1));
            int index2 = matcher.group(3) != null ? Integer.parseInt(matcher.group(3).substring(1)) : index1;

            if (index1 <= index2) {
                matrizTriangular[index1][index2] += factor;
            } else {
                matrizTriangular[index2][index1] += factor;
            }
        }

        return matrizTriangular;
    }
    
    @Override
    public String toString() {
        return calcularHamiltoniano();
    }
}
