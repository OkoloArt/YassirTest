package com.example.yassir_test.network

import com.example.swiftcart.utils.ApiException
import com.example.yassir_test.data.model.MovieDetailResponse
import com.example.yassir_test.utils.Constants
import com.example.yassir_test.data.model.MovieListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import javax.inject.Inject
import javax.inject.Named

class ApiService @Inject constructor(@Named("AuthClient")private val client: HttpClient) {

    suspend fun getTrendingMovie(): MovieListResponse {
        val response: HttpResponse = client.get(Constants.BASE_URL) {
            contentType(ContentType.Application.Json)
            parameter("api_key", Constants.API_KEY)
        }

        if (response.status.isSuccess()) {
            return response.body<MovieListResponse>()
        } else {
            throw ApiException(statusCode = response.status.value, "Error fetching movie list")
        }
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetailResponse {
        val response: HttpResponse = client.get("${Constants.BASE_DETAIL_URL} $movieId") {
            contentType(ContentType.Application.Json)
            parameter("api_key", Constants.API_KEY)
        }

        if (response.status.isSuccess()) {
            return response.body<MovieDetailResponse>()
        } else {
            throw ApiException(statusCode = response.status.value, "Error fetching movie details")
        }
    }

}