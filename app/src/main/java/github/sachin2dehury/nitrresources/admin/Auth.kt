package github.sachin2dehury.nitrresources.admin

import android.util.Log
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.CredentialsProvider
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils

class Auth {
    fun auth() {
        try {
            val credsProvider: CredentialsProvider = BasicCredentialsProvider()
            credsProvider.setCredentials(
                AuthScope(AuthScope.ANY),
                UsernamePasswordCredentials(
                    "117cr0160@nitrkl.ac.in",
                    "Bulbul@2017",
                )
            )
            val httpclient: CloseableHttpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build()
            httpclient.use {
                val httpGet = HttpGet("https://nitrklacin-my.sharepoint.com/_api/web/lists")
                println("Executing request " + httpGet.requestLine)
                val response: CloseableHttpResponse = it.execute(httpGet)
                response.use { that ->
                    println("----------------------------------------")
                    println(that.statusLine)
                    EntityUtils.consume(that.entity)
                }
            }
        } catch (e: Exception) {
            Log.w("Test", "Failed")
        }
    }
}