package cn.com.codequality.business.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import cn.com.codequality.R;

public class TabLayoutViewPager2Comp extends FrameLayout {
    /**
     * 上滑主题卡片弹出内容背景最大百分比
     */
    private static final float MAX_BOTTOM_SHEET_DIM = 0.5f;
    public TabLayoutViewPager2Comp(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_viewpager2_detail, null);
        addView(view);
    }
}
