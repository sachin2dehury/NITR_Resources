package github.sachin2dehury.nitrresources.component

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

    fun clearList() {
        AppCore.notes.clear()
        AppCore.assignment.clear()
        AppCore.slides.clear()
        AppCore.lab.clear()
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
}