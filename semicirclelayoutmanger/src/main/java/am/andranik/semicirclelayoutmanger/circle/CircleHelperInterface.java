package am.andranik.semicirclelayoutmanger.circle;

import android.view.View;

import am.andranik.semicirclelayoutmanger.point.Point;
import am.andranik.semicirclelayoutmanger.utils.ViewData;

/**
 * Created by andranik on 9/21/16.
 */

public interface CircleHelperInterface {
    Point findNextViewCenter(ViewData previousViewData, int nextViewHalfViewWidth, int nextViewHalfViewHeight);

    int getViewCenterPointIndex(Point point);

    Point getViewCenterPoint(int newCenterPointIndex);

    int getNewCenterPointIndex(int newCalculatedIndex);

    Point findPreviousViewCenter(ViewData nextViewData, int previousViewHalfViewHeight, int previousViewHalfViewWidth);

    boolean isLastLayoutedView(int recyclerWidth, View view);

    int checkBoundsReached(int recyclerWidth, int dy, View firstView, View lastView, boolean isFirstItemReached, boolean isLastItemReached);

    int getOffset(int recyclerWidth, View lastView);

    void setReduceWidhtToHeight(boolean reduce);
}
