package dissw24.sqa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        Suma hamiltoniano = new Suma();

        // Agregar la función objetivo
        if (funcionObjetivo != null) {
            for (Sumando sumando : funcionObjetivo.getSumandos()) {
                Cuadrado cuadrado = new Cuadrado();
                cuadrado.setFactor(sumando.getFactor() * sumando.getFactor());
                cuadrado.setIndex(sumando.getIndex());
                hamiltoniano.add(cuadrado);
            }
        }

        // Agregar las restricciones multiplicadas por sus lambdas
        for (Ecuacion restriccion : restricciones) {
            int lambda = restriccion.getLambda();
            for (Sumando sumando : restriccion.getSumandos()) {
                Doble doble = new Doble();
                doble.setFactor(sumando.getFactor() * lambda);
                doble.setIndex(sumando.getIndex());
                hamiltoniano.add(doble);
            }
            Simple constante = new Simple();
            constante.setFactor(restriccion.getConstante() * lambda);
            constante.setIndex(-1); // Indicador de término constante
            hamiltoniano.add(constante);
        }

        return hamiltoniano.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append(calcularHamiltoniano());
        return sb.toString();
    }
}
