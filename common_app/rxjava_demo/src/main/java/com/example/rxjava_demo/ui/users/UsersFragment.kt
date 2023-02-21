package com.example.rxjava_demo.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjava_demo.bean.UserResponse
import com.example.rxjava_demo.databinding.FragmentUsersBinding
import com.example.rxjava_demo.databinding.ItemUserBinding

class UsersFragment : Fragment() {
    private var binding: FragmentUsersBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val usersViewModel = ViewModelProvider(this).get(
            UsersViewModel::class.java
        )
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val rvUsers = binding!!.rvUsers
        val adapter = UserAdapter(inflater)
        rvUsers.adapter = adapter
        rvUsers.layoutManager = LinearLayoutManager(context)
        usersViewModel.users.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        context?.let { usersViewModel.getUser2(it) }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}