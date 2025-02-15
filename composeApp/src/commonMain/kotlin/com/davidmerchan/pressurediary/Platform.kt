package com.davidmerchan.pressurediary

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform