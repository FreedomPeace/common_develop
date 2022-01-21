package com.example.rxjava_demo.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.rxjava_demo.api.card.RechargeCard
import com.example.rxjava_demo.databinding.FragmentAllCardBinding

open class AllCardFragment : Fragment() {
    //    private val viewModel:CardViewModel by lazy { ViewModelProvider(requireActivity()).get(CardViewModel::class.java) }
    private lateinit var viewModel:CardViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CardViewModel::class.java)
        viewModel.cardList.value = DistinctUntilChangedAPI().fakeData
        viewModel.subject.doOnSuccess {
            DistinctUntilChangedAPI.mockStr
            print("6666")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentAllCardBinding = FragmentAllCardBinding.inflate(layoutInflater)
        binding.vm = viewModel
        return binding.root
    }

}

@BindingAdapter("cardList")
fun bindCardList(view: ViewPager2, data: List<RechargeCard>) {
    val cardAdapter = CardAdapter(data)
    view.adapter = cardAdapter
}