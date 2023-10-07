package com.usth.job.home.fragments.userFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.usth.job.R
import com.usth.job.auth.AuthActivity
import com.usth.job.databinding.BottomSheetLogoutBinding
import com.usth.job.databinding.FragmentUserBinding
import com.usth.job.home.viewmodel.UserEditViewModel
import com.usth.job.model.Student
import com.usth.job.util.LoadingDialog
import com.usth.job.util.Status.*
import com.usth.job.util.showToast


class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private val userEditViewModel by viewModels<UserEditViewModel>()
    private val loadingDialog by lazy { LoadingDialog(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)

        setupUI()
        setupObserver()

        return binding.root
    }

    private fun setupUI() {
        userEditViewModel.fetchStudent()
        binding.apply {

            ivPopOut.setOnClickListener {
                requireActivity().finish()
            }

            cvContactSup.setOnClickListener {
                navigateToContactSup()
            }

            cvLogout.setOnClickListener {
                logoutBottomSheet()
            }
        }
    }
    private fun setupObserver() {
        userEditViewModel.student.observe(viewLifecycleOwner){ studentState ->
            when(studentState.status){
                LOADING -> {
                    loadingDialog.show()
                }
                SUCCESS -> {
                    val student = studentState.data!!
                    binding.tvUsername.text = student.details?.username
                    binding.tvUserEmail.text = student.details?.email
                    binding.profileImage.load(student.details?.imageUrl)
                    binding.cvManageAccount.setOnClickListener {
                        navigateToUserEdit(student)
                    }
                    binding.cvUpdateResume.setOnClickListener {
                        navigateToUpdateResume()
                    }
                    loadingDialog.dismiss()
                }
                ERROR -> {
                    val errorMessage = studentState.message!!
                    showToast(requireContext(), errorMessage)
                    loadingDialog.dismiss()
                }
            }
        }
    }

    private fun navigateToUserEdit(student: Student) {
        val direction = UserFragmentDirections.actionUserFragmentToUserEditFragment(student)
        findNavController().navigate(direction)
    }

    private fun navigateToUpdateResume() {
        findNavController().navigate(R.id.action_userFragment_to_userResumeEditFragment)
    }

    private fun navigateToContactSup(){
        findNavController().navigate(R.id.action_userFragment_to_userSupContact)
    }

    private fun logoutBottomSheet(){
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val logoutSheetBinding = BottomSheetLogoutBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(logoutSheetBinding.root)
        logoutSheetBinding.apply {
            btnNo.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
            btnLogout.setOnClickListener {
                bottomSheetDialog.dismiss()
                FirebaseAuth.getInstance().signOut()
                requireActivity().finishAffinity()
                val loginIntent = Intent(requireContext(), AuthActivity::class.java)
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(loginIntent)
            }
        }
        bottomSheetDialog.show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}