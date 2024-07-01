package com.example.yassir_test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftcart.utils.AuthResult
import com.example.yassir_test.data.model.MovieDetailResponse
import com.example.yassir_test.data.model.MovieListResponse
import com.example.yassir_test.data.repository.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepo: MovieRepo): ViewModel(){

    private val _movies = MutableStateFlow<AuthResult<MovieListResponse>>(AuthResult.Loading)
    val movie : StateFlow<AuthResult<MovieListResponse>> = _movies.asStateFlow()

    private val _movieId = MutableLiveData<Int>()
    val movieId: LiveData<Int> = _movieId

    private val _movieDetail = MutableStateFlow<AuthResult<MovieDetailResponse>>(AuthResult.Loading)
    val movieDetail : StateFlow<AuthResult<MovieDetailResponse>> = _movieDetail.asStateFlow()


    init {
        getTrendingMovie()
    }

    private fun getTrendingMovie(){
        viewModelScope.launch {
            val response = movieRepo.getTrendingMovie()
            _movies.value = response
        }
    }

    fun setMovieId(movieId: Int){
        _movieId.value = movieId
    }

    fun getMovieDetails(movieId: Int){
        viewModelScope.launch {
            val response = movieRepo.getMovieDetails(movieId)
            _movieDetail.value = response
        }
    }

}