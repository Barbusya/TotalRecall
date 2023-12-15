package com.bbbrrr8877.totalrecall.core

import androidx.lifecycle.ViewModel

interface Module<T : ViewModel> {
    fun viewModel(): T
}