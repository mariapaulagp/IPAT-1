package ipat.johanbayona.gca.ipat.Adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import ipat.johanbayona.gca.ipat.MyConvert;

public class SketchCustViewPager extends ViewPager {

    private boolean enabled;
    public static final int DEFAULT_DRAGGING_START_X = -1;
    public static final int SLIDE_FROM_LEFT_EDGE = 0;

    private int startDraggingX = 1;//DEFAULT_DRAGGING_START_X;

    public SketchCustViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }

    float x;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        if(event.getX() > (MyConvert.getScreenResolution().x - 20)) return super.onTouchEvent(event);
        return false;
    }
}

//    mViewPager.beginFakeDrag();
//    mViewPager.endFakeDrag();
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if(event.getX() > 1004) return super.onTouchEvent(event);
//        return false;
//    }