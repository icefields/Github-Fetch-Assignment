package org.scotia.githubfetcher.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.scotia.githubfetcher.data.GithubDataRepositoryImpl
import org.scotia.githubfetcher.domain.GithubDataRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindGithubDataRepository(
        githubDataRepositoryImpl: GithubDataRepositoryImpl
    ): GithubDataRepository
}

