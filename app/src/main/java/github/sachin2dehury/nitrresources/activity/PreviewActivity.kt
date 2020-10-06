package github.sachin2dehury.nitrresources.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import github.sachin2dehury.nitrresources.R
import kotlinx.android.synthetic.main.activity_preview.*

class PreviewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        val url = intent.getStringExtra("url")!!
        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.loadsImagesAutomatically = true
            loadUrl(url)
        }
    }

//    private fun delete() = CoroutineScope(Dispatchers.IO).launch {
//        val me = AppCore.firebaseFireStore.collection("User")
//            .whereEqualTo("email", "sachindehury2015@gmail.com").get().await().documents
//        for (i in me) {
//            AppCore.firebaseFireStore.collection("User").document(i.id).delete()
//        }
//    }
}