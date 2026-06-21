package com.example.data

import com.example.domain.TranslationProvider
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeminiProvider(
    private val apiKey: String,
    private val modelName: String
) : TranslationProvider {

    override suspend fun translate(text: String, targetLanguage: String): String {
        return withContext(Dispatchers.IO) {
            try {
                if (apiKey.isBlank()) return@withContext "API Key is missing."
                
                val model = GenerativeModel(
                    modelName = modelName,
                    apiKey = apiKey
                )
                
                val prompt = "Translate the following text to $targetLanguage. Keep the formatting and style. Only return the translated text:\n\n$text"
                val response = model.generateContent(prompt)
                response.text ?: "No translation returned."
            } catch (e: Exception) {
                "Error: ${e.message}"
            }
        }
    }
}
