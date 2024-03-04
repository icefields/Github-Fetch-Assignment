package org.scotia.githubfetcher.domain.use_case

import org.scotia.githubfetcher.domain.GithubDataRepository

class GetRepository(private val dataRepository: GithubDataRepository) {
    suspend operator fun invoke(
        username: String,
        repoName: String
    ) = dataRepository.getRepository(username, repoName)
}
