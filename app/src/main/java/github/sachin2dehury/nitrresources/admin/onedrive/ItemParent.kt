package github.sachin2dehury.nitrresources.admin.onedrive

import com.squareup.moshi.Json

data class ItemParent(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "path") val path: String
)