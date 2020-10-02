package github.sachin2dehury.nitrresources.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.HttpAuthHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.activity.NavActivity
import github.sachin2dehury.nitrresources.component.AppCore
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        AppCore.firebaseAuth.signInWithEmailAndPassword("sachindehury2015@gmail.com", "bulbul2017")

        webView.apply {
            webViewClient = WebClient()
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.javaScriptEnabled = true
            settings.loadsImagesAutomatically = true
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            loadUrl("https://nitrklacin-my.sharepoint.com/")
        }


        runButton.setOnClickListener {
            val intent = Intent(this, NavActivity::class.java)
            startActivity(intent)
        }
    }
}

class WebClient : WebViewClient() {
    override fun onReceivedHttpAuthRequest(
        view: WebView?,
        handler: HttpAuthHandler?,
        host: String?,
        realm: String?
    ) {
        handler?.proceed("117cr0160@nitrkl.ac.in", "Bulbul@2017")
    }
}