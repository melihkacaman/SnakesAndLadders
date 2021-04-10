package stuff;

public class Snake extends Stuff{
    public Snake(int id, int beginCell, int endCell) {
        super(id, beginCell, endCell);
    }

    @Override
    public int getStepsNumber() {
        // move backwards
        return getBeginCell() - getEndCell();
    }
}
