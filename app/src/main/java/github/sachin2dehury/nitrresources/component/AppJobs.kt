package github.sachin2dehury.nitrresources.component

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.DocumentChange
import github.sachin2dehury.nitrresources.core.DocDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object AppJobs {
    fun getList(item: Int) = CoroutineScope(Dispatchers.IO).launch {
        val list = AppLogic.pageSelector(item)
        val path =
            "${AppCore.COLLEGE}/${AppCore.currentStream}/${AppCore.currentYear}/${AppCore.currentBranch}/${AppCore.pageList[item]}"
        val documents = AppCore.firebaseFireStore.collection(path).get().await()!!.documents
        for (document in documents) {
            val doc = document.toObject(DocDetails::class.java)!!
            list[document.id] = doc
        }
    }

    fun updateDocList(item: Int) = CoroutineScope(Dispatchers.Main).launch {
        val list = AppLogic.pageSelector(item)
        val path =
            "${AppCore.COLLEGE}/${AppCore.currentStream}/${AppCore.currentYear}/${AppCore.currentBranch}/${AppCore.pageList[item]}"
        AppCore.firebaseFireStore.collection(path).addSnapshotListener { querySnapshot, _ ->
            for (change in querySnapshot!!.documentChanges) {
                val doc = (change.document.toObject(DocDetails::class.java))
                when (change.type) {
                    DocumentChange.Type.ADDED -> list[change.document.id] = doc
                    DocumentChange.Type.MODIFIED -> list[change.document.id] = doc
                    DocumentChange.Type.REMOVED -> list.remove(change.document.id)
                }
            }
        }
    }

    fun uploadDoc(files: ArrayList<String>, doc: DocDetails, item: Int) =
        CoroutineScope(Dispatchers.IO).launch {
            val path =
                "${AppCore.COLLEGE}/${AppCore.currentStream}/${AppCore.currentYear}/${AppCore.currentBranch}/${AppCore.pageList[item]}"
            val list = AppLogic.pageSelector(item)
            for (file in files) {
                val uri = Uri.parse(file)
                val docId = AppCore.firebaseFireStore.collection(path).add(doc).await()!!.id
                val storeReference = AppCore.firebaseStorage.child("$path/$docId")
                val docRef = AppCore.firebaseFireStore.collection(path).document(docId)
                storeReference.putFile(uri).await()

                doc.url = storeReference.downloadUrl.await().toString()
                storeReference.metadata.await().apply {
                    doc.size = sizeBytes.toDouble() / AppCore.MB
                    doc.time = updatedTimeMillis
                    doc.type = contentType.toString()
                }
                AppCore.firebaseFireStore.runBatch { batch ->
                    batch.set(docRef, doc)
                }
                list[docId] = doc
                Log.w("Test", "Upload Done $docId")
            }
        }
}