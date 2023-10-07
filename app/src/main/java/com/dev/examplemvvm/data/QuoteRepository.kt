package com.dev.examplemvvm.data

import com.dev.examplemvvm.data.database.dao.QuoteDao
import com.dev.examplemvvm.data.database.entities.QuoteEntity
import com.dev.examplemvvm.data.model.QuoteModel
import com.dev.examplemvvm.data.netwoork.QuoteService
import com.dev.examplemvvm.domain.model.Quote
import com.dev.examplemvvm.domain.model.toDomain
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val api: QuoteService,
    private val quoteDao: QuoteDao
) {
    suspend fun getAllQuotesFromApi(): List<Quote> {
        val response: List<QuoteModel> = api.getQoutes()
        return response.map { it.toDomain() }
    }

    suspend fun getAllQuotesFromDatabase(): List<Quote> {
        val response: List<QuoteEntity> = quoteDao.getAllQuote()
        return response.map { it.toDomain() }
    }

    suspend fun insertQuotes(quotes:List<QuoteEntity>) {
        quoteDao.insertAll(quotes)
    }

    suspend fun clearQuotes() {
        quoteDao.deleteAllQuotes()
    }

}