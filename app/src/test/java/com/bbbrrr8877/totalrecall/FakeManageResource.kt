package com.bbbrrr8877.totalrecall

import com.bbbrrr8877.android.ManageResource

class FakeManageResource(private val string: String) : ManageResource {
    override fun string(id: Int): String = string
}