package org.scotia.githubfetcher.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.scotia.githubfetcher.data.MainNetwork
import org.scotia.githubfetcher.data.MockMainNetwork
import org.scotia.githubfetcher.domain.GithubDataRepository
import org.scotia.githubfetcher.domain.use_case.GetRepos
import org.scotia.githubfetcher.domain.use_case.GetRepository
import org.scotia.githubfetcher.domain.use_case.GetUser
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    /** TODO
     * Ideally write an interceptor in Retrofit and make it return mocked data instead of
     * completely making up the responses in MockMainNetwork
     */
//    @Provides
//    @Singleton
//    fun provideRetrofit(): Retrofit {
//        val okHttpClient = OkHttpClient.Builder()
//            .retryOnConnectionFailure(true)
//            .build()
//
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }

    @Provides
    @Singleton
    fun provideApi(): MainNetwork =
        MockMainNetwork()

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
