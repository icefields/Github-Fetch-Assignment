package org.scotia.githubfetcher.data.mock

import org.scotia.githubfetcher.data.REAL_USER_ID
import org.scotia.githubfetcher.data.dto.RepositoryDto
import org.scotia.githubfetcher.data.dto.UserDto
import java.util.UUID

object MocksDto {
    fun mockGithubUserDto(username: String = UUID.randomUUID().toString()) = UserDto(
        login = username,
        name = "antonio",
        avatarUrl = "https://private-user-images.githubusercontent.com/149625124/300672209-7614a6a7-898b-4a8c-b891-9a07505529be.png"
    )

    fun mockRepositoryDto(
        repoName: String = UUID.randomUUID().toString(),
        ownerId: String = UUID.randomUUID().toString()
    ) = RepositoryDto(
        owner = mockGithubUserDto(username = ownerId),
        name = repoName,
        description = "mock repo with name $repoName",
        updatedAt = "2023-11-13T19:32:57Z",
        stargazersCount = Math.random().toInt(),
        forks = Math.random().toInt()
    )

    fun mockReposDto() = listOf(
        mockRepositoryDto(repoName = "repo name one", ownerId = REAL_USER_ID),
        mockRepositoryDto(),
        mockRepositoryDto(),
        mockRepositoryDto(),
        mockRepositoryDto()
    )

}