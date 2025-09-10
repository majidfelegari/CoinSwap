package com.majid_felegari07.coinswap.data.local

import androidx.room.Database
import com.majid_felegari07.coinswap.data.local.entity.CurrencyRateEntity

@Database(
    entities = [CurrencyRateEntity::class],
    version = 1
)
abstract class CurrencyRateDatabase {
    abstract val currencyRateDao: CurrencyRateDao
}