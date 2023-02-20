package cn.com.codequality.business.bottomsheet;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.com.codequality.business.custom_view.TouchEventUtil;

public class TouchDispatchFrameLayout extends FrameLayout {
    public TouchDispatchFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.w("fp", "TouchDispatchFrameLayout | dispatchTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
        View downView = getChildAt(0);
        View upView = getChildAt(1);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                downView.dispatchTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                ev.setAction(MotionEvent.ACTION_DOWN);
                upView.dispatchTouchEvent(ev);
                ev.setAction(MotionEvent.ACTION_MOVE);
                upView.dispatchTouchEvent(ev);
                break;
        }
//        ViewCompat.startNestedScroll()

        return true;
//        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.w("fp", "TouchDispatchFrameLayout | onInterceptTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.w("fp", "TouchDispatchFrameLayout | onTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
        return super.onTouchEvent(ev);
    }
}
