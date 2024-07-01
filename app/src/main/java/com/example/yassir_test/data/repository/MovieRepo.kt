package com.example.yassir_test.data.repository

import com.example.swiftcart.utils.AuthResult
import com.example.yassir_test.data.model.MovieDetailResponse
import com.example.yassir_test.data.model.MovieListResponse
import com.example.yassir_test.network.ApiService
import javax.inject.Inject

interface MovieRepo {

    suspend fun getTrendingMovie() : AuthResult<MovieListResponse>

    suspend fun getMovieDetails(movieId: Int) : AuthResult<MovieDetailResponse>
}


class MovieRepoImpl @Inject constructor(private val apiService: ApiService) : MovieRepo {
    override suspend fun getTrendingMovie(): AuthResult<MovieListResponse> {
        return try {
            val response = apiService.getTrendingMovie()
            AuthResult.Success(response)
        }catch (e : Exception){
            AuthResult.Error(e)
        }
    }

    override suspend fun getMovieDetails(movieId: Int): AuthResult<MovieDetailResponse> {
        return try {
            val response = apiService.getMovieDetails(movieId)
            AuthResult.Success(response)
        }catch (e: Exception){
            AuthResult.Error(e)
        }
    }
}