package cn.com.codequality.business.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import cn.com.codequality.R;
import cn.com.codequality.business.chat.ChatAdapter;
import cn.com.codequality.data.chat.bean.Chat;

/**
 * Created by  on 2018/6/22.
 */

public class TestFragment extends Fragment {
    public TextView testJsonView;
    private ChatAdapter mChatAdapter;
    public  RecyclerView mChatList;
    public static final String TAG = "TestFragment ==";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        mChatList = view.findViewById(R.id.chat_list);
        testJsonView = view.findViewById(R.id.test_json);

        testJsonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
            }
        });
        mChatList.setLayoutManager(new LinearLayoutManager(getContext()));
        mChatList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        mChatAdapter = new ChatAdapter(mChatList, R.layout.recycler_chat);
        List<Chat> data = getData();
        mChatAdapter.setData(data);

        mChatAdapter.setOnRVItemClickListener((parent, itemView, position) -> Toast.makeText(getContext(), data.get(position).getMessage(), Toast.LENGTH_SHORT).show());
        mChatList.setAdapter(mChatAdapter);

        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
       on_layout = view.findViewById(R.id.on_layout);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        on_layout.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        on_layout.setVisibility(View.VISIBLE);
    }

    View on_layout;
    public List<Chat> getData() {
        List<Chat> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Chat chat = new Chat();
            chat.setId(""+i*12);
            chat.setMessage("i am TestFragment"+i);
            data.add(chat);
        }
        return data;
    }
}
