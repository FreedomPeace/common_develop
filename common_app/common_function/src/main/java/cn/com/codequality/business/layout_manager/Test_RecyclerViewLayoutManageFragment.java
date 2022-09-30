package cn.com.codequality.business.layout_manager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.com.codequality.R;
import cn.com.codequality.business.chat.ChatAdapter;
import cn.com.codequality.data.chat.bean.Chat;

/**
 * Created by  on 2018/6/22.
 */

public class Test_RecyclerViewLayoutManageFragment extends Fragment {
    public TextView testJsonView;
    private ChatAdapter mChatAdapter;
    public  RecyclerView mChatList;
    public static final String TAG = "TestFragment ==";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_manager_experiment, container, false);
        mChatList = view.findViewById(R.id.chat_list);

        final Context context = getContext();
        if (context==null) {return view;}
        mChatList.setLayoutManager(new CusLayoutManager(context,mChatList));
//        mChatList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
        mChatAdapter = new ChatAdapter(mChatList, R.layout.recycler_item_custom_layoutmanager);
        List<Chat> data = getData();
        mChatAdapter.setData(data);

        mChatAdapter.setOnRVItemClickListener((parent, itemView, position) -> Toast.makeText(context, data.get(position).getMessage(), Toast.LENGTH_SHORT).show());
        mChatList.setAdapter(mChatAdapter);

        return view;
    }

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
