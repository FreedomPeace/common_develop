package cn.com.codequality.enter;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bankcomm.ui.adapter.intfc.BGAOnRVItemClickListener;
import com.bankcomm.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import cn.com.codequality.R;
import cn.com.codequality.app_component_factory.TestActivity;

public class EnterListActivity extends BaseActivity {

    MutableLiveData<Integer> index = new MutableLiveData<>();

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_list);
        List<PageInfo> data = initData();
        initView(data);
        setSwipeBackEnable(false);
//        AppComponentFactory appFactory = createAppFactory(getApplicationInfo(), getClassLoader());
//        try {
//            appFactory.instantiateActivity(getClassLoader(), TestActivity.class.getName(), null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    private AppComponentFactory createAppFactory(ApplicationInfo appInfo, ClassLoader cl) {
        if (appInfo.appComponentFactory != null && cl != null) {
            try {
                return (AppComponentFactory)
                        cl.loadClass(appInfo.appComponentFactory).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                Log.e(TAG, "Unable to instantiate appComponentFactory", e);
            }
        }
        return null;
    }

    public void go2Test(View view) {
//        startActivity(new Intent(this,TestActivity.class));
        AppComponentFactory appFactory = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            appFactory = createAppFactory(getApplicationInfo(), getClassLoader());
        }
        try {
            Activity activity = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                activity = appFactory.instantiateActivity(getClassLoader(), TestActivity.class.getName(), null);
            }
            startActivity(new Intent(this,activity.getClass()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class PageInfo {
        public String name;
        public String simpleName;

        public PageInfo(String name, String simpleName) {
            this.name = name;
            this.simpleName = simpleName;
        }
    }

    private List<PageInfo> initData() {
        List<PageInfo> data = new ArrayList<>();
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            ActivityInfo[] activities = packageInfo.activities;
            for (ActivityInfo activity : activities) {
                String name = activity.name;
                String simpleName = name.substring(name.lastIndexOf(".") + 1);
                data.add(new PageInfo(name, simpleName));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void initView(final List<PageInfo> data) {
        RecyclerView activityList = findViewById(R.id.activity_list);
        activityList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        DividerItemDecoration decor = new DividerItemDecoration(this, DividerItemDecoration
                .HORIZONTAL);
        decor.setDrawable(AppCompatResources.getDrawable(this, R.drawable.bga_adapter_divider_shape));
        activityList.addItemDecoration(decor);
        EnterListAdapter enterListAdapter = new EnterListAdapter(activityList, R.layout.recycler_chat);
        enterListAdapter.setData(data);
        activityList.setAdapter(enterListAdapter);
        enterListAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                PageInfo className = data.get(position);
                Intent intent = new Intent();
                intent.setClassName(getApplicationContext(), className.name);
                startActivity(intent);
            }
        });

        index.observe(this, offset -> {
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
                            int indexTmp = ((RecyclerView.LayoutParams) snapView.getLayoutParams()).getViewAdapterPosition();
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
