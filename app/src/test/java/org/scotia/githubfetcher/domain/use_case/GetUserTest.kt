package org.scotia.githubfetcher.domain.use_case

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import org.scotia.githubfetcher.common.Resource
import org.scotia.githubfetcher.data.FakeDataRepository
import org.scotia.githubfetcher.data.REAL_USER_ID
import org.scotia.githubfetcher.domain.GithubDataRepository

class GetUserTest {
    private lateinit var getUser: GetUser
    private lateinit var fakeRepository: GithubDataRepository

    @Before
    fun setUp() {
        fakeRepository = FakeDataRepository()
        getUser = GetUser(fakeRepository)
    }

    @Test
    fun `user from API given user id, user exists`() = runBlocking {
        // testing the first result from the flow, that's all we need for this test
        val result = getUser(REAL_USER_ID).first()
        assertThat(result).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `user from API given user id, user does not exists`() = runBlocking {
        // testing the first result from the flow, that's all we need for this test
        val result = getUser("non-existing-user").first()
        assertThat(result).isInstanceOf(Resource.Error::class.java)
    }
}
