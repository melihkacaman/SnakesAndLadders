package stuff;

public class FinishPoint extends Stuff {
    public FinishPoint(int id, int beginCell, int endCell) {
        super(id, beginCell, endCell);
    }

    @Override
    public int getStepsNumber() {
        return 100;
    }
}
