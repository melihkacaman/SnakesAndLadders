package movement;

import java.util.LinkedList;
import java.util.Queue;

public class MovementContoller extends Thread {
    private Queue<Moveable> movements;

    public MovementContoller(){
        this.movements = new LinkedList<>();
    }

    public void addMovement(Moveable moveable){
        this.movements.add(moveable);
    }

    @Override
    public void run() {
        Moveable moveable = movements.poll();
        while (moveable != null){
            moveable.move();
            moveable = movements.poll();
        }
    }
}
