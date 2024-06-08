package dissw24.sqa.model;

import java.io.Serializable;

public class Sumando implements Serializable {

    private static final long serialVersionUID = 1L;

    protected int factor;
    protected int index;

    public void setFactor(int factor) {
        this.factor = factor;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int calcular(int x) {
        return this.factor * x;
    }
}