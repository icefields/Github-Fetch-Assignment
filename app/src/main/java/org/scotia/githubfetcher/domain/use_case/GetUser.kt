package org.scotia.githubfetcher.domain.use_case

import org.scotia.githubfetcher.domain.GithubDataRepository

class GetUser(private val dataRepository: GithubDataRepository) {
    suspend operator fun invoke(username: String) =
        dataRepository.getUserData(username)
}
