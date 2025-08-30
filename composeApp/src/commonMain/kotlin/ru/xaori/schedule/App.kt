package ru.xaori.schedule

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.KoinApplication
import org.koin.dsl.module
import ru.xaori.schedule.navigation.Screen
import ru.xaori.schedule.ui.AppTheme

@Composable
fun App() {
    KoinApplication({ module { CommonModule } }) {
        AppTheme {
            val navController = rememberNavController()

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Vertical).asPaddingValues()
                    ),
                color = MaterialTheme.colorScheme.background
            ) {
                NavHost(navController = navController, startDestination = Screen.Schedule) {
                    composable(Screen.Schedule.route) {

                    }
                    composable(Screen.ChangeClient.route) {

                    }
                }
            }
        }
    }
}