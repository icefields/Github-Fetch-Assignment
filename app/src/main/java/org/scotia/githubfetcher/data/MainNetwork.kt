package org.scotia.githubfetcher.data

import org.scotia.githubfetcher.data.dto.RepositoryDto
import org.scotia.githubfetcher.data.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Main network interface
 */
interface MainNetwork {
    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): UserDto

    @GET("users/{username}/repos")
    suspend fun getRepos(
        @Path("username") username: String
    ): List<RepositoryDto>

    @GET("repos/{username}/{repoName}")
    suspend fun getRepository(
        @Path("username") username: String,
        @Path("repoName") repoName: String
    ): RepositoryDto

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}
