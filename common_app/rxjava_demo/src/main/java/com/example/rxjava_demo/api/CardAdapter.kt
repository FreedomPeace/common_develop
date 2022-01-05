package com.example.rxjava_demo.api

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjava_demo.R
import com.example.rxjava_demo.api.card.RechargeCard
import com.example.rxjava_demo.databinding.ItemCardOneBinding
import kotlinx.android.synthetic.main.item_card_one.view.*

class CardAdapter(private var data: List<RechargeCard>) : RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = DataBindingUtil.inflate<ItemCardOneBinding>(LayoutInflater.from(parent.context), R.layout.item_card_one, parent, false)
        return VH(view.root)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.type.text = data[position].cardType
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

}

