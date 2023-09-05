package com.emirhanuyunmaz.kotlincountries.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Country(

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var countryName:String?,

    @ColumnInfo(name = "region")
    @SerializedName("region")
    var countryRegion:String?,

    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    var countryCapital:String?,

    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    var countryCurrency:String?,

    @ColumnInfo(name = "language")
    @SerializedName("language")
    var countryLanguage:String?,

    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    var imageURL :String?,
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int=0
}