package com.example.yahtzeegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yahtzeegame.ui.theme.YahtzeeGameTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            YahtzeeGameTheme {

                val viewModel: YahtzeeViewModel = viewModel()

                YahtzeeScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}