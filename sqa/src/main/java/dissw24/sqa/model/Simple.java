package dissw24.sqa.model;

public class Simple extends Sumando {

    @Override
    public String toString() {
        if (index == -1) {
            return String.valueOf(factor);
        }
        return super.toString();
    }

    @Override
    public String toStringSinFactor() {
        return "x" + index;
    }
}
