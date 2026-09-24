package com.yang.dhexambot

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : Activity() {
    private lateinit var webView: WebView
    @Suppress("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(245,247,244); window.navigationBarColor = Color.rgb(245,247,244)
        webView = WebView(this).apply {
            settings.javaScriptEnabled = true; settings.domStorageEnabled = true
            webViewClient = WebViewClient(); webChromeClient = WebChromeClient()
            addJavascriptInterface(ReminderBridge(), "AndroidReminder")
            loadUrl("file:///android_asset/index.html")
        }
        setContentView(webView); DailyReminderWorker.restore(this)
    }
    private inner class ReminderBridge {
        @JavascriptInterface fun save(enabled:Boolean,hour:Int,minute:Int) {
            DailyReminderWorker.saveAndSchedule(this@MainActivity,enabled,hour,minute)
            if(enabled && android.os.Build.VERSION.SDK_INT>=33 && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)!=PackageManager.PERMISSION_GRANTED)
                runOnUiThread { requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS),1001) }
        }
    }
    @Deprecated("Android back") override fun onBackPressed(){ if(webView.canGoBack()) webView.goBack() else super.onBackPressed() }
}
