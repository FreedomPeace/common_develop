package com.example.rxjava_demo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rxjava_demo.databinding.FragmentHomeBinding
import com.example.rxjava_demo.ui.home.HomeViewModel

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val homeViewModel = ViewModelProvider(this).get(
            HomeViewModel::class.java
        )
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val textView = binding!!.textHome
        homeViewModel.text.observe(
            viewLifecycleOwner
        ) { text: String? -> textView.text = text }
        homeViewModel.getFreedomPeaceInfo()
//        homeViewModel.getUsers(context)
//        homeViewModel.getUser3()
//        homeViewModel.getReposInfo(context)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}