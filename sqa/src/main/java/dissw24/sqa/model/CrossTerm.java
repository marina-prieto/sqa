package dissw24.sqa.model;

public class CrossTerm extends Sumando {

    private int index2;

    public void setIndex1(int index1) {
        this.index = index1;
    }

    public void setIndex2(int index2) {
        this.index2 = index2;
    }

    public int getIndex1() {
        return index;
    }
    
    public int getIndex2() {
        return index2;
    }

    @Override
    public String toStringSinFactor() {
        return "x" + index + "x" + index2;
    }
}