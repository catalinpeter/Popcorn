package com.catap.popcorn.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.catap.popcorn.data.TmdbRepository

class MainViewModel @ViewModelInject constructor(private val tmdbRepository: TmdbRepository) :
    ViewModel() {
    //  LiveData  trandingMovie used in MainFragment
    val trandingMovies = tmdbRepository.getTrandingMovies().cachedIn(viewModelScope)

}