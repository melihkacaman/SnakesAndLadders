package stuff;

public class Ladder extends Stuff {

    public Ladder(int beginCell, int endCell) {
        super(beginCell, endCell);
    }

    @Override
    public int getStepsNumber() {
        return getEndCell() - getBeginCell();
    }
}
