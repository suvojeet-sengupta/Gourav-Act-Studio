package com.suvojeet.gauravactstudio.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suvojeet.gauravactstudio.R
import com.suvojeet.gauravactstudio.ui.theme.GauravActStudioTheme
import androidx.compose.ui.res.stringResource

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.app_logo_gaurav),
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary, // Use primary color from theme
            fontFamily = FontFamily.Cursive // Or a more elegant font
        )
        Text(
            text = stringResource(R.string.app_logo_act_studio),
            fontSize = 32.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = FontFamily.SansSerif
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppLogoPreview() {
    GauravActStudioTheme {
        AppLogo()
    }
}