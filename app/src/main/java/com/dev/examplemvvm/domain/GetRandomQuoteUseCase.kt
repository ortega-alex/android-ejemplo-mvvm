package com.dev.examplemvvm.domain

import com.dev.examplemvvm.data.QuoteRepository
import com.dev.examplemvvm.domain.model.Quote
import javax.inject.Inject

class GetRandomQuoteUseCase @Inject constructor(
    private val repository: QuoteRepository
) {

    suspend operator fun invoke(): Quote? {
        val quotes = repository.getAllQuotesFromDatabase()
        if (!quotes.isNullOrEmpty()) {
            val randomNumber: Int = (quotes.indices).random()
            return quotes[randomNumber]
        }
        return null
    }
}