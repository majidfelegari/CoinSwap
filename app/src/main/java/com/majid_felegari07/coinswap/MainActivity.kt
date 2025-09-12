package com.majid_felegari07.coinswap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.majid_felegari07.coinswap.presentation.main_screen.MainScreen
import com.majid_felegari07.coinswap.presentation.main_screen.MainScreenViewModel
import com.majid_felegari07.coinswap.presentation.theme.CoinSwapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoinSwapTheme {
                val viewModel: MainScreenViewModel = hiltViewModel()
                MainScreen(
                    state = viewModel.state,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}
