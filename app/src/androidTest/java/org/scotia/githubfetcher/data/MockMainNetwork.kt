package org.scotia.githubfetcher.data

import org.scotia.githubfetcher.data.dto.UserDto
import org.scotia.githubfetcher.data.mock.MocksDto.mockGithubUserDto
import org.scotia.githubfetcher.data.mock.MocksDto.mockReposDto
import org.scotia.githubfetcher.data.mock.MocksDto.mockRepositoryDto
import kotlin.jvm.Throws

const val REAL_USER_ID = "icefields"

/**
 * Ideally write an interceptor in Retrofit and return mocked data instead of completely making
 * up the responses here
 */
class MockMainNetwork() : MainNetwork {
    private val backendUsers = mutableListOf<UserDto>().apply {
        ('a'..'z').forEach {
            add(mockGithubUserDto(it.toString()))
        }
        add(mockGithubUserDto(REAL_USER_ID))
    }

    private val backendRepos = mockReposDto().toMutableList()
        .apply {
            // adding real user for test purposes
            add(mockRepositoryDto(ownerId = REAL_USER_ID))
        }

    @Throws(Exception::class)
    override suspend fun getUser(username: String) =
        backendUsers.firstOrNull { it.login == username }
            ?: throw Exception("user not found") // TODO create custom exceptions

    override suspend fun getRepos(username: String) =
        backendRepos

    @Throws(Exception::class)
    override suspend fun getRepository(
        username: String,
        repoName: String
    ) = backendRepos.firstOrNull {
        it.owner?.login == username && it.name == repoName
    } ?: throw Exception("repository not found")
}
