package github.sachin2dehury.nitrresources.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import github.sachin2dehury.nitrresources.component.AppJobs
import github.sachin2dehury.nitrresources.dialog.RenameDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppUploadService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.w("Test", "Service Started")
        CoroutineScope(Dispatchers.IO).launch {
            intent!!.apply {
                val files = getStringArrayListExtra("Files")!!
                val item = getStringExtra("Index")!!.toInt()
                AppJobs.uploadDoc(files, RenameDialog.doc, item)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.w("Test", "Service Stopped")
        super.onDestroy()
    }
}