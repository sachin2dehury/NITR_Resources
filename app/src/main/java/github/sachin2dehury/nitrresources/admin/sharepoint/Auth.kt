package github.sachin2dehury.nitrresources.admin.sharepoint

import android.util.Log
import com.panxoloto.sharepoint.rest.PLGSharepointClientOnline
import org.json.JSONObject


class Auth {

    //    https://nitrklacin-my.sharepoint.com
//    https://nitrklacin-my.sharepoint.com/personal/117cr0160_nitrkl_ac_in/_layouts/15/onedrive.aspx
    private val user = "117cr0160@nitrkl.ac.in"
    private val password = "Bulbul2017"
    private val domain = "nitrklacin-my.sharepoint.com"
    private val spSiteUrl = "/personal/117cr0160_nitrkl_ac_in"

    fun auth() {
        val wrapper = PLGSharepointClientOnline(user, password, domain, spSiteUrl)
        try {
            val result: JSONObject = wrapper.getAllLists("{}")
            Log.w("Test", result.toString())
        } catch (e: Exception) {
            Log.w("Test", e.toString())
        }
    }
}