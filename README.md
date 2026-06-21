# 📚 Persian PDF Translator

<div align="center">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android"/>
  <img src="https://img.shields.io/badge/Kotlin-2.2.10-blue?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/Jetpack_Compose-Material3-4285F4?style=for-the-badge&logo=jetpack-compose&logoColor=white" alt="Jetpack Compose"/>
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge" alt="License"/>
  
  **ترجمه هوشمند فایل‌های PDF به فارسی با قدرت هوش مصنوعی**
  
  *ترجمه سریع، دقیق و با حفظ فرمت اصلی اسناد*
  
  [ویژگی‌ها](#-ویژگی‌های-کلیدی) • [نصب](#-نصب-و-راه‌اندازی) • [نحوه استفاده](#-نحوه-استفاده) • [معماری](#-معماری-فنی) • [مجوز](#-مجوز)
</div>

---

## 🌟 معرفی

**Persian PDF Translator** یک اپلیکیشن اندرویدی مدرن و پیشرفته برای ترجمه خودکار فایل‌های PDF به زبان فارسی (و سایر زبان‌ها) است. این برنامه با بهره‌گیری از جدیدترین مدل‌های هوش مصنوعی زبانی (LLM)، امکان ترجمه صفحه‌به‌صفحه اسناد را با حفظ ساختار، فرمت و خوانایی اصلی فراهم می‌کند.

### ✨ چرا این پروژه؟
- 🎯 **چندمنظوره:** پشتیبانی از ۵ سرویس مختلف هوش مصنوعی
- 🔒 **امن:** مدیریت امن API Keyها با Secrets Gradle Plugin
- 📱 **مدرن:** رابط کاربری کاملاً با Jetpack Compose و Material 3
- 🚀 **سریع:** استخراج متن با PDFBox و ترجمه بلادرنگ
- 🌐 **RTL:** پشتیبانی عالی از زبان‌های راست‌چین مانند فارسی

---

## 🚀 ویژگی‌های کلیدی

### 🤖 پشتیبانی از چندین ارائه‌دهنده AI

| سرویس              | توضیحات                        | حالت اجرا     |
|--------------------|--------------------------------|---------------|
| **Google Gemini**  | مدل قدرتمند گوگل             | ☁️ ابری      |
| **OpenAI GPT**     | مدل‌های سری GPT               | ☁️ ابری      |
| **OpenRouter**     | دسترسی به بیش از ۱۰۰ مدل     | ☁️ ابری      |
| **AI Edge (Local)**| سرورهای محلی (مانند Ollama) | 🏠 محلی      |
| **AI Edge (SDK)**  | اجرای مستقیم روی دستگاه     | 📱 آفلاین    |

### 📋 قابلیت‌های اصلی
- ✅ انتخاب آسان فایل PDF از دستگاه
- ✅ استخراج هوشمند و دقیق متن با PDFBox
- ✅ ترجمه صفحه‌به‌صفحه با ناوبری Previous/Next
- ✅ نمایش همزمان متن اصلی و ترجمه‌شده
- ✅ ذخیره امن تنظیمات و API Keyها با Room Database
- ✅ پشتیبانی چندزبانه (پیش‌فرض: فارسی)
- ✅ طراحی Material 3 با تم تیره و روشن
- ✅ ناوبری Bottom Navigation

---

## 📦 نصب و راه‌اندازی

### پیش‌نیازها
- **Android Studio:** Arctic Fox یا جدیدتر
- **JDK:** ۱۷ یا بالاتر
- **Android SDK:** API Level 26+ (Android 8.0)
- **Kotlin:** 2.2.10

### مراحل نصب

#### ۱. کلون کردن پروژه
```bash
git clone https://github.com/yourusername/persian-pdf-translator.git
cd persian-pdf-translator
```

#### ۲. پیکربندی API Keys
فایل `.env` را در ریشه پروژه ایجاد کنید:
```env
# Google Gemini
GEMINI_API_KEY=your_gemini_api_key_here

# OpenAI
OPENAI_API_KEY=your_openai_api_key_here

# OpenRouter (اختیاری)
OPENROUTER_API_KEY=your_openrouter_api_key_here

# Local Server (Ollama و غیره)
LOCAL_SERVER_URL=http://localhost:11434
```

> ⚠️ **نکته امنیتی:** هرگز فایل `.env` را در Git کامیت نکنید (در `.gitignore` قرار دارد).

#### ۳. ساخت پروژه
```bash
# لینوکس / macOS
./gradlew assembleDebug

# ویندوز
gradlew.bat assembleDebug
```

#### ۴. نصب روی دستگاه
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```
یا مستقیماً از Android Studio اجرا کنید.

---

## 📱 نحوه استفاده

### گام‌های سریع:
1. برنامه را باز کنید و سرویس AI مورد نظر را انتخاب کنید (پیشنهاد: Google Gemini)
2. در بخش **Settings**، API Key و مدل را تنظیم کنید
3. روی دکمه **+** ضربه بزنید و فایل PDF را انتخاب کنید
4. ترجمه به صورت خودکار انجام می‌شود و می‌توانید صفحه‌به‌صفحه مطالعه کنید

---

## 🏗️ معماری فنی

### ساختار پروژه
```
app/src/main/java/com/example/persianpdftranslator/
├── ui/
│   ├── screens/ (HomeScreen, ReaderScreen, SettingsScreen)
│   └── theme/
├── data/ (Providers + Room)
├── domain/ (TranslationProvider interface)
├── util/PdfUtil.kt
└── MainViewModel.kt
```

### تکنولوژی‌های کلیدی

| دسته‌بندی         | کتابخانه                     | نسخه       |
|------------------|------------------------------|-----------|
| زبان             | Kotlin                       | 2.2.10    |
| UI               | Jetpack Compose + Material 3 | Latest    |
| Navigation       | Navigation Compose           | Latest    |
| Database         | Room                         | 2.6.1     |
| PDF Processing   | PDFBox Android               | 2.0.27.0  |
| AI (Cloud)       | Google Generative AI         | Latest    |
| Network          | OkHttp + Retrofit            | 4.12.0    |
| Secrets          | Secrets Gradle Plugin        | 2.0.1     |

---

## 🔧 پیکربندی پیشرفته

**مدل‌های Gemini:**
- `gemini-1.5-pro` (کیفیت بالا)
- `gemini-1.5-flash` (سریع)

**مدل‌های OpenAI:**
- `gpt-4o`
- `gpt-4-turbo`

**مدل‌های محلی:**
- `llama3`, `mistral`, `gemma`

**زبان‌های پشتیبانی‌شده:** فارسی، انگلیسی، فرانسه، آلمانی، اسپانیایی، ترکی و ...

---

## 🔐 امنیت
- تزریق امن API Key با Secrets Plugin
- ذخیره رمزنگاری‌شده در Room Database
- دسترسی محدود به فایل انتخاب‌شده

---
## ❓ سوالات متداول

**آیا نیاز به اینترنت دارد؟**  
- سرویس‌های ابری: ✅ بله  
- سرویس‌های محلی: ❌ خیر (کاملاً آفلاین)

**حداکثر حجم فایل PDF؟**  
تا ۵۰ مگابایت توصیه می‌شود.

**آیا فرمت اصلی حفظ می‌شود؟**  
متن استخراج و ترجمه می‌شود. تصاویر و جداول به صورت متنی نمایش داده می‌شوند.

---

## 📄 مجوز
این پروژه تحت مجوز **MIT License** منتشر شده است.

---

## 👨‍💻 توسعه‌دهنده

**پوریا رضایی**

<div align="center">
  <a href="https://github.com/yourusername">
    <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub"/>
  </a>
  <a href="https://t.me/yourusername">
    <img src="https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white" alt="Telegram"/>
  </a>
</div>

---

<div align="center">
  <strong>⭐ اگر پروژه را مفید دانستید، آن را Star کنید! ⭐</strong><br><br>
  ساخته شده با ❤️ و ☕ توسط پوریا رضایی
</div>
