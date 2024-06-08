package dissw24.sqa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hamiltoniano implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Ecuacion> ecuaciones = new ArrayList<>();
    private Suma suma;

    public void add(Ecuacion equation) {
        this.ecuaciones.add(equation);
    }

    public Suma getSuma() {
        return suma;
    }

    public static Hamiltoniano defecto() {
        Cuadrado c1 = new Cuadrado();
        c1.setFactor(-7);
        c1.setIndex(0);

        Cuadrado c2 = new Cuadrado();
        c2.setFactor(-15);
        c2.setIndex(1);

        Cuadrado c3 = new Cuadrado();
        c3.setFactor(10);
        c3.setIndex(2);

        Doble d1 = new Doble();
        d1.setFactor(20);
        d1.setIndex(0);
        d1.setIndexB(1);

        Doble d2 = new Doble();
        d2.setFactor(12);
        d2.setIndex(1);
        d2.setIndexB(2);

        Hamiltoniano h = new Hamiltoniano();
        h.suma.add(c1, c2, c3, d1, d2);

        return h;
    }
}