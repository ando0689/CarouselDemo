package am.andranik.semicirclelayoutmanger.layouter;

import android.util.Log;
import android.util.Pair;
import android.view.View;

import am.andranik.semicirclelayoutmanger.circle.CircleHelperInterface;
import am.andranik.semicirclelayoutmanger.point.Point;
import am.andranik.semicirclelayoutmanger.utils.Config;
import am.andranik.semicirclelayoutmanger.utils.ViewData;

/**
 * Created by andranik on 9/21/16.
 */

public class Layouter {
    private static final boolean SHOW_LOGS = Config.SHOW_LOGS;
    private static final String TAG = Layouter.class.getSimpleName();

    private final LayouterCallback mCallback;

    private final CircleHelperInterface mQuadrantHelper;

    public Layouter(LayouterCallback callback, CircleHelperInterface quadrantHelper) {
        mCallback = callback;
        mQuadrantHelper = quadrantHelper;
    }

    public ViewData layoutNextView(View view, ViewData previousViewData) {
        if (SHOW_LOGS) Log.v(TAG, ">> layoutNextView, previousViewData " + previousViewData);

        Pair<Integer, Integer> halfWidthHeight = mCallback.getHalfWidthHeightPair(view);

        Point viewCenter = mQuadrantHelper.findNextViewCenter(previousViewData, halfWidthHeight.first, halfWidthHeight.second);
        if (SHOW_LOGS) Log.v(TAG, "layoutNextView, viewCenter " + viewCenter);

        performLayout(view, viewCenter, halfWidthHeight.first, halfWidthHeight.second);
        previousViewData.updateData(view, viewCenter);

        if (SHOW_LOGS) Log.v(TAG, "<< layoutNextView");
        return previousViewData;
    }

    public ViewData layoutViewPreviousView(View view, ViewData previousViewData) {
        if (SHOW_LOGS)
            Log.v(TAG, ">> layoutViewPreviousView, previousViewData " + previousViewData);

        Pair<Integer, Integer> halfWidthHeight = mCallback.getHalfWidthHeightPair(view);

        Point viewCenter = mQuadrantHelper.findPreviousViewCenter(previousViewData, halfWidthHeight.second, halfWidthHeight.first);
        if (SHOW_LOGS) Log.v(TAG, "layoutViewPreviousView, viewCenter " + viewCenter);

        performLayout(view, viewCenter, halfWidthHeight.first, halfWidthHeight.second);
        previousViewData.updateData(view, viewCenter);

        if (SHOW_LOGS) Log.v(TAG, "<< layoutViewPreviousView");
        return previousViewData;
    }

    private void performLayout(View view, Point viewCenter, int halfViewWidth, int halfViewHeight) {
        if (SHOW_LOGS) Log.i(TAG, "performLayout, final viewCenter " + viewCenter);

        int left, top, right, bottom;

        top = viewCenter.getY() - halfViewHeight;
        bottom = viewCenter.getY() + halfViewHeight;

        left = viewCenter.getX() - halfViewWidth;
        right = viewCenter.getX() + halfViewWidth;

        mCallback.layoutDecorated(view, left, top, right, bottom);
    }

    /**
     * This method checks if this is last visible layouted view.
     * The return might be used to know if we should stop laying out
     */
    public boolean isLastLaidOutView(View view) {
        int recyclerWidth = mCallback.getWidth();
        return mQuadrantHelper.isLastLayoutedView(recyclerWidth, view);
    }
}
