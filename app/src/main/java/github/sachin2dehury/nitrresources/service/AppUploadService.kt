package github.sachin2dehury.nitrresources.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.component.AppJobs
import github.sachin2dehury.nitrresources.dialog.RenameDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppUploadService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val channel = "NITR Resources Upload Service"
        val notificationManager = NotificationManagerCompat.from(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channel, channel, NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notification = NotificationCompat.Builder(this, channel).apply {
            priority = NotificationCompat.PRIORITY_DEFAULT
            setSmallIcon(R.drawable.ic_baseline_cloud_upload_24)
            setContentTitle("Uploading Files")
            setProgress(100, 0, false)
        }
        notificationManager.notify(AppCore.REQUEST_CODE_UPLOAD_SERVICE, notification.build())
        CoroutineScope(Dispatchers.IO).launch {
            intent!!.apply {
                val files = getStringArrayListExtra("Files")!!
                val item = getIntExtra("Index", 0)
                AppJobs.uploadDoc(files, RenameDialog.doc, item).invokeOnCompletion {
                    notificationManager.cancel(AppCore.REQUEST_CODE_UPLOAD_SERVICE)
                }
            }
        }.invokeOnCompletion {
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }
}