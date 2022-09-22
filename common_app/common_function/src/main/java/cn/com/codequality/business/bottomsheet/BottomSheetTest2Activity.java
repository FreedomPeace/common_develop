package cn.com.codequality.business.bottomsheet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import cn.com.codequality.R;

/**
 * 1] header_pic  just translationY up to  parent_top when  behavior state change from [state =STATE_COLLAPSED ] to [state =STATE_HALF_EXPANDED ]
 * 2】属性 isHeaderFixed2Top = TRUE   make header_pic can continue to setTranslationY
 */
public class BottomSheetTest2Activity extends AppCompatActivity {
    boolean isHeaderFixed2Top;
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

    float onHalfOffsetRatio;

    private void initViewPage2AndTabLayout() {
        TestFragment testFragment = (TestFragment) getSupportFragmentManager().findFragmentById(R.id.sheet);
        View headerPic = findViewById(R.id.header_pic);

        final View behaviorView = testFragment.getView();

        final BottomSheetBehavior<View> sheetBehavior = BottomSheetBehavior.from(behaviorView);


        final View slidingContainerView = findViewById(R.id.content);

        behaviorView.post(new Runnable() {
            @Override
            public void run() {
                int peekHeight = sheetBehavior.getPeekHeight();
                int containerViewHeight = slidingContainerView.getHeight();
                onHalfOffsetRatio = getOnHalfOffsetRatio(sheetBehavior, peekHeight, containerViewHeight);
                Log.d("ppp", String.format("peekHeight is %d ；containerViewHeight is %d", peekHeight, containerViewHeight));
                headerPic.getLayoutParams().height = peekHeight;
//                sheetBehavior.setPeekHeight(peekHeight);
            }
        });

        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                public static final int STATE_DRAGGING = 1;
//                public static final int STATE_SETTLING = 2;
//                public static final int STATE_EXPANDED = 3;
//                public static final int STATE_COLLAPSED = 4;
//                public static final int STATE_HIDDEN = 5;
//                public static final int STATE_HALF_EXPANDED = 6;
//                if (newState == BottomSheetBehavior.STATE_HALF_EXPANDED) {
//                    headerPic.setTranslationY(dimen_);
//                }
                Log.d("ppp", String.format("onStateChanged newState is %d", newState));
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                float top = headerPic.getTop();//
                double arc = Math.atan(onHalfOffsetRatio);//
                double offsetL = slideOffset / Math.tan(arc);

                if (offsetL > 1 && isHeaderFixed2Top) {
                    offsetL = 1;
                }
                float translationY = (float) (-top * offsetL);
//                ObjectAnimator.ofFloat(headerPic,View.TRANSLATION_Y,preTranslationY,translationY)
//                        .setDuration(0).start();
//                preTranslationY = translationY;
                headerPic.setTranslationY(translationY);
                Log.d("ppp", String.format(" slideOffset is %f =offsetL is %f  top is %f onHalfOffsetRatio is %f", slideOffset, offsetL, top, onHalfOffsetRatio));
            }
        });
//        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(headerPic,View.TRANSLATION_Y,0 ,dimen_);
//        animator.setDuration(500);
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                Log.d("ppp", String.format("onAnimationEnd topY %d testFragment topY %d", headerPic.getTop(),behaviorView.getTop()));
//            }
//        });
//        animator.start();
    }

    //适用于  app:behavior_fitToContents="false"
    private float getOnHalfOffsetRatio(BottomSheetBehavior<View> sheetBehavior, int peekHeight, int containerViewHeight) {
        int collapsedOffset = containerViewHeight - peekHeight;//要销毁的总高度
        int halfExpandedOffset = (int) (containerViewHeight * (1 - sheetBehavior.getHalfExpandedRatio()));//半展开时候，已经销毁的高度。
        return (float) (collapsedOffset - halfExpandedOffset) / (float) collapsedOffset;
    }
}