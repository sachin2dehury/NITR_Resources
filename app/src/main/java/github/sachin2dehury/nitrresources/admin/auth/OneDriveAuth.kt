package github.sachin2dehury.nitrresources.admin.auth

import android.util.Log
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.core.UserDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.*
import java.io.IOException

object OneDriveAuth {

    lateinit var admin: UserDetails

    val client = OkHttpClient.Builder().authenticator { _, response ->
        val credential = Credentials.basic(admin.email, admin.password)
        response.request.newBuilder().header("Authorization", credential).build()
    }.build()

    private fun getAdmin() = CoroutineScope(Dispatchers.IO).launch {
        val path = "Admin"
        val oneDrive = "OneDrive"
        val temp = AppCore.firebaseFireStore.collection(path).document(oneDrive).get().await()
        admin = temp.toObject(UserDetails::class.java)!!
    }

    private fun adminLogin() = CoroutineScope(Dispatchers.IO).launch {
        val url = "https://login.live.com/"
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.w("Test Admin login failed", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.w("Test", "Admin logged in")
            }
        })
    }

    fun startOneDriveService() {
        getAdmin().invokeOnCompletion {
            adminLogin()
        }
    }
}