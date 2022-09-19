package cn.com.codequality.business.bottomsheet;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import cn.com.codequality.R;
import cn.com.codequality.business.view.TabLayoutViewPager2Comp;

public class BottomSheetTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_test);
        TabLayoutViewPager2Comp detail = (TabLayoutViewPager2Comp) findViewById(R.id.details);
        detail.initView(this);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.sliding);
    }
}