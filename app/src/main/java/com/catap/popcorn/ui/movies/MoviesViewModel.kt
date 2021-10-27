package com.catap.popcorn.ui.movies

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.paging.cachedIn
import com.catap.popcorn.Prefs
import com.catap.popcorn.R
import com.catap.popcorn.data.TmdbPagingSource
import com.catap.popcorn.data.TmdbRepository
import com.github.guilhe.sharedprefs.gson.get
import com.github.guilhe.sharedprefs.gson.put
import com.google.gson.reflect.TypeToken


class MoviesViewModel @ViewModelInject constructor(private val tmdbRepository: TmdbRepository) :
    ViewModel() {


    private val currentQuery = MutableLiveData<String>()

    lateinit var listChecktest: TmdbPagingSource.EmptyListCallback

    //triggers every time when currentQuery value was modified and update the value of movies LiveData
    val movies = currentQuery.switchMap { queryString ->
        tmdbRepository.getMovieSearchResults(queryString, listChecktest).cachedIn(viewModelScope)
    }

    //handle movie search
    fun searchMovies(query: String, listCheck: TmdbPagingSource.EmptyListCallback) {
        //modifi the value of the currentQuery
        currentQuery.value = query
        listChecktest = listCheck
    }

    //LiveData<PagingData<TmdbRepository>>
    val trandingMovies = tmdbRepository.getTrandingMovies().cachedIn(viewModelScope)

    // in case a search have results the querry will be aded to shared preferences to be shown when longpress search input text
    fun handleLastTenSearches(query: String) {
        var movieQueryList = Prefs.get(
            R.string.lastTenMoviesKey.toString(),
            object : TypeToken<List<String>>() {},
            mutableListOf()
        ).toMutableList()

        if (!movieQueryList.contains(query))
            if (movieQueryList.size < 10) {
                movieQueryList.add(query)
                Prefs.put(R.string.lastTenMoviesKey.toString(), movieQueryList)
            } else {
                movieQueryList.removeAt(0)
                movieQueryList.add(query)
                Prefs.put(R.string.lastTenMoviesKey.toString(), movieQueryList)
            }


    }

}