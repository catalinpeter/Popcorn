package com.catap.popcorn.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.catap.popcorn.networkapi.TmdbApi
import javax.inject.Inject
// App repository class
class TmdbRepository @Inject constructor(private val tmdbApi: TmdbApi) {

    //will be called by ViewModel .
    fun getMovieSearchResults(query: String, emptyListCallback: TmdbPagingSource.EmptyListCallback) =

        //Primary entry point into Paging; constructor for a reactive stream of PagingData.
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TmdbPagingSource(tmdbApi, query, emptyListCallback) }
        ).liveData //transform into a stream of PagingData exposed as LiveData

    fun getTrandingMovies() =
        //Primary entry point into Paging; constructor for a reactive stream of PagingData.
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TmdbPagingSource(tmdbApi) }
        ).liveData //transform into a stream of PagingData exposed as LiveData

}