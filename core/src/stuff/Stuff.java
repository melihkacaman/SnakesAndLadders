package stuff;

public abstract class Stuff {
    private int beginCell;
    private int endCell;

    public Stuff(int beginCell, int endCell){
        this.beginCell = beginCell;
        this.endCell = endCell;
    }

    public int getBeginCell() {
        return beginCell;
    }

    public int getEndCell() {
        return endCell;
    }

    public abstract int getStepsNumber();
}
