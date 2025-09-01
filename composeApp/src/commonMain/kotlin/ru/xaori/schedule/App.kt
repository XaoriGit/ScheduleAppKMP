package ru.xaori.schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import ru.xaori.schedule.navigation.Navigation
import ru.xaori.schedule.navigation.Screen
import ru.xaori.schedule.ui.AppTheme
import ru.xaori.schedule.model.AppUiState

@Composable
fun App() {
    KoinApplication({ modules(CommonModule) }) {
        AppTheme {
            val viewModel: AppViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsState()

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Surface(
                    modifier = Modifier.padding(
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Vertical).asPaddingValues()
                    ),
                    color = Color.Transparent
                ) {
                    when(uiState) {
                        is AppUiState.Loading -> {}
                        is AppUiState.Success -> {
                            Navigation(Screen.Schedule.route)
                        }
                        is AppUiState.NewUser -> {
                            Navigation(Screen.Start.route)
                        }
                    }
                }

            }
        }
    }
}