package github.sachin2dehury.nitrresources.admin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import github.sachin2dehury.nitrresources.R
import github.sachin2dehury.nitrresources.component.AppCore
import github.sachin2dehury.nitrresources.core.DocDetails
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        Auth().auth()
        AppCore.firebaseAuth.signInWithEmailAndPassword("sachindehury2015@gmail.com", "bulbul2017")

        val indexBuilder = Rebuilder.rebuildIndex()
        val course = "Basic Electrical Engineering"
        val pageList = listOf("Notes", "Books", "Slides", "Labs", "Assignments")
        val streamList = listOf("B. Arch", "B. Tech", "Int. M, Sc (Only B. Sc)", "M. Sc", "M. Tech")
        val yearList =
            listOf("First Year", "Second Year", "Third Year", "Fourth Year", "Fifth Year")
        val path =
            "${AppCore.COLLEGE}/${streamList[1]}/${yearList[0]}/All/${pageList[2]}"

        runButton.setOnClickListener {
            val call = indexBuilder.getFolderItems()
            call.enqueue(object : Callback<OneDriveItems> {
                override fun onResponse(
                    call: Call<OneDriveItems>,
                    response: Response<OneDriveItems>
                ) {
                    Log.w("Test", response.toString())
                    val data = response.body()
                    if (data != null) {
                        textView.text = data.toString()
                        val files = data.oneDriveFiles
                        val doc = DocDetails()
                        for (file in files) {
                            doc.apply {
                                subjectName = file.name
                                courseName = course
                                size = file.size.toDouble()
                                time = Date().time
                                url = file.url
                                type = file.type.mime
                            }
                            CoroutineScope(Dispatchers.IO).launch {
                                Log.w("Test", doc.toString())
                                AppCore.firebaseFireStore.collection(path).add(doc).await()!!
                            }.invokeOnCompletion {
                                Log.w(" Test Upload", "Done")
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<OneDriveItems>, t: Throwable) {
                    Log.w("Test failed", t.toString())
                }
            })
        }
    }
}