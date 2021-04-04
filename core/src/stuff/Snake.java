package stuff;

public class Snake extends Stuff{
    public Snake(int beginCell, int endCell) {
        super(beginCell, endCell);
    }

    @Override
    public int getStepsNumber() {
        // move backwards
        return getBeginCell() - getEndCell();
    }
}
