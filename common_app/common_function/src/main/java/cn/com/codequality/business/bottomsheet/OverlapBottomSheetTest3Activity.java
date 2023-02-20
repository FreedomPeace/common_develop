package cn.com.codequality.business.bottomsheet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import cn.com.codequality.R;
import cn.com.codequality.business.chat.ChatFragment;
import cn.com.codequality.business.view.TabLayoutViewPager2Comp;

public class OverlapBottomSheetTest3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_overlap_touch);
        initViewPage2AndTabLayout();
    }
    private void setTabLayoutHeightAndAlpha(float slideOffset,TabLayout tabLayout) {
        ViewGroup.LayoutParams layoutParams = tabLayout.getLayoutParams();
        int maxHeight = layoutParams.height;
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
        double radio2 = Math.abs(1f - slideOffset *ratio);
        Log.d("ppp" ,"radio2 "+radio2);
        layoutParams.height = (int) (maxHeight*(radio2));
        Log.d("ppp" ,"height "+layoutParams.height);

        //高度为0 会还原开始高度。 。。。
        layoutParams.height = layoutParams.height==0?1:layoutParams.height;
        tabLayout.setLayoutParams(layoutParams);
    }

    View translationView;
    private void initViewPage2AndTabLayout() {
        TabLayoutViewPager2Comp tabLayoutViewPager2Comp = findViewById(R.id.sheet);
        View headPic = findViewById(R.id.header_pic);
        CoordinatorLayout sliding = findViewById(R.id.sliding);
        ViewPager2 viewPager2 = findViewById(R.id.viewpager2);
        TabLayout tabLayout = findViewById(R.id.tab);

        translationView = headPic;
        TestFragment testFragment = new TestFragment();
        viewPager2.setAdapter(new FragmentStateAdapter(OverlapBottomSheetTest3Activity.this) {
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

        BottomSheetBehavior<TabLayoutViewPager2Comp> sheetBehavior = BottomSheetBehavior.from(tabLayoutViewPager2Comp);

        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                int headTop = translationView.getTop();
                float translationY = -headTop * slideOffset ;
                translationView.setTranslationY(translationY);
                Log.d("ppp",String.format(" slideOffset is %f ===translationY is %f",slideOffset,translationY));
            }
        });

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
        mediator.attach();
        viewPager2.setNestedScrollingEnabled(false);
        for (int i = 0; i < viewPager2.getChildCount(); i++) {
            viewPager2.getChildAt(i).setNestedScrollingEnabled(false);
        }

        //tab
        int dimen_300 = getResources().getDimensionPixelSize(R.dimen.dimen_300);
        int dimen_200 = getResources().getDimensionPixelSize(R.dimen.dimen_200);
        int dimen_100 = getResources().getDimensionPixelSize(R.dimen.dimen_100);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    sheetBehavior.setPeekHeight(dimen_300);
//                    headPic.setVisibility(View.VISIBLE);
                } else {
                    sheetBehavior.setPeekHeight(dimen_100);
//                    sheetBehavior.setHalfExpandedRatio(0.8f);
//                    headPic.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}