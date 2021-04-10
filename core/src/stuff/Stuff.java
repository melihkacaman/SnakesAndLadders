package stuff;

public abstract class Stuff {
    private int id;
    private int beginCell;
    private int endCell;

    public Stuff(int id,int beginCell, int endCell){
        this.id = id;
        this.beginCell = beginCell;
        this.endCell = endCell;
    }

    public int getBeginCell() {
        return beginCell;
    }

    public int getEndCell() {
        return endCell;
    }

    public int getId() {
        return id;
    }

    public abstract int getStepsNumber();
}
