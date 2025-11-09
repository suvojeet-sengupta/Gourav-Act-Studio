package com.suvojeet.gauravactstudio

import android.content.res.Configuration
import java.util.Locale
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.suvojeet.gauravactstudio.ui.screens.WelcomeScreen
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme
import com.suvojeet.gauravactstudio.util.Prefs

class MainActivity : ComponentActivity() {

    private var showWelcomeScreen by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedLanguage = Prefs.getLanguage(this)
        if (selectedLanguage != null) {
            setLocale(selectedLanguage)
        }

        showWelcomeScreen = Prefs.isFirstLaunch(this)

        setContent {
            GauravActStudioTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    if (showWelcomeScreen) {
                        WelcomeScreen(onCompletion = {
                            Prefs.setFirstLaunch(this, false)
                            showWelcomeScreen = false
                            recreate()
                        })
                    } else {
                        MainScreen()
                    }
                }
            }
        }
    }

    fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}