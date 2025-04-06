package com.kk.letterme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kk.letterme.ui.screen.MainApp
import com.kk.letterme.ui.theme.LetterMeTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent() {
            LetterMeTheme {

                MainApp()
//
            }
        }

    }

}