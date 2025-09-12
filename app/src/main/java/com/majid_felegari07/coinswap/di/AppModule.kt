package com.majid_felegari07.coinswap.di

import android.app.Application
import androidx.room.Room
import com.majid_felegari07.coinswap.data.local.CurrencyRateDatabase
import com.majid_felegari07.coinswap.data.remote.CurrencyApi
import com.majid_felegari07.coinswap.data.repository.CurrencyRepositoryImpl
import com.majid_felegari07.coinswap.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCurrencyApi(): CurrencyApi {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(CurrencyApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CurrencyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(application: Application): CurrencyRateDatabase {
        return Room.databaseBuilder(
                application,
                CurrencyRateDatabase::class.java,
                "currency_db"
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRepository(
        api: CurrencyApi,
        db: CurrencyRateDatabase
    ) : CurrencyRepository {
        return CurrencyRepositoryImpl(
            api = api,
            dao = db.currencyRateDao
        )
    }
}