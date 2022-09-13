package cn.com.codequality.business.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import cn.com.codequality.R;
import cn.com.codequality.business.TestFragment;
import cn.com.codequality.business.chat.ChatFragment;

public class TabLayoutViewPager2Comp extends FrameLayout {
    public TabLayoutViewPager2Comp(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_viewpager2_detail, null);
        addView(view);
    }

    public void initView(FragmentActivity fa) {
        TabLayout tabLayout = findViewById(R.id.tab);
        ViewPager2 viewPager2 = findViewById(R.id.viewpager2);
        TestFragment chatFragment = new TestFragment();
        viewPager2.setAdapter(new FragmentStateAdapter(fa) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position == 0) {
                    return chatFragment;
                } else {
                    return new ChatFragment();
                }
            }

            @Override
            public int getItemCount() {
                return 2;
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
    }

}
