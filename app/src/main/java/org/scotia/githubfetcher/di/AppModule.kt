package org.scotia.githubfetcher.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import org.scotia.githubfetcher.data.MainNetwork
import org.scotia.githubfetcher.data.MainNetwork.Companion.BASE_URL
import org.scotia.githubfetcher.domain.GithubDataRepository
import org.scotia.githubfetcher.domain.use_case.GetRepos
import org.scotia.githubfetcher.domain.use_case.GetRepository
import org.scotia.githubfetcher.domain.use_case.GetUser
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): MainNetwork =
        retrofit.create(MainNetwork::class.java)

    @Provides
    @Singleton
    fun provideGetUserUseCase(repository: GithubDataRepository) =
        GetUser(repository)

    @Provides
    @Singleton
    fun provideGetReposUseCase(repository: GithubDataRepository) =
        GetRepos(repository)

    @Provides
    @Singleton
    fun provideGetRepositoryUseCase(repository: GithubDataRepository) =
        GetRepository(repository)
}
