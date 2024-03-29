package cn.com.codequality.business.chat;

import static com.bankcomm.framework.utils.Utils.checkNotNull;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeBounds;
import androidx.transition.Slide;

import com.bankcomm.ui.adapter.intfc.BGAOnRVItemClickListener;
import com.bankcomm.ui.base.BaseFragment;
import com.bankcomm.ui.view.dialogs.shade.IShade;
import com.bankcomm.ui.view.dialogs.shade.ProgressShadeImp;

import java.util.ArrayList;
import java.util.List;

import cn.com.codequality.R;
import cn.com.codequality.business.chat.detail.ChatDetailFragment;
import cn.com.codequality.data.chat.bean.Chat;

/**
 * Created by  on 2018/6/22.
 */

public class ChatFragment extends BaseFragment implements ChatContract.View {

    private ChatContract.Presenter mPresenter;
    private TextView testJsonView;
    private ChatAdapter mChatAdapter;
    private View mNoDataView;
    private RecyclerView mChatList;
    private IShade mShade;
    private ChatDetailFragment chatDetailFragment;

    @Override
    public void setPresenter(ChatContract.Presenter presenter) {
        checkNotNull(presenter, "mainPresenter cannot be null");
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mShade = new ProgressShadeImp(getActivity());
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        mNoDataView = view.findViewById(R.id.no_data_view);
        mChatList = view.findViewById(R.id.chat_list);
        testJsonView = view.findViewById(R.id.test_json);

        chatDetailFragment = new ChatDetailFragment();
        Slide slideTransition = new Slide(Gravity.RIGHT);
        slideTransition.setDuration(500);

        ChangeBounds changeBoundsTransition = new ChangeBounds();
        changeBoundsTransition.setDuration(500);

        chatDetailFragment.setEnterTransition(slideTransition);
        chatDetailFragment.setAllowEnterTransitionOverlap(true);
        chatDetailFragment.setAllowReturnTransitionOverlap(true);
        chatDetailFragment.setSharedElementEnterTransition(changeBoundsTransition);
        testJsonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mShade.showDialog();
//                startActivity(new Intent(getContext(), GameActivity.class));
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, chatDetailFragment)
                        .addToBackStack(null)
                        .addSharedElement(testJsonView,"test").commit();

            }
        });
        mChatList.setLayoutManager(new LinearLayoutManager(getContext()));
        mChatList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        mChatAdapter = new ChatAdapter(mChatList, R.layout.recycler_chat);
        mChatAdapter.setData(getData());

        mChatAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {

            }
        });
        mChatList.setAdapter(mChatAdapter);
        on_layout = view.findViewById(R.id.on_layout);
        return view;
    }
    View on_layout;
    @Override
    public void onResume() {
        super.onResume();
        on_layout.setVisibility(View.GONE);
        if (mPresenter==null) {
            return;
        }
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        on_layout.setVisibility(View.VISIBLE);
        if (mPresenter==null) {
            return;
        }
        mPresenter.unsubscribe();
    }

    @Override
    public void showChatList(List<Chat> data) {
        mNoDataView.setVisibility(View.GONE);
        mChatList.setVisibility(View.VISIBLE);
        mChatAdapter.setData(data);
    }

    @Override
    public void showNoData() {
        mNoDataView.setVisibility(View.VISIBLE);
        mChatList.setVisibility(View.GONE);
    }

    @Override
    public void showTestJson(String text) {
//        testJsonView.setText(text);
    }

    @Override
    public void showWaitDialog() {
        mShade.showDialog();
    }

    @Override
    public void dismissDialog() {
        mShade.hideDialog();
    }

    public List<Chat> getData() {
        List<Chat> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Chat chat = new Chat();
            chat.setId(""+i*12);
            chat.setMessage("i am busy"+i);
            data.add(chat);
        }
        return data;
    }
}
