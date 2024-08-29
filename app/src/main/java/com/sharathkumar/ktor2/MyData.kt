package com.sharathkumar.ktor2

import androidx.compose.runtime.internal.composableLambdaInstance
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class MyData {
    private val client = HttpClient{
        install(ContentNegotiation){
            json(Json{
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }
    suspend fun fetchImages(query: String): List<ImageResult>{
        val apiKey = ""
        return try{
            val response : Respone = client.get("https://pixabay.com/api/"){
                parameter("key",apiKey)
                parameter("q",query)
                parameter("image_type","photo")
            }.body()
            response.hits
        }catch (e:Exception){
            e.printStackTrace()
            emptyList()
        }
    }
}
