package com.catap.popcorn.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


//Serializable class that describe one Movie object from api
@Parcelize
data class TmdbMovie(
    val id: String,
    val original_title: String,
    val poster_path: String?,  // EX: "/k8Q9ulyRE8fkvZMkAM9LPYMKctb.jpg"
    val release_date: String?, //EX: "1997-11-18"
    val vote_average: String, //EX: 78.104
    val popularity: String,   //EX: 78.435
    val adult: String,
    val overview: String,
    val original_language: String
) : Parcelable {

}