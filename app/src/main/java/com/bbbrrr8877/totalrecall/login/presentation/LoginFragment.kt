package com.bbbrrr8877.totalrecall.login.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.BaseFragment

class LoginFragment : BaseFragment<LoginViewModel>(R.layout.fragment_login) {

    override val viewModelClass = LoginViewModel::class.java

    private val authResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.handleResult(AuthResultWrapper.Base(it))
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar = view.findViewById<View>(R.id.progressBar)
        val errorTextView = view.findViewById<TextView>(R.id.errorTextView)
        val loginButton = view.findViewById<View>(R.id.loginButton)

        viewModel.observe(this) {
            it.handle(authResult, requireActivity())
            it.update(loginButton, progressBar, errorTextView)
        }

        loginButton.setOnClickListener {
            viewModel.login()
        }

        viewModel.init(savedInstanceState == null)
    }

}