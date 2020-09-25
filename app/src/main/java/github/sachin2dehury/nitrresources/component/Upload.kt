package github.sachin2dehury.nitrresources.component

import android.net.Uri
import android.webkit.MimeTypeMap
import github.sachin2dehury.nitrresources.core.DocDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object Upload {
    private fun fileType(file: Uri): String {
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.toString())!!
        return AppCore.mime.getMimeTypeFromExtension(extension)!!
    }

    fun uploadDoc(file: Uri, doc: DocDetails, item: Int) =
        CoroutineScope(Dispatchers.IO).launch {
            val path =
                "${AppCore.COLLEGE}/${AppCore.stream}/${AppCore.year}/${AppCore.branch}/${AppCore.pageList[item]}"
            val list = AppLogic.pageSelector(item)
            val docId = AppCore.firebaseFireStore.collection(path).add(doc).await()!!.id
            val storeReference = AppCore.firebaseStorage.child("$path/$docId")
            val docRef = AppCore.firebaseFireStore.collection(path).document(docId)
            storeReference.putFile(file).await()

            doc.type = fileType(file)
            doc.url = storeReference.downloadUrl.await().toString()
            storeReference.metadata.await().apply {
                doc.size = sizeBytes.toDouble() / AppCore.MB
                doc.time = updatedTimeMillis
            }
            AppCore.firebaseFireStore.runBatch { batch ->
                batch.set(docRef, doc)
            }
            list[docId] = doc
        }
}