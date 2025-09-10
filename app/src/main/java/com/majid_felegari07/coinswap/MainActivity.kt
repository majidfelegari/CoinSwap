package com.majid_felegari07.coinswap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.majid_felegari07.coinswap.presentation.main_screen.MainScreen
import com.majid_felegari07.coinswap.presentation.theme.CoinSwapTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoinSwapTheme {
                MainScreen()
            }
        }
    }
}
