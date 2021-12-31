package com.example.rxjava_demo.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.rxjava_demo.api.card.RechargeCard
import com.example.rxjava_demo.databinding.FragmentAllCardBinding

class AllCardFragment : Fragment() {
    private val viewModel:CardViewModel by lazy { ViewModelProvider(requireActivity()).get(CardViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding:FragmentAllCardBinding = FragmentAllCardBinding.inflate(layoutInflater)
        binding.vm = viewModel
        return binding.root
    }

    @BindingAdapter("cardList")
    fun bindCardList(view: ViewPager2, list: List<RechargeCard?>?) {

        view.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                TODO("Not yet implemented")
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                TODO("Not yet implemented")
            }

            override fun getItemCount(): Int {
                TODO("Not yet implemented")
            }
        }
    }
}