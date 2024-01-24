package com.bbbrrr8877.totalrecall.core

import com.bbbrrr8877.totalrecall.topics.presentation.ReloadWithError

interface InitialReloadCallback {

    fun init(reload: ReloadWithError)
}