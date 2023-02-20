package cn.com.codequality.business.bottomsheet;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import cn.com.codequality.business.custom_view.TouchEventUtil;

public class PrintEventCoordinatorLayout extends CoordinatorLayout {

    private final CharSequence contentDescription;

    public PrintEventCoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        contentDescription ="--description--" + getContentDescription();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.w("fp", "PrintEventCoordinatorLayout | dispatchTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()) + contentDescription);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.w("fp", "PrintEventCoordinatorLayout | onInterceptTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()) + contentDescription);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.w("fp", "PrintEventCoordinatorLayout | onTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()) + contentDescription);
        return super.onTouchEvent(ev);
    }
}
