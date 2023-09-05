package com.emirhanuyunmaz.kotlincountries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.emirhanuyunmaz.kotlincountries.model.Country
import com.emirhanuyunmaz.kotlincountries.service.CountryAPIService
import com.emirhanuyunmaz.kotlincountries.service.CountryDatabase
import com.emirhanuyunmaz.kotlincountries.util.CountrySharedPreferences
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedViewModel(application: Application) :BaseViewModel(application) {

    private var countryPreferences=CountrySharedPreferences(getApplication())
    private val countryAPIService=CountryAPIService()
    private val disposable= CompositeDisposable()
    private val refreshTime=10 * 60 * 1000 * 1000 * 1000L
    val countries=MutableLiveData<List<Country>>()
    val countryError=MutableLiveData<Boolean>()
    val countryLoading=MutableLiveData<Boolean>()


    fun refreshData(){

        val updateTime=countryPreferences.getTime()

        if(updateTime!=null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){

            getDataFromSQLite()

        }else{
            getDataFromAPI()
        }

    }

    fun refreshAPI(){
        getDataFromAPI()
    }

    private fun getDataFromSQLite(){
        countryLoading.value=true
        CoroutineScope(Dispatchers.IO).launch {

            var countries=CountryDatabase(getApplication()).countryDao().getAllCountries()
          withContext(Dispatchers.Main){
              showCountries(countries)

              Toast.makeText(getApplication(), "Countries from SQLite", Toast.LENGTH_SHORT).show()
          }
        }
    }

    private fun getDataFromAPI(){
        countryLoading.value=true

        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeInCountry(t)
                        //println(t.size)
                        Toast.makeText(getApplication(), "Countries from API", Toast.LENGTH_SHORT).show()

                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value=false
                        countryError.value=true

                    }

                })

        )

    }

    private fun showCountries(countryList:List<Country>){
        countryError.value=false
        countryLoading.value=false
        countries.value=countryList

    }

    private fun storeInCountry(list: List<Country>){
        CoroutineScope(Dispatchers.IO).launch() {
            val dao=CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong=dao.insertAll(*list.toTypedArray())

            var i=0

            while (i<list.size){
                list[i].uuid=listLong[i].toInt()
                i++
            }
            withContext(Dispatchers.Main){
                showCountries(list)
            }


        }
        countryPreferences.saveTime(System.nanoTime())
    }

}