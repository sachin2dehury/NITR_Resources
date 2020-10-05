package github.sachin2dehury.nitrresources.component

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import github.sachin2dehury.nitrresources.activity.PreviewActivity
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
            putExtra(Intent.EXTRA_SUBJECT, "Check Out This : ${doc.courseName}")
            putExtra(Intent.EXTRA_TEXT, doc.url)
        }
        context.startActivity(Intent.createChooser(intent, "Share link!"))
    }

    fun renameDoc(docId: String, doc: DocDetails, item: Int) =
        CoroutineScope(Dispatchers.IO).launch {
            val list = AppLogic.pageSelector(item)
            val path =
                "${AppCore.COLLEGE}/${AppCore.currentStream}/${AppCore.currentYear}/${AppCore.currentBranch}/${AppCore.pageList[item]}"
            val docRef = AppCore.firebaseFireStore.collection(path).document(docId)
            AppCore.firebaseFireStore.runTransaction { batch ->
                batch.set(docRef, doc)
            }
            list[docId] = doc
        }

    fun deleteDoc(docId: String, item: Int) = CoroutineScope(Dispatchers.IO).launch {
        val list = AppLogic.pageSelector(item)
        val path =
            "${AppCore.COLLEGE}/${AppCore.currentStream}/${AppCore.currentYear}/${AppCore.currentBranch}/${AppCore.pageList[item]}"
        AppCore.firebaseFireStore.collection("Trash").add(list[docId]!!).await()
        AppCore.firebaseFireStore.collection(path).document(docId).delete().await()
        list.remove(docId)
    }

    fun openLink(link: String, context: Context) {
        val url = Uri.parse(link)!!
        val intent = Intent(Intent.ACTION_VIEW, url)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun preview(link: String, context: Context) {
        val intent = Intent(context, PreviewActivity::class.java)
        intent.putExtra("url", link)
        context.startActivity(intent)
    }

    fun downloadDoc(doc: DocDetails, context: Context) {
        if (!AppPermission.checkPermission(context)) {
            AppPermission.requestPermission(context as Activity)
        }
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val url = Uri.parse(doc.url)!!
        val request = DownloadManager.Request(url).apply {
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE).apply {
                setAllowedOverRoaming(false)
                setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "${doc.subjectName}_${doc.courseName}" +
                            ".${AppCore.mime.getExtensionFromMimeType(doc.type)}"
                )
                setTitle(doc.subjectName)
                setMimeType(doc.type)
                setDescription(doc.courseName)
            }
        }
        downloadManager.enqueue(request)
    }
}