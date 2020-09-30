package github.sachin2dehury.nitrresources.admin

import com.squareup.moshi.Json

data class OneDriveItems(
    @field:Json(name = "value") val oneDriveFiles: MutableList<OneDriveItemDetails>
)