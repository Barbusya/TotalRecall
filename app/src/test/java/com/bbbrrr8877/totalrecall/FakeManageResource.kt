package com.bbbrrr8877.totalrecall

import com.bbbrrr8877.totalrecall.core.ManageResource

class FakeManageResource(private val string: String) : ManageResource {
    override fun string(id: Int): String = string
}