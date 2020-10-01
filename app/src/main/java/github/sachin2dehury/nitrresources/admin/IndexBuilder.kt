package github.sachin2dehury.nitrresources.admin

import github.sachin2dehury.nitrresources.admin.Rebuilt.folder
import retrofit2.Call
import retrofit2.http.GET

interface IndexBuilder {
    @GET("_api/v2.0/drives/b!vDRlySusokmSGtJUAcuokTU6XUQW9RJJrgK7xUmZyccMLdnFg8vWTIKMlobzeGGX/items/$folder/children")
    fun getFolderItems(): Call<OneDriveItems>
}