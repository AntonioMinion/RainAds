package com.rainads.rainadsapp.util

enum class AdActivityStatus(private val caseSensitive: String) {
    PLAYING("Playing"),
    PAUSED("Paused");

    override fun toString(): String {
        return caseSensitive
    }
}