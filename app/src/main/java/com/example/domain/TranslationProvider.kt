package com.example.domain

interface TranslationProvider {
    suspend fun translate(text: String, targetLanguage: String): String
}
