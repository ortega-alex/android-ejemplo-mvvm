package com.dev.examplemvvm.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dev.examplemvvm.domain.GetQuotesUseCase
import com.dev.examplemvvm.domain.GetRandomQuoteUseCase
import com.dev.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class QuoteViewModelTest {
    @RelaxedMockK
    private lateinit var getQuotesUseCase: GetQuotesUseCase

    @RelaxedMockK
    private lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    private lateinit var quoteViewModel: QuoteViewModel

    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        quoteViewModel = QuoteViewModel(getQuotesUseCase, getRandomQuoteUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created at the first time, get all quotes and set the first value`() =
        runTest {
            //Given
            val quotesList = listOf(Quote("Holi", "Aristi"), Quote("dame un like", "otro aris"))
            coEvery { getQuotesUseCase() } returns quotesList

            // When
            quoteViewModel.onCreate()

            //Then
            assert(quoteViewModel.quoteModel.value == quotesList.first())
        }

    @Test
    fun `when reandomQuoteUseCase return a quote set on the livedata`() = runTest {
        //Give
        val quote = Quote("ya no se que ejemplo poner", "aris")
        coEvery { getRandomQuoteUseCase() } returns quote

        //When
        quoteViewModel.randomQuote()

        //Then
        assert(quoteViewModel.quoteModel.value == quote)
    }

    @Test
    fun `if reandomQuoteUseCase return null keep the last value`() = runTest {
        //Give
        val quote = Quote("ya no se que ejemplo poner", "aris")
        quoteViewModel.quoteModel.value = quote
        coEvery { getRandomQuoteUseCase() } returns null

        //When
        quoteViewModel.randomQuote()

        //Then
        assert(quoteViewModel.quoteModel.value == quote)
    }
}