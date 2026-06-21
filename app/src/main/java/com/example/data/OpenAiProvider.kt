package com.example.data

import com.example.domain.TranslationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class OpenAiProvider(
    private val apiKey: String,
    private val baseUrl: String,
    private val modelName: String
) : TranslationProvider {

    private val client = OkHttpClient()

    override suspend fun translate(text: String, targetLanguage: String): String {
        return withContext(Dispatchers.IO) {
            try {
                if (apiKey.isBlank() && !baseUrl.contains("localhost")) return@withContext "API Key is missing."

                val url = if (baseUrl.endsWith("/")) "${baseUrl}chat/completions" else "$baseUrl/chat/completions"
                
                val json = JSONObject().apply {
                    put("model", modelName)
                    put("messages", JSONArray().apply {
                        put(JSONObject().apply {
                            put("role", "system")
                            put("content", "You are a professional translator. Translate provide text to $targetLanguage. Only return the translation.")
                        })
                        put(JSONObject().apply {
                            put("role", "user")
                            put("content", text)
                        })
                    })
                }

                val body = json.toString().toRequestBody("application/json".toMediaType())
                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .apply {
                        if (apiKey.isNotBlank()) {
                            addHeader("Authorization", "Bearer $apiKey")
                        }
                    }
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) return@withContext "Error: ${response.code} ${response.message}"
                    
                    val responseData = response.body?.string() ?: return@withContext "Empty response"
                    val responseJson = JSONObject(responseData)
                    responseJson.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                }
            } catch (e: Exception) {
                "Error: ${e.message}"
            }
        }
    }
}
