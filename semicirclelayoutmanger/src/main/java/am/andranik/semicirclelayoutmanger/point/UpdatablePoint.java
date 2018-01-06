package am.andranik.semicirclelayoutmanger.point;

/**
 * Created by andranik on 9/21/16.
 */

public class UpdatablePoint extends Point{

    public UpdatablePoint(int x, int y) {
        super(x, y);
    }

    public void update(int x, int y) {
        setX(x);
        setY(y);
    }
}
