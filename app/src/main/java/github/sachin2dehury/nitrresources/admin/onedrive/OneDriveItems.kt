package github.sachin2dehury.nitrresources.admin.onedrive

import com.squareup.moshi.Json

data class OneDriveItems(
    @field:Json(name = "value") val oneDriveFiles: MutableList<OneDriveItemDetails>
)