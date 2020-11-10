package com.shivamkumarjha.weatherdemo.utility

class Utility {
    private val tag = "Utility"

    companion object {
        private var INSTANCE: Utility? = null

        fun get(): Utility {
            if (INSTANCE == null) {
                INSTANCE = Utility()
            }
            return INSTANCE!!
        }
    }
}