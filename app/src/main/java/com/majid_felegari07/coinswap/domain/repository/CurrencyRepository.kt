package com.majid_felegari07.coinswap.domain.repository

import com.majid_felegari07.coinswap.domain.model.CurrencyRate
import com.majid_felegari07.coinswap.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getCurrencyRatesList(): Flow<Resource<List<CurrencyRate>>>
}