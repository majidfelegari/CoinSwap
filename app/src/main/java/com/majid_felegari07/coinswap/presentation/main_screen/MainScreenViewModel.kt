package com.majid_felegari07.coinswap.presentation.main_screen

import android.icu.text.DecimalFormat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majid_felegari07.coinswap.domain.model.Resource
import com.majid_felegari07.coinswap.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val repository: CurrencyRepository
) : ViewModel(){

    var state by mutableStateOf(MainScreenState())
    init {
        getCurrencyRatesList()
    }

    fun  onEvent(event: MainScreenEvent){
        when(event) {
            MainScreenEvent.FromCurrencySelect -> {
                state = state.copy(selection = SelectionState.FROM)
            }
            MainScreenEvent.ToCurrencySelect -> {
                state = state.copy(selection = SelectionState.TO)
            }
            MainScreenEvent.SwapIconClicked -> {
                state = state.copy(
                    fromCurrencyCode = state.toCurrencyCode,
                    fromCurrencyValue = state.toCurrencyValue,
                    toCurrencyCode = state.fromCurrencyCode,
                    toCurrencyValue = state.fromCurrencyValue
                )
            }
            is MainScreenEvent.NumberButtonClicked -> {
                updateCurrencyValue(value = event.value)
            }
            is MainScreenEvent.BottomSheetItemClicked -> TODO()
        }
    }

    private fun getCurrencyRatesList() {
        viewModelScope.launch {
            repository
                .getCurrencyRatesList()
                .collectLatest { result ->
                    when(result) {
                        is Resource.Error -> {
                            state.copy(
                                currencyRates = result.data?.associateBy { it.code } ?: emptyMap(),
                                error = null
                            )
                        }
                        is Resource.Success -> {
                            state.copy(
                                currencyRates = result.data?.associateBy { it.code } ?: emptyMap(),
                                error = result.message
                            )
                        }
                    }
                }
        }
    }
    private fun updateCurrencyValue(value: String) {
        val currentCurrencyValue = when(state.selection) {
            SelectionState.FROM -> state.fromCurrencyValue
            SelectionState.TO -> state.toCurrencyValue
        }
        val fromCurrencyRate = state.currencyRates[state.fromCurrencyValue]?.rate ?: 0.0
        val toCurrencyRate = state.currencyRates[state.toCurrencyValue]?.rate ?: 0.0

        val updatedCurrencyValue = when(value) {
            "C" -> "0.00"
            else -> if (currentCurrencyValue == "0.00") value else currentCurrencyValue + value
        }

        val numberFormat = DecimalFormat("#.00")

        when(state.selection) {
            SelectionState.FROM -> {
                val fromValue = updatedCurrencyValue.toDoubleOrNull() ?: 0.0
                val toValue = fromValue / fromCurrencyRate * toCurrencyRate
                state = state.copy(
                    fromCurrencyValue = updatedCurrencyValue,
                    toCurrencyValue = numberFormat.format(toValue)
                )
            }
            SelectionState.TO -> {
                val toValue = updatedCurrencyValue.toDoubleOrNull() ?: 0.0
                val fromValue = toValue / toCurrencyRate * fromCurrencyRate
                state = state.copy(
                    fromCurrencyValue = updatedCurrencyValue,
                    toCurrencyValue = numberFormat.format(fromValue)
                )
            }
        }
    }
}