package am.andranik.semicirclelayoutmanger.scroller;

import android.support.v7.widget.RecyclerView;

import am.andranik.semicirclelayoutmanger.circle.CircleHelperInterface;
import am.andranik.semicirclelayoutmanger.layouter.Layouter;

/**
 * Created by andranik on 9/21/16.
 */

public interface IScrollHandler {
    int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler);

    enum Strategy{
        PIXEL_PERFECT,
        NATURAL
    }

    class Factory{

        private Factory(){}

        public static IScrollHandler createScrollHandler(Strategy strategy, ScrollHandlerCallback callback, CircleHelperInterface quadrantHelper, Layouter layouter){
            IScrollHandler scrollHandler = null;
            switch (strategy){
                case PIXEL_PERFECT:
                    scrollHandler = new PixelPerfectScrollHandler(callback, quadrantHelper, layouter);
                    break;
                case NATURAL:
                    scrollHandler = new NaturalScrollHandler(callback, quadrantHelper, layouter);
                    break;
            }
            return  scrollHandler;
        }
    }
}
