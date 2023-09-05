package com.emirhanuyunmaz.kotlincountries.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CountrySharedPreferences {

    companion object{
        private val PREFERENCES_TIME="preferences_time"

        private var sharedPreferences : SharedPreferences? = null

        @Volatile private var instance : CountrySharedPreferences? = null

        private val lock=Any()

        operator fun invoke(context: Context) : CountrySharedPreferences = instance ?: synchronized(lock){
            instance ?: makeCountrySharedPreferences(context).also {
                instance=it
            }
        }

        private fun makeCountrySharedPreferences(context: Context) : CountrySharedPreferences{
            sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context)
            return CountrySharedPreferences()
        }
    }

    fun saveTime(time:Long){
        sharedPreferences?.edit(commit = true){
            putLong(PREFERENCES_TIME,time)
        }
    }

    fun getTime() = sharedPreferences?.getLong(PREFERENCES_TIME,0)

}