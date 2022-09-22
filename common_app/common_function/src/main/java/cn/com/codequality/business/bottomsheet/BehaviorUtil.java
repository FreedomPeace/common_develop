package cn.com.codequality.business.bottomsheet;

import static java.lang.Math.max;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class BehaviorUtil {
    public static float calOffset(int top, BottomSheetBehavior<?> behavior,int parentHeight,int peekHeight) {
        int collapsedOffset;
        if (behavior.isFitToContents()) {
            //todo  fitToContentsOffset ，未实现
            int fitToContentsOffset = 0 ;
            collapsedOffset = max(parentHeight - peekHeight, fitToContentsOffset);
        } else {
            collapsedOffset = parentHeight - peekHeight;
        }
        return (top > collapsedOffset || collapsedOffset == behavior.getExpandedOffset())
                ? (float) (collapsedOffset - top) / (parentHeight - collapsedOffset)
                : (float) (collapsedOffset - top) / (collapsedOffset - behavior.getExpandedOffset());
    }

}
