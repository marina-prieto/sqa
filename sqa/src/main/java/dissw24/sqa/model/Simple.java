package dissw24.sqa.model;

public class Simple extends Sumando {

    @Override
    public String toString() {
        if (index == -1) { // Término constante
            return String.valueOf(factor);
        }
        return super.toString();
    }
}
