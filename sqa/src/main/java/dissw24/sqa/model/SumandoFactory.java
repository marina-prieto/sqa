package dissw24.sqa.model;

public class SumandoFactory {

    public static Sumando crear(String clave, int factor) {
        if (clave.contains("x") && clave.contains("^2")) {
            int index = Integer.parseInt(clave.split("x")[1].split("\\^2")[0]);
            Cuadrado cuadrado = new Cuadrado();
            cuadrado.setIndex(index);
            cuadrado.setFactor(factor);
            return cuadrado;
        } else if (clave.contains("x") && clave.contains("x")) {
            String[] indices = clave.split("x");
            int index1 = Integer.parseInt(indices[1]);
            int index2 = Integer.parseInt(indices[2]);
            CrossTerm crossTerm = new CrossTerm();
            crossTerm.setIndex1(index1);
            crossTerm.setIndex2(index2);
            crossTerm.setFactor(factor);
            return crossTerm;
        } else {
            int index = Integer.parseInt(clave.split("x")[1]);
            Doble doble = new Doble();
            doble.setIndex(index);
            doble.setFactor(factor);
            return doble;
        }
    }
}
