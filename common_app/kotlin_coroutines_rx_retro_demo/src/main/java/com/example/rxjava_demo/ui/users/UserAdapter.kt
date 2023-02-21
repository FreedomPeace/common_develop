package com.example.rxjava_demo.ui.users

import Utils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjava_demo.bean.UserResponse
import com.example.rxjava_demo.databinding.ItemUserBinding

class UserAdapter(private val inflater: LayoutInflater) : ListAdapter<UserResponse,UserViewHolder>(
    itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = ItemUserBinding.inflate(inflater)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userResponse = getItem(position)
        holder.itemUserBinding.textUser.text = Utils.toPrettyFormat(userResponse)
    }


}
val itemCallback = object : ItemCallback<UserResponse>() {
    override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
        return oldItem.equals(newItem)
    }

}
class UserViewHolder(val itemUserBinding: ItemUserBinding):
    RecyclerView.ViewHolder(itemUserBinding.root)