package com.bbbrrr8877.totalrecall.login.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.BaseFragment
import com.bbbrrr8877.totalrecall.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<LoginViewModel>(R.layout.fragment_login) {

    override val viewModelClass = LoginViewModel::class.java

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val authResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.handleResult(AuthResultWrapper.Base(it))
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            loginButton.setOnClickListener {
                viewModel.login()
            }
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.handle(authResult, requireActivity())
            it.update(binding.loginButton, binding.progressBar, binding.errorTextView)
        }

        viewModel.init(savedInstanceState == null)
    }

}