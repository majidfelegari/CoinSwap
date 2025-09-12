package com.majid_felegari07.coinswap.data.repository

import com.majid_felegari07.coinswap.data.local.CurrencyRateDao
import com.majid_felegari07.coinswap.data.local.entity.toCurrencyRate
import com.majid_felegari07.coinswap.data.local.entity.toCurrencyRateEntity
import com.majid_felegari07.coinswap.data.remote.CurrencyApi
import com.majid_felegari07.coinswap.data.remote.dto.toCurrencyRates
import com.majid_felegari07.coinswap.domain.model.CurrencyRate
import com.majid_felegari07.coinswap.domain.model.Resource
import com.majid_felegari07.coinswap.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class CurrencyRepositoryImpl(
    private val api: CurrencyApi,
    private val dao: CurrencyRateDao
): CurrencyRepository {
    override fun getCurrencyRatesList(): Flow<Resource<List<CurrencyRate>>> = flow {
        val localCurrencyRates = getLocalCurrencyRates()
        emit(Resource.Success(localCurrencyRates))

        try {
            val newRates = getRemoteCurrencyRates()
            updateLocalCurrencyRates(newRates)
            emit(Resource.Success(newRates))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "couldn't reach server, check your internet connection",
                    data = localCurrencyRates
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = "oops, something went wrong! ${e.message}",
                    data = localCurrencyRates
                )
            )
        }
    }

    private suspend fun getLocalCurrencyRates(): List<CurrencyRate> {
        return dao.getAllCurrencyRates().map { it.toCurrencyRate() }
    }

    private suspend fun getRemoteCurrencyRates(): List<CurrencyRate> {
        val response = api.getLatestRates()
        return response.toCurrencyRates()
    }

    private suspend fun updateLocalCurrencyRates(currencyRates: List<CurrencyRate>) {
        dao.upsertAll(currencyRates.map { it.toCurrencyRateEntity() })
    }
}