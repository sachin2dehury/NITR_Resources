package github.sachin2dehury.nitrresources.admin.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Rebuilt {
    const val folder = "01I5FU6DYGMCHXMS7XCJDZIB6D4VYZB2N7"
    fun rebuildIndex(): IndexBuilder {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://nitrklacin-my.sharepoint.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit.create(IndexBuilder::class.java)
    }
}