package com.example.rxjava_demo.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rxjava_demo.databinding.FragmentRepositoryBinding

class RepoFragment : Fragment() {
    private var binding: FragmentRepositoryBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val repoViewModel = ViewModelProvider(this).get(
            RepoViewModel::class.java
        )
        binding = FragmentRepositoryBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val textView = binding!!.textRepo
        repoViewModel.text.observe(viewLifecycleOwner) { text: String? -> textView.text = text }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}