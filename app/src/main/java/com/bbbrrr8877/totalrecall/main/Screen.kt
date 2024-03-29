package com.bbbrrr8877.totalrecall.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {

    fun show(fragmentManager: FragmentManager, containerId: Int)

    abstract class Add(private val className: Class<out Fragment>) : Screen {

        override fun show(fragmentManager: FragmentManager, containerId: Int) {
            fragmentManager.beginTransaction()
                .add(containerId, className.getDeclaredConstructor().newInstance())
                .addToBackStack(className.simpleName)
                .commit()
        }
    }

    abstract class Replace(private val className: Class<out Fragment>) : Screen {
        override fun show(fragmentManager: FragmentManager, containerId: Int) {
            fragmentManager.beginTransaction()
                .replace(containerId, className.getDeclaredConstructor().newInstance())
                .commit()
        }
    }

    object Pop : Screen {
        override fun show(fragmentManager: FragmentManager, containerId: Int) =
            fragmentManager.popBackStack()
    }
}