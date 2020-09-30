package github.sachin2dehury.nitrresources.admin

import com.squareup.moshi.Json

data class Parent(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "path") val path: String
)