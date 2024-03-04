package org.scotia.githubfetcher.domain

import kotlinx.coroutines.flow.Flow
import org.scotia.githubfetcher.common.Resource
import org.scotia.githubfetcher.domain.models.GithubUser
import org.scotia.githubfetcher.domain.models.Repository

interface GithubDataRepository {
    suspend fun getUserData(username: String): Flow<Resource<GithubUser>>
    suspend fun getRepos(username: String): Flow<Resource<List<Repository>>>
    suspend fun getRepository(username: String, repoName: String): Flow<Resource<Repository>>
}
