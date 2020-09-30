package github.sachin2dehury.nitrresources.admin

import github.sachin2dehury.nitrresources.admin.Rebuilder.folder
import retrofit2.Call
import retrofit2.http.GET

interface IndexBuilder {
    @GET("/items/$folder/children")
    fun getFolderItems(): Call<OneDriveItems>
}