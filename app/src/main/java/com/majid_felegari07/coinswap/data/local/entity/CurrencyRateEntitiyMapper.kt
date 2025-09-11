package com.majid_felegari07.coinswap.data.local.entity

import com.majid_felegari07.coinswap.domain.model.CurrencyRate

fun CurrencyRateEntity.toCurrencyRate(): CurrencyRate {
    return  CurrencyRate (
        code = code,
        name = name,
        rate = rate,
    )
}

fun CurrencyRate.toCurrencyRateEntity(): CurrencyRateEntity {
    return  CurrencyRateEntity (
        code = code,
        name = name,
        rate = rate,
    )
}