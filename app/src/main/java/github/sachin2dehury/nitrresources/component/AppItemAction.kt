package github.sachin2dehury.nitrresources.component

import android.content.Context
import android.content.Intent
import android.net.Uri
import github.sachin2dehury.nitrresources.core.DocDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object AppItemAction {
    fun shareDoc(doc: DocDetails, context: Context) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Check Out This : ${doc.name}")
            putExtra(Intent.EXTRA_TEXT, doc.url)
        }
        context.startActivity(Intent.createChooser(intent, "Share link!"))
    }

    fun renameDoc(docId: String, doc: DocDetails, item: Int) =
        CoroutineScope(Dispatchers.IO).launch {
            val list = AppLogic.pageSelector(item)
            val path =
                "${AppCore.COLLEGE}/${AppCore.stream}/${AppCore.year}/${AppCore.branch}/${AppCore.pageList[item]}"
            val docRef = AppCore.firebaseFireStore.collection(path).document(docId)
            AppCore.firebaseFireStore.runTransaction { batch ->
                batch.set(docRef, doc)
            }
            list[docId] = doc
        }

    fun deleteDoc(docId: String, item: Int) =
        CoroutineScope(Dispatchers.IO).launch {
            val list = AppLogic.pageSelector(item)
            val path =
                "${AppCore.COLLEGE}/${AppCore.stream}/${AppCore.year}/${AppCore.branch}/${AppCore.pageList[item]}"
            if (list[docId]!!.contributor == AppCore.firebaseAuth.currentUser!!.email!!) {
                AppCore.firebaseFireStore.collection("Trash").add(list[docId]!!).await()
                AppCore.firebaseFireStore.collection(path).document(docId).delete().await()
                list.remove(docId)
            }
        }

    fun openLink(link: String, context: Context) {
        val url = Uri.parse(link)!!
        val intent = Intent(Intent.ACTION_VIEW, url)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
}