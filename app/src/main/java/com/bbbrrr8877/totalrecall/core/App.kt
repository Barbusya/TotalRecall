package com.bbbrrr8877.totalrecall.core

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bbbrrr8877.android.ProvideViewModel

class App : Application(), ProvideViewModel {

    private lateinit var viewModelsFactory: ViewModelsFactory

    override fun onCreate() {
        super.onCreate()
        viewModelsFactory = ViewModelsFactory(DependencyContainer.Base(Core(this)))
    }

    override fun <T : ViewModel> viewModel(owner: ViewModelStoreOwner, className: Class<T>) =
        ViewModelProvider(owner, viewModelsFactory)[className]

}