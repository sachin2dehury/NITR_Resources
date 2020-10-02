package github.sachin2dehury.nitrresources.admin

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.squareup.moshi.Moshi
import github.sachin2dehury.nitrresources.admin.api.OneDriveItems
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.core.DocDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*

class ParseJson(private val context: Context) {
    private val moshi = Moshi.Builder().build()!!
    private val jsonAdapter = moshi.adapter(OneDriveItems::class.java)!!
    private val streamList =
        listOf("B. Arch", "B. Tech", "Int. M, Sc (Only B. Sc)", "M. Sc", "M. Tech")
    private val yearList =
        listOf("First Year", "Second Year", "Third Year", "Fourth Year", "Fifth Year")


    private val course = "Physics of Material"
    private val type =
        "Notes"

    //        "Books"
//    "Slides"
//    "Labs"
//        "Assignments"
    private val branch =
//        "All"
        "Metallurgical and Materials Engineering"
    private val path =
        "${AppCore.COLLEGE}/${streamList[1]}/${yearList[1]}/$branch/$type"

    @SuppressLint("CheckResult")
    fun parseData() {
        val json = context.assets.open("data.json").bufferedReader().use { it.readText() }
        val data = jsonAdapter.fromJson(json)
        Log.w("Test", data.toString())
        if (data != null) {
            val files = data.oneDriveFiles
            for (file in files) {
                val doc = DocDetails(
                    file.name,
                    course,
                    AppCore.firebaseAuth.currentUser!!.email!!,
                    Date().time,
                    file.size.toDouble(),
                    file.url,
                    file.type.mime
                )
                CoroutineScope(Dispatchers.IO).launch {
                    Log.w("Test", doc.toString())
                    AppCore.firebaseFireStore.collection(path).add(doc).await()!!
                }.invokeOnCompletion {
                    Log.w(" Test Upload", "Done")
                }
            }
        }
    }
}