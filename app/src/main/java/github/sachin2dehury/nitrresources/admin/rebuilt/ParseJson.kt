package github.sachin2dehury.nitrresources.admin.rebuilt

import android.util.Log
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.core.DocDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.Response
import java.util.*

object ParseJson {
    private val streamList =
        listOf("B. Arch", "B. Tech", "Int. M, Sc (Only B. Sc)", "M. Sc", "M. Tech")
    private val yearList =
        listOf("First Year", "Second Year", "Third Year", "Fourth Year", "Fifth Year")


    private const val course = "Physics of Material"
    private const val type =
        "Notes"

    //    "Books"
//    "Slides"
//    "Labs"
//    "Assignments"
    private const val branch =
//      "All"
        "Metallurgical and Materials Engineering"
    private val path =
        "${AppCore.COLLEGE}/${streamList[1]}/${yearList[1]}/$branch/$type"

    fun parseData(response: Response) {
        val json = response.body
        val data = Rebuilt.jsonAdapter.fromJsonValue(json)
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
                    AppCore.firebaseFireStore.collection(path).add(doc).await()!!
                }.invokeOnCompletion {
                    Log.w(" Test Upload", "Done")
                }
            }
        }
    }
}