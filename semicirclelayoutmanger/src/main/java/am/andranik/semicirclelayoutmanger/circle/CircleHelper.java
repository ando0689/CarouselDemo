package am.andranik.semicirclelayoutmanger.circle;

import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import am.andranik.semicirclelayoutmanger.point.Point;
import am.andranik.semicirclelayoutmanger.utils.Config;
import am.andranik.semicirclelayoutmanger.utils.ViewData;

/**
 * Created by andranik on 9/21/16.
 */

public class CircleHelper implements CircleHelperInterface {

    private static final boolean SHOW_LOGS = Config.SHOW_LOGS;
    private static final String TAG = CircleHelper.class.getSimpleName();

    private boolean reduseWidthToHeight = false;


    private List<Point> mPoints;

    private final Map<Point, Integer> mCirclePointIndex;


    public CircleHelper(List<Point> points, int pointsToAddCount) {

        mPoints = points;

        mCirclePointIndex = new HashMap<>();

        if(SHOW_LOGS) Log.v(TAG, ">> constructor, start filling sector points");
        long start = System.currentTimeMillis();

        addMissingPoints(pointsToAddCount);
        initMaps();

        if(SHOW_LOGS) Log.v(TAG, "<< constructor, finished filling sector points in " + (System.currentTimeMillis() - start));
    }

    private void addMissingPoints(int pointsToAddCount){
        final ArrayList<Point> newPoints = new ArrayList<>();

        final Point firstPoint = mPoints.get(0);
        final Point lastPoint = mPoints.get(mPoints.size() - 1);

        // append points from start
        for (int i = pointsToAddCount; i > 0; i--){
            newPoints.add(new Point(firstPoint.getX() + i, firstPoint.getY()));
        }

        // add existing points
        newPoints.addAll(mPoints);

        // append points from end
        for (int i = 1; i < pointsToAddCount; i++){
            newPoints.add(new Point(lastPoint.getX() + i, lastPoint.getY()));
        }

        mPoints = newPoints;
    }

    private void initMaps(){
        for (int i = 0; i < mPoints.size(); i++){
            final Point p = mPoints.get(i);
            mCirclePointIndex.put(p, i);
        }
    }


    private Point getNextViewCenter(Point previousViewCenter, int indexToAdd) {

        int previousViewCenterPointIndex = mCirclePointIndex.get(previousViewCenter);

        int newIndex = previousViewCenterPointIndex + indexToAdd;
        int lastIndex = mPoints.size() - 1;

        int nextViewCenterPointIndex = newIndex > lastIndex ?
                newIndex - lastIndex :
                newIndex;

        return mPoints.get(nextViewCenterPointIndex);
    }

    private Point getPreviousViewCenter(Point nextViewCenter, int indexToDistract) {

        int nextViewCenterPointIndex = mCirclePointIndex.get(nextViewCenter);

        int newIndex = nextViewCenterPointIndex - indexToDistract;
        int lastIndex = mPoints.size() - 1;

        int previousViewCenterPointIndex = newIndex < 0 ?
                lastIndex + newIndex: // this will subtract newIndex from last index
                newIndex;

        return mPoints.get(previousViewCenterPointIndex);
    }

    @Override
    public Point findNextViewCenter(ViewData previousViewData, int nextViewHalfViewWidth, int nextViewHalfViewHeight) {

        Point previousViewCenter = previousViewData.getCenterPoint();

        Point nextViewCenter;

        int indexToAdd = nextViewHalfViewWidth;

        int redusedWidth = reduseWidthToHeight ? nextViewHalfViewHeight - nextViewHalfViewHeight / 2 : nextViewHalfViewWidth;

        boolean foundNextViewCenter;
        do {
            indexToAdd = indexToAdd <= 0 ? 1 : indexToAdd;
            nextViewCenter = getNextViewCenter(previousViewCenter, indexToAdd);
            indexToAdd /=2;

            int nextViewTop = nextViewCenter.getY() - nextViewHalfViewHeight;
            int nextViewBottom = nextViewCenter.getY() + nextViewHalfViewHeight;
            int nextViewRight = nextViewCenter.getX() + redusedWidth;
            int nextViewLeft = nextViewCenter.getX() - redusedWidth;

            boolean nextViewTopIsBelowPreviousViewBottom = nextViewTop >= previousViewData.getViewBottom();
            boolean nextViewBottomIsAbovePreviousViewTop = nextViewBottom <= previousViewData.getViewTop();
            boolean nextViewIsToTheLeftOfThePreviousView = nextViewRight <= previousViewData.getViewLeft();
            boolean nextViewIsToTheRightOfThePreviousView = nextViewLeft >= previousViewData.getViewRight();

            foundNextViewCenter =
                    nextViewTopIsBelowPreviousViewBottom
                    || nextViewIsToTheLeftOfThePreviousView
                    || nextViewBottomIsAbovePreviousViewTop
                    || nextViewIsToTheRightOfThePreviousView;

            // "next view center" become previous
            previousViewCenter = nextViewCenter;
        } while (!foundNextViewCenter);

        return nextViewCenter;
    }

    @Override
    public Point findPreviousViewCenter(ViewData nextViewData, int previousViewHalfViewHeight, int previousViewHalfViewWidth) {

        Point nextViewCenter = nextViewData.getCenterPoint();

        Point previousViewCenter;

        int indexToDistract = previousViewHalfViewWidth;

        int redusedWidth = reduseWidthToHeight ? previousViewHalfViewHeight - previousViewHalfViewHeight / 2 : previousViewHalfViewWidth;

        boolean foundPreviousViewCenter;
        do {
            indexToDistract = indexToDistract <= 0 ? 1 : indexToDistract;
            previousViewCenter = getPreviousViewCenter(nextViewCenter, indexToDistract);
            indexToDistract /=2;

            int previousViewTop = previousViewCenter.getY() - previousViewHalfViewHeight;
            int previousViewBottom = previousViewCenter.getY() + previousViewHalfViewHeight;
            int previousViewRight = previousViewCenter.getX() + redusedWidth;
            int previousViewLeft = previousViewCenter.getX() - redusedWidth;

            boolean previousViewTopIsBelowNextViewBottom = previousViewTop >= nextViewData.getViewBottom();
            boolean previousViewBottomIsAboveNextViewTop = previousViewBottom <= nextViewData.getViewTop();
            boolean previousViewIsToTheLeftOfTheNextView = previousViewRight <= nextViewData.getViewLeft();
            boolean previousViewIsToTheRightOfTheNextView = previousViewLeft >= nextViewData.getViewRight();

            foundPreviousViewCenter =
                    previousViewTopIsBelowNextViewBottom
                    || previousViewBottomIsAboveNextViewTop
                    || previousViewIsToTheLeftOfTheNextView
                    || previousViewIsToTheRightOfTheNextView;

            // "previous view center" become next
            nextViewCenter = previousViewCenter;
        } while (!foundPreviousViewCenter);

        return nextViewCenter;
    }



        @Override
    public int getViewCenterPointIndex(Point point) {
        return mCirclePointIndex.get(point);
    }

    @Override
    public Point getViewCenterPoint(int newCenterPointIndex) {
        return mPoints.get(
                newCenterPointIndex
        );
    }

    @Override
    public int getNewCenterPointIndex(int newCalculatedIndex) {

        int lastIndex = mPoints.size() - 1;
        int correctedIndex;
        if(newCalculatedIndex < 0){
            correctedIndex = lastIndex + newCalculatedIndex;
        } else {
            correctedIndex = newCalculatedIndex > lastIndex ?
                    newCalculatedIndex - lastIndex :
                    newCalculatedIndex;
        }

        return correctedIndex;
    }

    /**
     * This method checks if this is last visible layouted view.
     * The return might be used to know if we should stop laying out
     * TODO: use this method in Scroll Handler
     */
    @Override
    public boolean isLastLayoutedView(int recyclerWidth, View view) {
        boolean isLastLayoutedView;
        if(SHOW_LOGS) Log.v(TAG, "isLastLaidOutView, recyclerWidth " + recyclerWidth);
        int spaceToRightEdge = view.getRight();
        if(SHOW_LOGS) Log.v(TAG, "isLastLaidOutView, spaceToRightEdge " + spaceToRightEdge);
        isLastLayoutedView = spaceToRightEdge >= recyclerWidth;
        if(SHOW_LOGS) Log.v(TAG, "isLastLaidOutView, " + isLastLayoutedView);
        return isLastLayoutedView;
    }

    @Override
    public int checkBoundsReached(int recyclerWidth, int dy, View firstView, View lastView, boolean isFirstItemReached, boolean isLastItemReached) {
        int delta;
        if (SHOW_LOGS) {
            Log.v(TAG, "checkBoundsReached, isFirstItemReached " + isFirstItemReached);
            Log.v(TAG, "checkBoundsReached, isLastItemReached " + isLastItemReached);
        }
        if (dy > 0) { // Contents are scrolling up
            //Check against bottom bound
            if (isLastItemReached) {
                //If we've reached the last row, enforce limits
                int rightOffset = getOffset(recyclerWidth, lastView);
                delta = Math.max(-dy, rightOffset);
            } else {

                delta = -dy;
            }
        } else { // Contents are scrolling down
            if (SHOW_LOGS) Log.v(TAG, "checkBoundsReached, dy " + dy);

            if (isFirstItemReached) {
                int rightOffset = getOffset(recyclerWidth, firstView);
                delta = -Math.max(dy, rightOffset);
            } else {
                delta = -dy;
            }
        }
        if (SHOW_LOGS) Log.v(TAG, "checkBoundsReached, delta " + delta);
        return delta;
    }

    @Override
    public int getOffset(int recyclerWidth, View lastView) {

        int offset = recyclerWidth - lastView.getRight();

        if (SHOW_LOGS) {
            Log.v(TAG, "getOffset, offset" + offset);
        }
        return offset;
    }

    @Override
    public void setReduceWidhtToHeight(boolean reduce) {
        reduseWidthToHeight = reduce;
    }
}