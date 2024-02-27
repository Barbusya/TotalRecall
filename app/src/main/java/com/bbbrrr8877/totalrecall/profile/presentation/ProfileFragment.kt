package com.bbbrrr8877.totalrecall.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bbbrrr8877.android.BaseFragment
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<ProfileViewModel>(R.layout.fragment_profile) {

    override val viewModelClass = ProfileViewModel::class.java

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            signOutButton.setOnClickListener { viewModel.signOut() }
            backButton.setOnClickListener { viewModel.goBack() }
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.show(binding.emailTextView)
        }
        viewModel.init(savedInstanceState == null)
    }

}