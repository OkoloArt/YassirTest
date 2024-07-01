package com.example.yassir_test.di

import android.content.Context
import com.example.yassir_test.data.repository.MovieRepo
import com.example.yassir_test.data.repository.MovieRepoImpl
import com.example.yassir_test.network.ApiService
import com.example.yassir_test.network.authHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Named
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object DataStoreModule {
//
//    @Provides
//    @Singleton
//    fun provideDataStoreRepository(
//        @ApplicationContext context: Context
//    ) = DataStoreRepo(context = context)
//
//}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepo(apiService: ApiService): MovieRepo {
        return MovieRepoImpl(apiService)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("AuthClient")
    fun provideAuthClient(): HttpClient {
        return authHttpClient
    }

    @Provides
    @Singleton
    fun provideApiService(@Named("AuthClient") httpClient: HttpClient): ApiService {
        return ApiService(httpClient)
    }

}
