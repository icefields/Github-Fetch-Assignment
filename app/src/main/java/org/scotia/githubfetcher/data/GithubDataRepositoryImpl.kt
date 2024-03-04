package org.scotia.githubfetcher.data

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.scotia.githubfetcher.common.Resource
import org.scotia.githubfetcher.data.dto.toGithubUser
import org.scotia.githubfetcher.data.dto.toRepository
import org.scotia.githubfetcher.domain.GithubDataRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubDataRepositoryImpl @Inject constructor(
    private val api: MainNetwork
): GithubDataRepository {

    override suspend fun getUserData(username: String) = flow {
        emit(Resource.Loading(true))
        // emit Resource.Success(cachedData) here
        val user = api.getUser(username).toGithubUser() // will throw exception if user not valid
        emit(Resource.Success(data = user))
        // cache newly fetched data here. To stick to the SOLID principle of "Single Source of
        // Truth" it's better to cache first and then return
        emit(Resource.Loading(false))
    }.catch { e -> emit(Resource.Error(exception = e)) }

    override suspend fun getRepos(username: String) = flow {
        emit(Resource.Loading(true))
        // emit Resource.Success(cachedData) here
        emit(Resource.Success(data = api.getRepos(username).map { it.toRepository() }))
        // cache newly fetched data here. To stick to the SOLID principle of "Single Source of
        // Truth" it's better to cache first and then return
        emit(Resource.Loading(false))
    }.catch { e -> emit(Resource.Error(exception = e)) }

    override suspend fun getRepository(
        username: String,
        repoName: String
    ) = flow {
        emit(Resource.Loading(true))
        // emit Resource.Success(cachedData) here
        emit(Resource.Success(data = api.getRepository(
            username = username,
            repoName = repoName
        ).toRepository()))
        // cache newly fetched data here. To stick to the SOLID principle of "Single Source of
        // Truth" it's better to cache first and then return
        emit(Resource.Loading(false))
    }.catch { e -> emit(Resource.Error(exception = e)) }
}
