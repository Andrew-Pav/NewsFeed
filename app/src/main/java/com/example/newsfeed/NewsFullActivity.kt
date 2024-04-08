package com.example.newsfeed

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.newsfeed.databinding.ActivityNewsFullBinding

class NewsFullActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsFullBinding
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.webView.canGoBack())
                binding.webView.goBack()
            else
                finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsFullBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("url")
        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()
        url?.let { binding.webView.loadUrl(url) }

        onBackPressedDispatcher.addCallback(this, callback)
    }
}