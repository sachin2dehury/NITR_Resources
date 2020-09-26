package github.sachin2dehury.nitrresources.dialog

import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatDialog
import github.sachin2dehury.nitrresources.R

class AboutDialog(context: Context) : AppCompatDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature((Window.FEATURE_NO_TITLE))
        setContentView(R.layout.dialog_about)
        setCancelable(true)
    }
}