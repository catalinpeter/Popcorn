package com.catap.popcorn.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


//Data class that allows to handle what type of api call i should do in movies fragment (movies that are in trending or a movie searched by the user)
@Parcelize
data class SearchType(
    val trending: Boolean,
    val movie_query: String
) : Parcelable