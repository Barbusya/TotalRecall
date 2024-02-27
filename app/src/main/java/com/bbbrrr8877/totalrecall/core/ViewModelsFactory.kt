package com.bbbrrr8877.totalrecall.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelsFactory(
    private val dependencyContainer: DependencyContainer
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        dependencyContainer.module(modelClass).viewModel() as T

}