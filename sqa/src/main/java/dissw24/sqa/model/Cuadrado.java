package dissw24.sqa.model;

public class Cuadrado extends Sumando {

    @Override
    public String toStringSinFactor() {
        return "x" + index + "^2";
    }
}