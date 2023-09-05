package com.emirhanuyunmaz.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emirhanuyunmaz.kotlincountries.model.Country
import com.emirhanuyunmaz.kotlincountries.service.CountryDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryViewModel(application: Application) : BaseViewModel(application) {
    val countryLiveData=MutableLiveData<Country>()


    fun getDataFromRoom(uuid:Int){

        CoroutineScope(Dispatchers.IO).launch {
            val dao=CountryDatabase(getApplication()).countryDao()
            val country1= dao.getCountry(uuid)
            withContext(Dispatchers.Main){
                countryLiveData.value=country1

            }
        }

    }
}