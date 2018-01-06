package am.andranik.semicirclelayoutmanger.layouter;

import android.graphics.Rect;
import android.util.Pair;
import android.view.View;

/**
 * Created by andranik on 9/21/16.
 */

public interface LayouterCallback {
    void getHitRect(Rect rect);

    void layoutDecorated(View view, int left, int top, int right, int bottom);

    Pair<Integer,Integer> getHalfWidthHeightPair(View view);

    int getChildCount();

    View getChildAt(int index);

    int getWidth();
}
