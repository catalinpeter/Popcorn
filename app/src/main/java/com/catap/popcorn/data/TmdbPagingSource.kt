package com.catap.popcorn.data

import androidx.paging.PagingSource
import com.catap.popcorn.networkapi.TmdbApi
import com.catap.popcorn.networkapi.TmdbApi.Companion.API_KEY
import retrofit2.HttpException
import java.io.IOException


//class that  load data from rest api and turn into pages for paging3 library
class TmdbPagingSource(
    private val tmdbApi: TmdbApi,
    private val query: String? = null,
    private val emptyListCallback: EmptyListCallback? = null
) : PagingSource<Int, TmdbMovie>() {


    //make the api request and turn the data into pages
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TmdbMovie> {

        //page that will curerent on (first page will be null for first page loading so we initialize it with 1)
        val page = params.key ?: 1

        // if the query is not null i make an api search call, else i make a get tarnding movies api call
        if (query != null) {
            return try {
                //make the api call and retrive list of results
                val movies = tmdbApi.searchMovies(API_KEY, query, page).results

                if (page == 1 && movies.isEmpty()) emptyListCallback?.checkResultStatus(query, true)
                else emptyListCallback?.checkResultStatus(query, false)
                //return one page of results
                LoadResult.Page(
                    data = movies,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (movies.isEmpty()) null else page + 1

                )

            } catch (exception: IOException) {
                LoadResult.Error(exception)
            } catch (exception: HttpException) {
                LoadResult.Error(exception)
            }
        } else {
            return try {
                //make the api call and retrive list of results
                val movies = tmdbApi.getTrandingMovies(API_KEY, page).results
                //return one page of results
                LoadResult.Page(
                    data = movies,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (movies.isEmpty()) null else page + 1
                )

            } catch (exception: IOException) {
                LoadResult.Error(exception)
            } catch (exception: HttpException) {
                LoadResult.Error(exception)
            }
        }

    }

    //used when a search is made for displaing an alert when the search has no result, or to store te query String in SharedPrefes
    interface EmptyListCallback {
        fun checkResultStatus(query: String, isResultEmpty: Boolean)
    }

}