package github.sachin2dehury.nitrresources.admin

import com.squareup.moshi.Json

data class ItemMime(
    @field:Json(name = "mimeType") val mime: String
)