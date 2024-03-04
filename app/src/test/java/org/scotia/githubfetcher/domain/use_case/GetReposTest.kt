package org.scotia.githubfetcher.domain.use_case

import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import org.scotia.githubfetcher.common.Resource
import org.scotia.githubfetcher.data.FakeDataRepository
import org.scotia.githubfetcher.data.REAL_USER_ID
import org.scotia.githubfetcher.domain.GithubDataRepository

class GetReposTest {
    private lateinit var getRepos: GetRepos
    private lateinit var fakeRepository: GithubDataRepository

    @Before
    fun setUp() {
        fakeRepository = FakeDataRepository()
        getRepos = GetRepos(fakeRepository)
    }

    @Test
    fun `repos from API given user id, user exists`() = runBlocking {
        // testing the first result from the flow, that's all we need for this test
        val result = getRepos(REAL_USER_ID).first()
        Truth.assertThat(result).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `repos from API given user id, user does not exists`() = runBlocking {
        // testing the first result from the flow, that's all we need for this test
        val result = getRepos("non-existing-user").first()
        Truth.assertThat(result).isInstanceOf(Resource.Error::class.java)
    }
}
