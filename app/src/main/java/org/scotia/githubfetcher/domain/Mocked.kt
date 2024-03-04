package org.scotia.githubfetcher.domain

import org.scotia.githubfetcher.domain.models.GithubUser
import org.scotia.githubfetcher.domain.models.Repository
import java.lang.Math.random
import java.util.UUID

// used for previews and for testing (this function must reside here and not in testing to be
// visible by previews)
fun mockGithubUser(username: String = UUID.randomUUID().toString()) = GithubUser(
    id = username,
    name = "antonio",
    avatarUrl = "https://private-user-images.githubusercontent.com/149625124/300672209-7614a6a7-898b-4a8c-b891-9a07505529be.png"
)

fun mockRepository(
    repoName: String = UUID.randomUUID().toString(),
    ownerId: String = UUID.randomUUID().toString()
) = Repository(
    ownerId = ownerId,
    name = repoName,
    description = "mock repo with name $repoName",
    updatedAt = "2023-11-13T19:32:57Z",
    stargazersCount = random().toInt(),
    forks = random().toInt()
)

fun mockRepos() = listOf(
    mockRepository(repoName = "repo name one"),
    mockRepository(),
    mockRepository(),
    mockRepository(),
    mockRepository()
)
