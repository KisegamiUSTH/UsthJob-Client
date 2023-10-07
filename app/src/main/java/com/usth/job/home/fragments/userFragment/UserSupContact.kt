package com.usth.job.home.fragments.userFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.usth.job.databinding.FragmentUserSupContactBinding
import com.usth.job.home.adapter.SupAdapter
import com.usth.job.home.viewmodel.UserEditViewModel

class UserSupContact : Fragment() {
    private var _binding : FragmentUserSupContactBinding? = null
    private val binding get() = _binding!!
    private var _supAdapter : SupAdapter? = null
    private val supAdapter get() = _supAdapter!!
    private val userEditViewModel by viewModels<UserEditViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserSupContactBinding.inflate(inflater, container, false)
        _supAdapter = SupAdapter()
        setupUI()
        setupObserver()

        return binding.root
    }

    private fun setupUI() {
        userEditViewModel.fetchSup()

        binding.ivPopOut.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.rvContactSUP.adapter = supAdapter
        binding.rvContactSUP.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObserver() {
        userEditViewModel.supList.observe(viewLifecycleOwner){ supList ->
            supAdapter.setData(supList)
        }
    }

    override fun onDestroyView() {
        _binding = null
        _supAdapter = null
        super.onDestroyView()
    }

}