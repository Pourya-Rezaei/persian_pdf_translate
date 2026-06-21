package com.example.data

import android.content.Context
import com.example.domain.TranslationProvider
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class EdgeGalleryProvider(
    private val context: Context,
    private val modelPath: String
) : TranslationProvider {

    override suspend fun translate(text: String, targetLanguage: String): String = withContext(Dispatchers.IO) {
        try {
            if (modelPath.isBlank()) return@withContext "خطا: مسیر مدل مشخص نشده است. لطفا در تنظیمات مسیر فایل .bin را وارد کنید."
            
            val modelFile = File(modelPath)
            if (!modelFile.exists()) {
                return@withContext "خطا: فایل مدل در مسیر $modelPath یافت نشد."
            }

            val options = LlmInference.LlmInferenceOptions.builder()
                .setModelPath(modelPath)
                .setTemperature(0.2f)
                .build()

            val llmInference = LlmInference.createFromOptions(context, options)
            
            val prompt = "Translate the following text to $targetLanguage. Only return the translation:\n\n$text"
            val result = llmInference.generateResponse(prompt)
            
            result
        } catch (e: Exception) {
            "Edge AI Error: ${e.message}"
        }
    }
}
