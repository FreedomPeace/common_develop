package cn.com.codequality.business;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bankcomm.ui.adapter.intfc.BGAOnRVItemClickListener;
import com.bankcomm.ui.base.BaseFragment;
import com.bankcomm.ui.view.dialogs.shade.IShade;
import com.bankcomm.ui.view.dialogs.shade.ProgressShadeImp;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.com.codequality.R;
import cn.com.codequality.business.chat.ChatAdapter;
import cn.com.codequality.business.chat.detail.ChatDetailFragment;
import cn.com.codequality.data.chat.bean.Chat;

/**
 * Created by  on 2018/6/22.
 */

public class TestFragment extends BaseFragment  {
    private TextView testJsonView;
    private ChatAdapter mChatAdapter;
    private View mNoDataView;
    public  RecyclerView mChatList;
    private IShade mShade;
    private ChatDetailFragment chatDetailFragment;
    public static final String TAG = "TestFragment ==";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mShade = new ProgressShadeImp(getActivity());
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        mNoDataView = view.findViewById(R.id.no_data_view);
        mChatList = view.findViewById(R.id.chat_list);
        testJsonView = view.findViewById(R.id.test_json);

        chatDetailFragment = new ChatDetailFragment();

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

        mChatAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Toast.makeText(getContext(), data.get(position).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        mChatList.setAdapter(mChatAdapter);

        RefreshLayout refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
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
