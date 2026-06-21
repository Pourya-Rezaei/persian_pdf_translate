package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "translation_settings")
@Serializable
data class TranslationSettings(
    @PrimaryKey val id: Int = 1,
    val provider: String = "Gemini", // Gemini, OpenAI, OpenRouter, Local
    val geminiApiKey: String = "",
    val openAiApiKey: String = "",
    val openRouterApiKey: String = "",
    val localApiUrl: String = "http://localhost:11434", // Default for Ollama/Edge Studio
    val modelPath: String = "", // For MediaPipe .bin file
    val modelName: String = "gemini-1.5-flash",
    val targetLanguage: String = "Persian"
)
