package am.andranik.semicirclelayoutmanger.scroller;

import android.view.View;

import am.andranik.semicirclelayoutmanger.circle.CircleHelperInterface;
import am.andranik.semicirclelayoutmanger.layouter.Layouter;

/**
 * Created by andranik on 9/21/16.
 */

public class NaturalScrollHandler extends ScrollHandler {

    private final ScrollHandlerCallback mCallback;

    public NaturalScrollHandler(ScrollHandlerCallback callback, CircleHelperInterface quadrantHelper, Layouter layouter) {
        super(callback, quadrantHelper, layouter);
        mCallback = callback;
    }

    @Override
    protected void scrollViews(View firstView, int delta) {
        for (int indexOfView = 0; indexOfView < mCallback.getChildCount(); indexOfView++) {
            View view = mCallback.getChildAt(indexOfView);
            scrollSingleViewVerticallyBy(view, delta);
        }
    }
}