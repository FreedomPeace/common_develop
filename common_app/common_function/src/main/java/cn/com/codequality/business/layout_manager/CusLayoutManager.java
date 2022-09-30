package cn.com.codequality.business.layout_manager;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.RecyclerView;

public class CusLayoutManager extends RecyclerView.LayoutManager {
    private Context context;
    private int viewWidth;
    private RecyclerView recyclerView;
    private int screenWidth;
    int recyclerViewHeight;
    private int horizontalScrollOffset = 0;
    public CusLayoutManager(Context context, RecyclerView recyclerView) {
        screenWidth =  context.getResources().getDisplayMetrics().widthPixels;
//        viewWidth =  context.getResources().getDimensionPixelSize(R.dimen.dimen_100);
        viewWidth = (int) (1/3f*screenWidth);
        this.recyclerView = recyclerView;
        recyclerViewHeight = 2 * viewWidth;
        this.context = context;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(WRAP_CONTENT, MATCH_PARENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
//        super.onLayoutChildren(recycler, state);

        fill(recycler, state);
    }
    private float mSelectedScale = 0.8f;
    private void animationItemView(View child) {
        int childWidth = child.getWidth() - child.getPaddingLeft() - child.getPaddingRight();
        int childHeight = child.getHeight() - child.getPaddingTop() - child.getPaddingBottom();
        int width = getWidth();
        if(width <= child.getWidth()){
//            return super.drawChild(canvas, child, drawingTime);
        }
        int pivot = (width - childWidth)/2;
        int x = child.getLeft();
        float scale , alpha;
        alpha = 1 - 0.6f*Math.abs(x - pivot)/pivot;
        if(x <= pivot){
            scale = 2*(1-mSelectedScale)*(x+childWidth) / (width+childWidth) + mSelectedScale;
        }else{
            scale = 2*(1-mSelectedScale)*(width - x) / (width+childWidth) + mSelectedScale;
        }
        child.setPivotX(childWidth / 2);
        child.setPivotY(childHeight*scale/2);
        child.setScaleX(scale);
        child.setScaleY(scale);
    }

    private int getTopOffsetForView(int viewCentreX) {
        double s = screenWidth / 2;
        double h = recyclerViewHeight - viewWidth;
        double radius = (h * h + s * s) / (h * 2);

        double cosAlpha = (s - viewCentreX) / radius;
        double alpha = Math.acos(MathUtils.clamp(cosAlpha, -1.0, 1.0));

        double yComponent = radius - (radius * Math.sin(alpha));
        return (int) yComponent;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        horizontalScrollOffset += dx;
        Log.d("qqq", String.format("horizontalScrollOffset is %d ",
                horizontalScrollOffset));
        if (horizontalScrollOffset <0&& horizontalScrollOffset <= -viewWidth) {
            horizontalScrollOffset = -viewWidth;
            return dx;
        } else if (getLastVisiblePosition()==getItemCount()-1){

        }
        fill(recycler, state);
        return dx;
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);

        int firstVisiblePosition = getFirstVisiblePosition();
        int lastVisiblePosition = getLastVisiblePosition();
        Log.d("qqq", String.format("horizontalScrollOffset is %d :: firstVisiblePosition is %d",
                horizontalScrollOffset,firstVisiblePosition));
        for (int position = firstVisiblePosition; position < lastVisiblePosition; position++) {

            // 可循环滑动的效果。。。
//            int recyclerIndex = position % getItemCount();
//            if (recyclerIndex < 0) {
//                recyclerIndex += getItemCount();
//            }
//            View view = recycler.getViewForPosition(recyclerIndex);
            // 可循环滑动的效果。。。
            if (position<0 || position >= getItemCount()) {
                return;
            }
            View view = recycler.getViewForPosition(position);
            addView(view);
            layoutChildView(position, viewWidth, view);

            animationItemView(view);
        }

        for (RecyclerView.ViewHolder viewHolder : recycler.getScrapList()) {
            recycler.recycleView(viewHolder.itemView);
        }
    }

    private int getFirstVisiblePosition() {
        return (int) Math.floor(horizontalScrollOffset / viewWidth);
    }
    private int getLastVisiblePosition() {
        int i = (horizontalScrollOffset + screenWidth) / viewWidth;
        return ++i;
    }
    private void layoutChildView(int i,int viewWidthWithSpacing, View view) {
        int left = i * viewWidthWithSpacing - horizontalScrollOffset;
        int right = left + viewWidth;
//        int top = getTopOffsetForView(left + viewWidth / 2);
        int top = 0;
        int bottom = top + viewWidth;

        measureChild(view,viewWidth,viewWidth);

        layoutDecorated(view, left, top, right, bottom);
    }
}
