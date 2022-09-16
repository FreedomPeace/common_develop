package cn.com.codequality.business.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import cn.com.codequality.R;
import cn.com.codequality.business.TestFragment;
import cn.com.codequality.business.chat.ChatFragment;

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initView(FragmentActivity fa) {
        TabLayout tabLayout = findViewById(R.id.tab);
        ViewPager2 viewPager2 = findViewById(R.id.viewpager2);
                TestFragment testFragment = new TestFragment();
        viewPager2.setAdapter(new FragmentStateAdapter(fa) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position == 0) {
                    return new ChatFragment();
                } else {
                    return testFragment;
                }
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });

//        MCoordinatorLayout coordinatorLayout = (MCoordinatorLayout) TabLayoutViewPager2Comp.this.getParent();
        TabLayoutMediator mediator=new TabLayoutMediator(tabLayout,viewPager2,(tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("详情");
                    break;
                case 1:
                    tab.setText(String.format("评价"));
                    break;
            }
        });
        ViewGroup.LayoutParams layoutParams = tabLayout.getLayoutParams();
        int maxHeight = layoutParams.height;
        BottomSheetBehavior<TabLayoutViewPager2Comp> sheetBehavior = BottomSheetBehavior.from(TabLayoutViewPager2Comp.this);
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.d("ppp" ,"onStateChanged "+newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                float absSlideOffset = Math.abs(slideOffset);
                float radio = 1f - absSlideOffset;
                if (radio > 1) {
                    radio = 1;
                }
                tabLayout.setAlpha(radio);

                //滑动到哪个比例消失
                double goneOffset = 0.6;
                if (absSlideOffset > goneOffset) {
                    return;
                }
                Log.d("ppp" ,"onSlide "+absSlideOffset);
                //确定放到倍数
                double ratio = 1 / goneOffset;
                //1 -> goneOffset  变化 ，放大到  1 -> 0 变化
                double radio2 = Math.abs(1f - slideOffset*ratio);
                Log.d("ppp" ,"radio2 "+radio2);
                layoutParams.height = (int) (maxHeight*(radio2));
                Log.d("ppp" ,"height "+layoutParams.height);

                //高度为0 会还原开始高度。 。。。
                layoutParams.height = layoutParams.height==0?1:layoutParams.height;
                tabLayout.setLayoutParams(layoutParams);
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        mediator.attach();
        viewPager2.setNestedScrollingEnabled(false);
        for (int i = 0; i < viewPager2.getChildCount(); i++) {
            viewPager2.getChildAt(i).setNestedScrollingEnabled(false);
        }
    }
}
