package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.data.TranslationSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentSettings: TranslationSettings?,
    onSave: (TranslationSettings) -> Unit
) {
    var settings by remember(currentSettings) {
        mutableStateOf(currentSettings ?: TranslationSettings())
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Settings") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onSave(settings) }) {
                Icon(Icons.Default.Save, contentDescription = "Save")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Provider Configuration", style = MaterialTheme.typography.titleMedium)
            
            // Provider Selection
            val providers = listOf("Gemini", "OpenAI", "OpenRouter", "AI Edge / Local", "AI Edge (SDK)")
            var expanded by remember { mutableStateOf(false) }
            
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = settings.provider,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("AI Provider") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    providers.forEach { p ->
                        DropdownMenuItem(
                            text = { Text(p) },
                            onClick = {
                                settings = settings.copy(provider = p)
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = settings.modelName,
                onValueChange = { settings = settings.copy(modelName = it) },
                label = { Text("Model Name") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g. gemini-1.5-flash, gpt-4, etc.") }
            )

            HorizontalDivider()

            when (settings.provider) {
                "Gemini" -> {
                    OutlinedTextField(
                        value = settings.geminiApiKey,
                        onValueChange = { settings = settings.copy(geminiApiKey = it) },
                        label = { Text("Gemini API Key") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                "OpenAI" -> {
                    OutlinedTextField(
                        value = settings.openAiApiKey,
                        onValueChange = { settings = settings.copy(openAiApiKey = it) },
                        label = { Text("OpenAI API Key") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                "OpenRouter" -> {
                    OutlinedTextField(
                        value = settings.openRouterApiKey,
                        onValueChange = { settings = settings.copy(openRouterApiKey = it) },
                        label = { Text("OpenRouter API Key") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                "AI Edge / Local" -> {
                    OutlinedTextField(
                        value = settings.localApiUrl,
                        onValueChange = { settings = settings.copy(localApiUrl = it) },
                        label = { Text("API URL (e.g. Ollama/Edge)") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("http://10.0.2.2:11434/v1") }
                    )
                }
                "AI Edge (SDK)" -> {
                    OutlinedTextField(
                        value = settings.modelPath,
                        onValueChange = { settings = settings.copy(modelPath = it) },
                        label = { Text("Model Path (.bin file)") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("/sdcard/Download/gemma-2b-it.bin") }
                    )
                }
            }

            OutlinedTextField(
                value = settings.targetLanguage,
                onValueChange = { settings = settings.copy(targetLanguage = it) },
                label = { Text("Target Language") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
