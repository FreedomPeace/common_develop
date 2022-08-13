package cn.com.codequality.enter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bankcomm.ui.adapter.intfc.BGAOnRVItemClickListener;
import com.bankcomm.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import cn.com.codequality.R;

public class EnterListActivity extends BaseActivity {

    MutableLiveData<Integer> index  = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_list);
        List<String> data = initData();
        initView(data);
        setSwipeBackEnable(false);
    }

    private List<String> initData() {
        List<String> data = new ArrayList<>();
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            ActivityInfo[] activities = packageInfo.activities;
            for (ActivityInfo activity : activities) {
                String name = activity.name;
                String simpleName = name.substring(name.lastIndexOf(".") - 1);
                data.add(name);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void initView(final List<String> data) {
        RecyclerView activityList = findViewById(R.id.activity_list);
        activityList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        DividerItemDecoration decor = new DividerItemDecoration(this, DividerItemDecoration
                .HORIZONTAL);
        decor.setDrawable(AppCompatResources.getDrawable(this,R.drawable.bga_adapter_divider_shape));
        activityList.addItemDecoration(decor);
        EnterListAdapter enterListAdapter = new EnterListAdapter(activityList, R.layout.recycler_chat);
        enterListAdapter.setData(data);
        activityList.setAdapter(enterListAdapter);
        enterListAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                String className = data.get(position);
                Intent intent = new Intent();
                intent.setClassName(getApplicationContext(), className);
                startActivity(intent);
            }
        });

        index.observe(this,offset->{
            if (offset < activityList.getAdapter().getItemCount()) {
                activityList.scrollToPosition(offset);
            }
        });

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(activityList);
        //监听 滚动 获取具体位置
        activityList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (recyclerView != null && recyclerView.getChildCount() > 0) {
                        try {
                            // 获取居中的具体位置 时的角标
                            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                            View snapView = snapHelper.findSnapView(layoutManager);
                            int  indexTmp = ((RecyclerView.LayoutParams) snapView.getLayoutParams()).getViewAdapterPosition();
                            Log.d(TAG, "onScrollStateChanged" + " 滑动的 position ==" + index);
                            index.setValue(indexTmp);
                        } catch (Exception e) {
                        }
                    }
                }
            }
        });
    }
}
