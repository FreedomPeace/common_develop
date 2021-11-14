package com.bankcomm.ui.adapter;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BGARecyclerViewDefaultAdapter<M> extends BGARecyclerViewAdapter<M, RecyclerView>{

    public BGARecyclerViewDefaultAdapter(RecyclerView recyclerView, int defaultItemLayoutId) {
        super(recyclerView, defaultItemLayoutId);
    }
}