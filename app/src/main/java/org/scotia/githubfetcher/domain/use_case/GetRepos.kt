package org.scotia.githubfetcher.domain.use_case

import org.scotia.githubfetcher.domain.GithubDataRepository

class GetRepos(private val dataRepository: GithubDataRepository) {
    suspend operator fun invoke(username: String) =
        dataRepository.getRepos(username)
}
