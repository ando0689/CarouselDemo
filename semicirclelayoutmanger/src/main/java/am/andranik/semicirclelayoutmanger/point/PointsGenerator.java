package am.andranik.semicirclelayoutmanger.point;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andranik on 9/14/16.
 */

public class PointsGenerator {

    public static List<Point> generatePoints(int x0, int y0, int radius){
        List<Point> points = new ArrayList<>();

        int x = radius;
        int y = 0;
        int err = 0;

        while (x >= y) {

            points.add(new Point(x0 - y, y0 + x));
            points.add(new Point(x0 - x, y0 + y));
            points.add(new Point(x0 - x, y0 - y));
            points.add(new Point(x0 - y, y0 - x));

            y += 1;
            err += 1 + 2 * y;
            if (2 * (err - x) + 1 > 0) {
                x -= 1;
                err += 1 - 2 * x;
            }
        }

        List<Point> newPoint = new ArrayList<>();

        log("******************************************** loop 1 ********************************************");
        for (int i = 3; i < points.size(); i+=4){
            newPoint.add(points.get(i));

            if(i == 3 || i == points.size() - 1)
            log("x = " + points.get(i).getX() + ", y = " + points.get(i).getY());
        }

        log("******************************************** loop 2 ********************************************");
        for (int i = points.size() - 6; i >= 2; i-=4){
            newPoint.add(points.get(i));
            if(i == 2 || i == points.size() - 6)
            log("x = " + points.get(i).getX() + ", y = " + points.get(i).getY());
        }

        log("******************************************** loop 3 ********************************************");
        for (int i = 5; i < points.size(); i+=4){
            newPoint.add(points.get(i));
            if(i == 5 || i == points.size() - 3)
            log("x = " + points.get(i).getX() + ", y = " + points.get(i).getY());
        }

        log("******************************************** loop 4 ********************************************");
        for (int i = points.size() - 8; i >= 0; i-=4){
            newPoint.add(points.get(i));
            if(i == 0 || i == points.size() - 8)
            log("x = " + points.get(i).getX() + ", y = " + points.get(i).getY());
        }

        return newPoint;
    }

    private static void log(String msg){
        Log.d("point", msg);
    }
}
