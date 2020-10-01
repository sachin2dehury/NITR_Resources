package github.sachin2dehury.nitrresources.admin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.component.AppCore
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : AppCompatActivity() {

    init {
        ParseJson(this).parseData()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        AppCore.firebaseAuth.signInWithEmailAndPassword("sachindehury2015@gmail.com", "bulbul2017")

        runButton.setOnClickListener {

        }
    }
}