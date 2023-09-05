package com.emirhanuyunmaz.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.emirhanuyunmaz.kotlincountries.model.Country

@Dao
interface CountryDAO {

    @Insert
    fun insertAll(vararg country: Country):List<Long>

    @Query("SELECT * FROM Country")
    fun getAllCountries():List<Country>

    @Query("SELECT * FROM Country WHERE uuid = :id ")
    fun getCountry(id:Int):Country

    @Query("DELETE FROM Country")
    fun deleteAllCountries()


}