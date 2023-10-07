package com.dev.examplemvvm.domain

import com.dev.examplemvvm.data.QuoteRepository
import com.dev.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetQuotesUseCaseTest {

    @RelaxedMockK
    private lateinit var quotesRepository: QuoteRepository

    lateinit var getQuotesUseCase: GetQuotesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getQuotesUseCase = GetQuotesUseCase(quotesRepository)
    }

    @Test
    fun `when the api doesnt return anything the get values from database`() = runBlocking {
        // Given
        coEvery { quotesRepository.getAllQuotesFromApi() } returns emptyList() // cuenado es una corutina se usa en coEvery de lo contrario es every

        // when
        getQuotesUseCase()

        // then
        coVerify(exactly = 1) { quotesRepository.getAllQuotesFromDatabase() }
    }

    @Test
    fun `when the api return something then get values from api`() = runBlocking {
        // Given
        val myList = listOf(Quote("suscribete", "aristidevs"))
        coEvery { quotesRepository.getAllQuotesFromApi() } returns  myList

        // When
        val response = getQuotesUseCase()

        // Then
        coVerify (exactly = 1) { quotesRepository.clearQuotes() }
        coVerify (exactly = 1) { quotesRepository.insertQuotes(any()) }
        coVerify(exactly = 0) { quotesRepository.getAllQuotesFromDatabase()}
        assert(myList == response)
    }
}