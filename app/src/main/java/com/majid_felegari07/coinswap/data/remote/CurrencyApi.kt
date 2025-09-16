package com.majid_felegari07.coinswap.data.remote

import com.majid_felegari07.coinswap.API_KEY_
import com.majid_felegari07.coinswap.data.remote.dto.CurrencyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("v1/latest")
    suspend fun getLatestRates(
        @Query("apikey") apiKey: String = API_KEY
    ): CurrencyDto

    companion object {
        const val API_KEY = API_KEY_
        const val BASE_URL = "https://api.freecurrencyapi.com/"
    }
}