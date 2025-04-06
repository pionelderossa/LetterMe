package com.kk.letterme.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color


@Composable
fun MainApp() {

    val modifier: Modifier =
        Modifier
            .fillMaxSize()
            .padding(all = 10.dp) // a common layout for all pages

    var shouldShowRegisterPage by rememberSaveable { mutableStateOf(false) }
    val showRegisterPage: () -> Unit  = {shouldShowRegisterPage = !shouldShowRegisterPage}

    if (shouldShowRegisterPage) RegistrationScreen()
    else LoadingPage(modifier, onclick = showRegisterPage)
}


@Composable
fun LoadingPage(modifier: Modifier = Modifier, onclick: () -> Unit = {}) {


    Surface (
        color = Color.White,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Align children horizontally
            verticalArrangement = Arrangement.Center // Align children vertically

        ) {

            val commonModifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp)

            Surface(color = Color.Green) {
                Text(
                    "Welcome to LetterMe",
                    textAlign = TextAlign.Center,
                    modifier = commonModifier
                )
            }

            Button(
                onClick = onclick,
                modifier = commonModifier
            ) {

                Text("Click to Continue")
            }
        }
    }

}