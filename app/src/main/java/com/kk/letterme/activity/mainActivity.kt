package com.kk.letterme.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
    LoadingPage(onclick = {}, modifier)
}


@Composable
fun LoadingPage(onclick: () -> Unit, modifier: Modifier = Modifier) {


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