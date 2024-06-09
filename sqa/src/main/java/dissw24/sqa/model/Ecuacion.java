package dissw24.sqa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ecuacion implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Sumando> sumandos;
    private int lambda;
    private int constante; // Término independiente si existe

    public Ecuacion(String eq, int lambda) {
        this.sumandos = new ArrayList<>();
        this.lambda = lambda;
        this.constante = 0;
        parseEquation(eq);
    }

    private void parseEquation(String eq) {
        // Separar la ecuación en la parte izquierda y derecha del "=" si existe
        String[] sides = eq.split("=");
        String leftSide = sides[0].trim();
        String rightSide = sides.length > 1 ? sides[1].trim() : "0";

        // Parsear la parte izquierda
        String[] terms = leftSide.split("(?=[+-])");
        for (String term : terms) {
            term = term.trim();
            if (term.isEmpty() || term.equals("+") || term.equals("-")) continue;

            // Verificar si el término contiene 'x'
            if (term.contains("x")) {
                String[] parts = term.split("x");
                int factor = 1;
                int index = 0;
                
                // Manejo de casos donde el factor no está explícito (ej: +x1 o -x1)
                if (!parts[0].trim().isEmpty() && !parts[0].trim().equals("+") && !parts[0].trim().equals("-")) {
                    factor = Integer.parseInt(parts[0].trim().replace("+", ""));
                } else if (parts[0].trim().equals("-")) {
                    factor = -1;
                }

                // Manejo del índice, debe existir después de 'x'
                if (parts.length > 1 && !parts[1].trim().isEmpty()) {
                    index = Integer.parseInt(parts[1].trim());
                }

                Simple sumando = new Simple();
                sumando.setFactor(factor);
                sumando.setIndex(index);
                this.add(sumando);
            } else {
                // Es un término constante
                this.constante += Integer.parseInt(term);
            }
        }

        // Parsear la parte derecha si existe
        if (!rightSide.equals("0")) {
            int constantTerm = Integer.parseInt(rightSide);
            this.constante -= constantTerm; // Mover el término constante al lado izquierdo
        }
    }

    public void add(Sumando sumando) {
        this.sumandos.add(sumando);
    }

    public List<Sumando> getSumandos() {
        return sumandos;
    }

    public int getLambda() {
        return lambda;
    }

    public int getConstante() {
        return constante;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Sumando sumando : sumandos) {
            sb.append(sumando.toString()).append(" ");
        }
        if (constante != 0) {
            sb.append(" + ").append(constante);
        }
        sb.append("= ").append(lambda);
        return sb.toString();
    }
}
