package com.davidmerchan.pressurediary

import androidx.compose.ui.window.ComposeUIViewController
import com.davidmerchan.pressurediary.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initializeKoin()
    }
) { App() }