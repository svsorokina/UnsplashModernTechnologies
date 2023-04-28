package ru.sorokina.unsplash.modern.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.sorokina.unsplash.modern.R
import ru.sorokina.unsplash.modern.navigation.AppNavHost
import ru.sorokina.unsplash.modern.ui.theme.UnsplashTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_UnsplashModernTechnologies)
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = useDarkIcons)
            }

            WindowCompat.setDecorFitsSystemWindows(window, false)

            val navController = rememberNavController()

            UnsplashTheme {
                AppNavHost(
                    navController = navController,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}