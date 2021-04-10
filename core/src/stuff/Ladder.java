package stuff;

public class Ladder extends Stuff {

    public Ladder(int id, int beginCell, int endCell) {
        super(id, beginCell, endCell);
    }

    @Override
    public int getStepsNumber() {
        return getEndCell() - getBeginCell();
    }
}
