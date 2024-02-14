package com.bbbrrr8877.totalrecall.cardsList.data

import android.util.Log
import java.util.Calendar

interface LearningOrder {

    fun calculateOrder(order: Int, date: Long): Boolean

    class Base : LearningOrder {
        private val currentDate = Calendar.getInstance().timeInMillis

        override fun calculateOrder(order: Int, date: Long): Boolean {
            Log.d("Bulat", "Current data: $currentDate")
            return when (order) {
                0 -> true
                1 -> currentDate - date >= MIN_30
                2 -> currentDate - date >= DAY_1
                3 -> currentDate - date >= DAYS_3
                4 -> currentDate - date >= DAYS_10
                5 -> currentDate - date >= MONTH_1
                6 -> currentDate - date >= MONTHS_3
                7 -> currentDate - date >= MONTHS_6
                8 -> currentDate - date >= MONTHS_12
                else -> false
            }
        }
    }

    companion object {
        private const val MIN_30 = 1_800_000
        private const val DAY_1 = 86_400_000
        private const val DAYS_3 = 259_200_000
        private const val DAYS_10 = 864_000_000
        private const val MONTH_1 = 2_629_800_000
        private const val MONTHS_3 = 7_889_400_000
        private const val MONTHS_6 = 15_778_800_000
        private const val MONTHS_12 = 31_557_600_000
    }
}