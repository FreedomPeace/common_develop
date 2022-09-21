package cn.com.codequality.business.bottomsheet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import cn.com.codequality.R;

public class BottomSheetTest2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_test2);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initViewPage2AndTabLayout();
    }

    View translationView;
    int currentState;
    float preTranslationY;

    private void initViewPage2AndTabLayout() {
        TestFragment testFragment = (TestFragment) getSupportFragmentManager().findFragmentById(R.id.sheet);
        translationView = findViewById(R.id.header_pic);

        View view = testFragment.getView();

        BottomSheetBehavior<View> sheetBehavior = BottomSheetBehavior.from(view);
        float halfExpandedRatio = sheetBehavior.getHalfExpandedRatio();

        View contentView = findViewById(R.id.content);
        contentView.post(new Runnable() {
            @Override
            public void run() {
                int height = contentView.getHeight();
                int peekHeight = (int) (height * (1 - halfExpandedRatio));
                translationView.getLayoutParams().height = peekHeight;
                sheetBehavior.setPeekHeight(peekHeight);
            }
        });
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                if (newState == BottomSheetBehavior.STATE_HALF_EXPANDED) {
//                    translationView.setTranslationY(dimen_);
//                }
                Log.d("ppp", String.format("onStateChanged newState is %d", newState));
//                currentState = newState;
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                int top = translationView.getTop();
                double arc = Math.atan(halfExpandedRatio-0.2f);//arc
                double offsetL = slideOffset / Math.tan(arc);

                if (offsetL>1) {
                    offsetL = 1;
                }
                float translationY = (float) (-top * offsetL);
//                ObjectAnimator.ofFloat(translationView,View.TRANSLATION_Y,preTranslationY,translationY)
//                        .setDuration(0).start();
//                preTranslationY = translationY;
                translationView.setTranslationY(translationY);
                Log.d("ppp", String.format(" slideOffset is %f =offsetL is %f  top is %d", slideOffset, offsetL,top));
            }
        });
//        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(translationView,View.TRANSLATION_Y,0 ,dimen_);
//        animator.setDuration(500);
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                Log.d("ppp", String.format("onAnimationEnd topY %d testFragment topY %d", translationView.getTop(),view.getTop()));
//            }
//        });
//        animator.start();
    }
}