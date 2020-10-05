package github.sachin2dehury.nitrresources.admin.onedrive

import com.squareup.moshi.Json

data class ItemMime(
    @field:Json(name = "mimeType") val mime: String
)