package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(
    originalText: String,
    translatedText: String,
    isTranslating: Boolean,
    currentPage: Int,
    pageCount: Int,
    onPageChange: (Int) -> Unit,
    onTranslate: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Page ${currentPage + 1} of $pageCount") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ChevronLeft, "Back")
                    }
                },
                actions = {
                    if (isTranslating) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else {
                        IconButton(onClick = onTranslate) {
                            Icon(Icons.Default.Translate, "Translate")
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onPageChange(currentPage - 1) },
                        enabled = currentPage > 0
                    ) {
                        Icon(Icons.Default.ChevronLeft, "Previous")
                    }
                    Text("${currentPage + 1} / $pageCount")
                    IconButton(
                        onClick = { onPageChange(currentPage + 1) },
                        enabled = currentPage < pageCount - 1
                    ) {
                        Icon(Icons.Default.ChevronRight, "Next")
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Split view or Tabs or Stacked view
            // Let's do stacked for simplicity with easy toggle or scroll
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                if (translatedText.isNotBlank()) {
                    Text(
                        text = "Translation (Persian)",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = translatedText,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 18.sp,
                            lineHeight = 28.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right // Persian is RTL
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Text(
                    text = "Original Text",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = originalText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
