package dissw24.sqa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Suma implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Sumando> sumandos;

    public Suma() {
        this.sumandos = new ArrayList<>();
    }

    public void add(Sumando... sumandos) {
        for (Sumando s : sumandos) {
            this.sumandos.add(s);
        }
    }
}