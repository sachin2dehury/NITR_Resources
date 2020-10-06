package github.sachin2dehury.nitrresources.admin.rebuilt

import android.util.Log
import com.squareup.moshi.Moshi
import github.sachin2dehury.nitrresources.admin.auth.OneDriveAuth
import github.sachin2dehury.nitrresources.admin.onedrive.OneDriveItems
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object Rebuilt {
    private const val folder = "01I5FU6DYGMCHXMS7XCJDZIB6D4VYZB2N7"
    val jsonAdapter = Moshi.Builder().build()!!.adapter(OneDriveItems::class.java)!!

    //    _api/v2.0/drives/b!vDRlySusokmSGtJUAcuokTU6XUQW9RJJrgK7xUmZyccMLdnFg8vWTIKMlobzeGGX/items/$folder/children

    fun rebuilt() {
        val url = "https://nitrklacin-my.sharepoint.com/_api/v2.0/drives"
        val request = Request.Builder().url(url).build()
        OneDriveAuth.client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.w("Test failed", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                ParseJson.parseData(response)
            }
        })
    }
}