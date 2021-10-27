package com.catap.popcorn.networkapi

import com.catap.popcorn.data.TmdbRespons
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {

    //companion object that holds information related to api interface
    companion object {

        //TheMovieDb API Key
        const val API_KEY = "Your TheMovieDb API Key"
        const val BASE_URL = "https://api.themoviedb.org/3/"

        const val IMAGE_URL_92 = "https://image.tmdb.org/t/p/w92"
        const val IMAGE_URL_185 = "https://image.tmdb.org/t/p/w185"
        const val IMAGE_URL_500 = "https://image.tmdb.org/t/p/w500"
        const val IMAGE_URL_780 = "https://image.tmdb.org/t/p/w780"
    }


    //get function that retrive an TmfbRespons object based
    //I also add page query (from api documentation)
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): TmdbRespons

    //get the list with popular movies for main screen
    @GET("movie/popular")
    suspend fun getTrandingMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): TmdbRespons


}