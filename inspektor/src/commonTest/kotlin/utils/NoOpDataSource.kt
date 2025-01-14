package utils

import com.gyanoba.inspektor.data.GetAllLatestWithLimit
import com.gyanoba.inspektor.data.HttpTransaction
import com.gyanoba.inspektor.data.InspektorDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Instant

object NoOpDataSource : InspektorDataSource {
    override suspend fun insertHttpTransaction(httpTransaction: HttpTransaction): Long {
        return 0
    }

    override fun getTransactionFlow(id: Long): Flow<HttpTransaction?> {
        TODO("Not yet implemented")
    }

    override suspend fun getTransaction(id: Long): HttpTransaction {
        TODO("Not yet implemented")
    }

    override suspend fun updateHttpTransaction(httpTransaction: HttpTransaction) {
        // no-op
    }

    override suspend fun getAllLatestHttpTransactionsForDateRange(
        startDate: Instant,
        endDate: Instant,
    ): List<HttpTransaction> {
        return emptyList()
    }

    override fun getAllLatestHttpTransactionsForDateRangeFlow(
        startDate: Instant,
        endDate: Instant,
    ): Flow<List<HttpTransaction>> {
        return flowOf(emptyList())
    }

    override fun getAllLatestHttpTransactionsFilteredFlow(
        startDate: Instant,
        endDate: Instant,
        responseCode: String,
        path: String,
    ): Flow<List<GetAllLatestWithLimit>> {
        return flowOf(emptyList())
    }

    override fun getAllHttpTransactionsCount(): Flow<Long> {
        return flowOf(0)
    }

    override suspend fun deleteBefore(timestamp: Instant) {
        // no-op
    }
}