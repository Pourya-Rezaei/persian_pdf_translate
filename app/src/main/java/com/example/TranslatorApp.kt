package com.example

import android.app.Application
import androidx.room.Room
import com.example.data.AppDatabase

class TranslatorApp : Application() {
    
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        try {
            com.tom_roush.pdfbox.android.PDFBoxResourceLoader.init(applicationContext)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "translator_db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }
}
