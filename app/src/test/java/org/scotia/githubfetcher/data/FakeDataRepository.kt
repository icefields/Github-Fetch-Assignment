package org.scotia.githubfetcher.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.scotia.githubfetcher.common.Resource
import org.scotia.githubfetcher.domain.GithubDataRepository
import org.scotia.githubfetcher.domain.mockGithubUser
import org.scotia.githubfetcher.domain.mockRepos
import org.scotia.githubfetcher.domain.mockRepository
import org.scotia.githubfetcher.domain.models.GithubUser
import org.scotia.githubfetcher.domain.models.Repository

const val REAL_USER_ID = "icefields"

class FakeDataRepository: GithubDataRepository {

    private val backendUsers = mutableListOf<GithubUser>().apply {
        // create a user for every letter of the alphabet
        ('a'..'z').forEach {
            add(mockGithubUser(it.toString()))
        }
        add(mockGithubUser(REAL_USER_ID))
    }

    private val backendRepos = mockRepos().toMutableList()
        .apply {
            // adding real user for test purposes
            add(mockRepository(ownerId = REAL_USER_ID))
        }

    override suspend fun getUserData(username: String): Flow<Resource<GithubUser>> = flow {
        backendUsers.firstOrNull { it.id == username }?.let { user ->
            emit(Resource.Success(user))
        } ?: run {
            emit(Resource.Error(exception = Exception("user does not exist")))
        }
    }

    override suspend fun getRepos(username: String): Flow<Resource<List<Repository>>> = flow {
        emit (
            backendRepos.filter { it.ownerId == username }.let {
                if (it.isNotEmpty()) {
                    Resource.Success(it)
                } else {
                    Resource.Error(exception = Exception("no repos present"))
                }
            }
        )
    }

    override suspend fun getRepository(
        username: String,
        repoName: String
    ): Flow<Resource<Repository>> = flow {
        emit(Resource.Success(mockRepository()))
    }
}
