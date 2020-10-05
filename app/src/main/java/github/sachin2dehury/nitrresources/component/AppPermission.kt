package github.sachin2dehury.nitrresources.component

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object AppPermission {
    fun checkPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(activity: Activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, WRITE_EXTERNAL_STORAGE)
        ) {
            Toast.makeText(
                activity, "Please allow Storage permission in App Settings.", Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(WRITE_EXTERNAL_STORAGE),
                AppCore.REQUEST_CODE_STORAGE
            )
        }
    }
}