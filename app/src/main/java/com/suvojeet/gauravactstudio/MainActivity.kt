package com.suvojeet.gauravactstudio

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.os.LocaleListCompat
import com.suvojeet.gauravactstudio.data.CloudinaryService
import com.suvojeet.gauravactstudio.ui.screens.WelcomeScreen
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme
import com.suvojeet.gauravactstudio.util.Prefs

class MainActivity : AppCompatActivity() {

    private var showWelcomeScreen by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        CloudinaryService.initialize(this)

        val selectedLanguage = Prefs.getLanguage(this)
        if (selectedLanguage != null) {
            val currentLocales = AppCompatDelegate.getApplicationLocales()
            if (currentLocales.isEmpty || currentLocales.toLanguageTags() != selectedLanguage) {
                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(selectedLanguage)
                AppCompatDelegate.setApplicationLocales(appLocale)
            }
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
}
