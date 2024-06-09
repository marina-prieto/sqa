package dissw24.sqa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Suma extends Sumando implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Sumando> sumandos = new ArrayList<>();

    public void add(Sumando sumando) {
        this.sumandos.add(sumando);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Sumando sumando : sumandos) {
            sb.append(sumando.toString()).append(" + ");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 3); // Eliminar el último " + "
        }
        return sb.toString();
    }
}
