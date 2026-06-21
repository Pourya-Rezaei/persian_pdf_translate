package com.example.util

import android.content.Context
import android.net.Uri
import android.util.Log
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.io.InputStream

object PdfUtil {
    private const val TAG = "PdfUtil"

    fun extractTextFromPage(context: Context, uri: Uri, pageIndex: Int): String {
        Log.d(TAG, "Extracting text from page $pageIndex of $uri")
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            if (inputStream == null) {
                Log.e(TAG, "Could not open input stream for $uri")
                return "خطا: امکان خواندن فایل وجود ندارد."
            }
            inputStream.use {
                PDDocument.load(it).use { document ->
                    if (pageIndex < 0 || pageIndex >= document.numberOfPages) {
                        Log.e(TAG, "Invalid page index: $pageIndex, total pages: ${document.numberOfPages}")
                        return ""
                    }
                    val stripper = PDFTextStripper()
                    stripper.startPage = pageIndex + 1
                    stripper.endPage = pageIndex + 1
                    stripper.getText(document)
                }
            } ?: ""
        } catch (e: Throwable) {
            Log.e(TAG, "Error extracting text", e)
            "Error extracting text: ${e.message}"
        }
    }

    fun getPageCount(context: Context, uri: Uri): Int {
        Log.d(TAG, "Getting page count for $uri")
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            inputStream?.use {
                PDDocument.load(it).use { document ->
                    document.numberOfPages
                }
            } ?: 0
        } catch (e: Throwable) {
            Log.e(TAG, "Error getting page count", e)
            0
        }
    }
}
