package am.andranik.semicirclelayoutmanger;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import java.util.List;

import am.andranik.semicirclelayoutmanger.circle.CircleHelper;
import am.andranik.semicirclelayoutmanger.circle.CircleHelperInterface;
import am.andranik.semicirclelayoutmanger.layouter.Layouter;
import am.andranik.semicirclelayoutmanger.layouter.LayouterCallback;
import am.andranik.semicirclelayoutmanger.point.Point;
import am.andranik.semicirclelayoutmanger.point.PointsGenerator;
import am.andranik.semicirclelayoutmanger.scroller.IScrollHandler;
import am.andranik.semicirclelayoutmanger.scroller.ScrollHandlerCallback;
import am.andranik.semicirclelayoutmanger.utils.Config;
import am.andranik.semicirclelayoutmanger.utils.ViewData;

/**
 * Created by andranik on 9/21/16.
 */

public class SemiCircularLayoutManager extends RecyclerView.LayoutManager implements LayouterCallback, ScrollHandlerCallback {

    public static final boolean ENDLESS_SCROLL = true;

    private static final boolean SHOW_LOGS = Config.SHOW_LOGS;
    private static final String TAG = SemiCircularLayoutManager.class.getSimpleName();

    public static int pointsToAddCount = 774;

    private RecyclerView mRecyclerView;

    private Layouter mLayouter;

    private IScrollHandler mScroller;
    private CircleHelperInterface mQuadrantHelper;

    private int maxVisibleItemCount;

    private boolean lastViewNotFullyDrawn = false;

    /**
     * This is a helper value. We should always return "true" from {@link #canScrollVertically()} but we need to change this value to false when measuring a child view size.
     * This is because the height "match_parent" is not calculated correctly if {@link #canScrollHorizontally()} returns "true"
     * and
     * the height "match_parent" is not calculated correctly if {@link #canScrollVertically()} returns "true"
     */

    private boolean mCanScrollVertically = true;

    private int mFirstVisiblePosition = 0; //TODO: implement save/restore state
    private int mLastVisiblePosition = 0; //TODO: implement save/restore state

    private boolean reduceWidthToHeight;

    public SemiCircularLayoutManager(RecyclerView recyclerView, boolean reduceWidthToHeight) {
        mRecyclerView = recyclerView;
        this.reduceWidthToHeight = reduceWidthToHeight;
    }

    @Override
    public void getHitRect(Rect rect) {
        mRecyclerView.getHitRect(rect);
    }

    @Override
    public Pair<Integer, Integer> getHalfWidthHeightPair(View view) {

        Pair<Integer, Integer> widthHeight;
        measureChildWithMargins(view, 0, 0);

        int measuredWidth = getDecoratedMeasuredWidth(view);
        int measuredHeight = getDecoratedMeasuredHeight(view);

        if (SHOW_LOGS)
            Log.i(TAG, "getHalfWidthHeightPair, measuredWidth " + measuredWidth + ", measuredHeight " + measuredHeight);

        int halfViewHeight = measuredHeight / 2;
        if (SHOW_LOGS) Log.v(TAG, "getHalfWidthHeightPair, halfViewHeight " + halfViewHeight);

        int halfViewWidth = measuredWidth / 2;
        if (SHOW_LOGS) Log.v(TAG, "getHalfWidthHeightPair, halfViewWidth " + halfViewWidth);

        widthHeight = new Pair<>(
                halfViewWidth,
                halfViewHeight
        );
        return widthHeight;
    }

    @Override
    public boolean isEndlessScrollEnabled() {
        return ENDLESS_SCROLL;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollVertically() {
        return mCanScrollVertically;
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        if(SHOW_LOGS) Log.v(TAG, "scrollVerticallyBy dy " + dy);
        int childCount = getChildCount();
        if(SHOW_LOGS) Log.v(TAG, "scrollVerticallyBy childCount " + childCount);

        if ((childCount == 0 || getItemCount() <= getChildCount()) && getItemCount() <= maxVisibleItemCount && !lastViewNotFullyDrawn) {
            return 0;
        }

        return mScroller.scrollVerticallyBy(dy, recycler);
    }

    private int getInitialMeasuredWidth(RecyclerView.Recycler recycler){
        View view = recycler.getViewForPosition(mLastVisiblePosition);
        addView(view);
        measureChildWithMargins(view, 0, 0);

        int measuredWidth = getDecoratedMeasuredWidth(view);

        removeAllViews();

        return measuredWidth;
    }

    private void initHelpers(RecyclerView.Recycler recycler){
        int x = getWidth();
        int y = getHeight()/2;
        int radius = x/2 + 100;

        List<Point> mPoints = PointsGenerator.generatePoints(x, y, radius);

        int measuredWidth = getInitialMeasuredWidth(recycler);
        pointsToAddCount = measuredWidth + (measuredWidth / 2);

        mQuadrantHelper = new CircleHelper(mPoints, pointsToAddCount);
        mQuadrantHelper.setReduceWidhtToHeight(reduceWidthToHeight);

        mLayouter = new Layouter(this, mQuadrantHelper);
        mScroller = IScrollHandler.Factory.createScrollHandler(
                IScrollHandler.Strategy.NATURAL,
                this,
                mQuadrantHelper,
                mLayouter);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if(SHOW_LOGS) Log.v(TAG, ">> onLayoutChildren, state " + state);
        if(!state.didStructureChange()){
            if(SHOW_LOGS) Log.v(TAG, "onLayoutChildren, Structure was not changed: Do not need to do anything. return");
            return;
        }

        //We have nothing to show for an empty data set but clear any existing views
        int itemCount = getItemCount();
        if (itemCount == 0) {
            removeAllViews();
            return;
        }

        if(getChildCount() == 0) {
            initHelpers(recycler);

            mLastVisiblePosition = 0;
            mFirstVisiblePosition = 0;

        } else {
            View firstVisibleView = getChildAt(0);

            mFirstVisiblePosition = getPosition(firstVisibleView);
            mLastVisiblePosition = mFirstVisiblePosition;
        }

        ViewData viewData = getViewData();

        removeAllViews();

        if(SHOW_LOGS) {
            Log.v(TAG, "onLayoutChildren, state " + state);
            Log.v(TAG, "onLayoutChildren, mLastVisiblePosition " + mLastVisiblePosition);
        }

        // It will be our stop flag
        boolean isLastLayoutedView;

        do{
            View view = recycler.getViewForPosition(mLastVisiblePosition);
            addView(view);
            viewData = mLayouter.layoutNextView(view, viewData);

            // We update coordinates instead of creating new object to keep the heap clean
            if (SHOW_LOGS) Log.v(TAG, "onLayoutChildren, viewData " + viewData);

            isLastLayoutedView = (mLayouter.isLastLaidOutView(view) && getChildCount() > 2) || getItemCount() <= getChildCount();

            incrementLastVisiblePosition();
            mLastVisiblePosition = getLastVisiblePosition();

            maxVisibleItemCount++;

            if(isLastLayoutedView && view.getRight() > getWidth()){
                lastViewNotFullyDrawn = true;
            }

        } while (!isLastLayoutedView);

        if (SHOW_LOGS) Log.v(TAG, "onLayoutChildren, mLastVisiblePosition " + mLastVisiblePosition);
    }


    private ViewData getViewData(){
        ViewData vd;

        if(getChildCount() == 0) {
            Point point = mQuadrantHelper.getViewCenterPoint(pointsToAddCount + 1);
            vd = new ViewData(0, 0, 0, 0, point);
        } else {
            View firstVisibleView = getChildAt(0);

            int viewCenterX = firstVisibleView.getRight() - firstVisibleView.getWidth() / 2;
            int viewCenterY = firstVisibleView.getTop() + firstVisibleView.getHeight() / 2;

            Point point = new Point(viewCenterX, viewCenterY);
            vd = new ViewData(
                    firstVisibleView.getTop(),
                    firstVisibleView.getBottom(),
                    firstVisibleView.getLeft(),
                    firstVisibleView.getRight(),
                    point
            );

            int halfWidth = getHalfWidthHeightPair(firstVisibleView).first;
            int halfHeight = getHalfWidthHeightPair(firstVisibleView).second;

            Point point1 = mQuadrantHelper.findPreviousViewCenter(vd, halfHeight, halfWidth);

            vd = new ViewData(
                    point1.getY() - halfHeight,
                    point1.getY() + halfHeight,
                    point1.getX() - halfWidth,
                    point1.getX() + halfWidth,
                    point1);
        }

        return vd;
    }

    /**
     * This is a wrapper method for {@link android.support.v7.widget.RecyclerView#measureChildWithMargins(View, int, int, int, int)}
     *
     * If capsules width is "match_parent" then we we need to return "false" from {@link #canScrollHorizontally()}
     * If capsules height is "match_parent" then we we need to return "false" from {@link #canScrollVertically()}
     *
     * This method simply changes return values of {@link #canScrollHorizontally()} and {@link #canScrollVertically()} while measuring
     * size of a child view
     */
    public void measureChildWithMargins(View child, int widthUsed, int heightUsed) {
        // change a value to "false "temporary while measuring
        mCanScrollVertically = false;

        super.measureChildWithMargins(child, widthUsed, heightUsed);

        // return a value to "true" because we do actually can scroll in both ways
        mCanScrollVertically = true;
    }

    @Override
    public int getFirstVisiblePosition() {
        if(isEndlessScrollEnabled() && mFirstVisiblePosition <= 0){
            mFirstVisiblePosition = getItemCount();
        }

        return mFirstVisiblePosition;
    }

    @Override
    public int getLastVisiblePosition() {
        if(isEndlessScrollEnabled() && mLastVisiblePosition >= getItemCount()){
            mLastVisiblePosition = 0;
        }
        return mLastVisiblePosition;
    }

    @Override
    public void incrementFirstVisiblePosition() {
        if(isEndlessScrollEnabled() && mFirstVisiblePosition == getItemCount()){
            mFirstVisiblePosition = 0;
        }
        mFirstVisiblePosition++;
    }

    @Override
    public void incrementLastVisiblePosition() {
        if(isEndlessScrollEnabled() && mLastVisiblePosition == getItemCount()){
            mLastVisiblePosition = 0;
        }
        mLastVisiblePosition++;
    }

    @Override
    public void decrementLastVisiblePosition() {
        if(isEndlessScrollEnabled() && mLastVisiblePosition == 0){
            mLastVisiblePosition = 1;
        }
        mLastVisiblePosition--;
    }

    @Override
    public void decrementFirstVisiblePosition() {
        if(isEndlessScrollEnabled() && mFirstVisiblePosition == 0){
            mFirstVisiblePosition = 1;
        }
        mFirstVisiblePosition--;
    }
}
