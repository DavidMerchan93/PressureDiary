package com.davidmerchan.pressurediary

import androidx.compose.runtime.Composable
import com.davidmerchan.pressurediary.presentation.home.HomeScreen
import com.davidmerchan.pressurediary.presentation.theme.PressureDiaryTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    PressureDiaryTheme {
        HomeScreen()
    }
}