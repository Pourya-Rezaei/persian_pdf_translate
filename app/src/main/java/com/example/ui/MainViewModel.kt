package com.example.ui

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.TranslatorApp
import com.example.data.EdgeGalleryProvider
import com.example.data.GeminiProvider
import com.example.data.OpenAiProvider
import com.example.data.TranslationSettings
import com.example.domain.TranslationProvider
import com.example.util.PdfUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsDao = (application as TranslatorApp).database.settingsDao()
    
    val settings: StateFlow<TranslationSettings?> = settingsDao.getSettings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _selectedPdfUri = MutableStateFlow<Uri?>(null)
    val selectedPdfUri = _selectedPdfUri.asStateFlow()

    private val _pageCount = MutableStateFlow(0)
    val pageCount = _pageCount.asStateFlow()

    private val _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()

    private val _originalText = MutableStateFlow("")
    val originalText = _originalText.asStateFlow()

    private val _translatedText = MutableStateFlow("")
    val translatedText = _translatedText.asStateFlow()

    private val _isTranslating = MutableStateFlow(false)
    val isTranslating = _isTranslating.asStateFlow()

    fun saveSettings(newSettings: TranslationSettings) {
        viewModelScope.launch {
            settingsDao.saveSettings(newSettings)
        }
    }

    fun selectPdf(uri: Uri) {
        viewModelScope.launch {
            try {
                getApplication<Application>().contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            _selectedPdfUri.value = uri
            _isTranslating.value = true // Neutral loading state
            val count = withContext(Dispatchers.IO) {
                PdfUtil.getPageCount(getApplication(), uri)
            }
            _pageCount.value = count
            loadPage(0)
            _isTranslating.value = false
        }
    }

    fun loadPage(index: Int) {
        val uri = _selectedPdfUri.value ?: return
        viewModelScope.launch {
            if (index < 0 || index >= _pageCount.value) return@launch
            
            _currentPage.value = index
            _translatedText.value = ""
            _isTranslating.value = true
            val text = withContext(Dispatchers.IO) {
                PdfUtil.extractTextFromPage(getApplication(), uri, index)
            }
            _originalText.value = text
            _isTranslating.value = false
        }
    }

    fun translateCurrentPage() {
        val currentSettings = settings.value ?: return
        val text = _originalText.value
        if (text.isBlank()) return

        _isTranslating.value = true
        viewModelScope.launch {
            val provider: TranslationProvider = when (currentSettings.provider) {
                "Gemini" -> GeminiProvider(currentSettings.geminiApiKey, currentSettings.modelName)
                "OpenAI" -> OpenAiProvider(currentSettings.openAiApiKey, "https://api.openai.com/v1/", currentSettings.modelName)
                "OpenRouter" -> OpenAiProvider(currentSettings.openRouterApiKey, "https://openrouter.ai/api/v1/", currentSettings.modelName)
                "AI Edge / Local" -> OpenAiProvider("", currentSettings.localApiUrl, currentSettings.modelName)
                "AI Edge (SDK)" -> EdgeGalleryProvider(getApplication(), currentSettings.modelPath)
                else -> GeminiProvider(currentSettings.geminiApiKey, currentSettings.modelName)
            }

            val translation = provider.translate(text, currentSettings.targetLanguage)
            _translatedText.value = translation
            _isTranslating.value = false
        }
    }
}
