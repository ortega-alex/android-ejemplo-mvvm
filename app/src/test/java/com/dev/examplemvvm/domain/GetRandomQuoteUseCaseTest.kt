package com.dev.examplemvvm.domain

import com.dev.examplemvvm.data.QuoteRepository
import com.dev.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetRandomQuoteUseCaseTest {
    @RelaxedMockK
    private lateinit var quotesRepository: QuoteRepository

    lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getRandomQuoteUseCase = GetRandomQuoteUseCase(quotesRepository)
    }

    @Test
    fun `when database is empty then return null`() = runBlocking{
        //Given
        coEvery { quotesRepository.getAllQuotesFromDatabase() } returns  emptyList()

        // When
        val response = getRandomQuoteUseCase()

        // Then
        assert(response == null)
    }

    @Test
    fun `when database is not empty then return quote`() = runBlocking {
        //Given
        val quoteList = listOf(Quote("Holi", "Aristidevs"))
        coEvery { quotesRepository.getAllQuotesFromDatabase() } returns quoteList

        // When
        val response = getRandomQuoteUseCase()

        //Then
        assert(response == quoteList.first())
    }
}