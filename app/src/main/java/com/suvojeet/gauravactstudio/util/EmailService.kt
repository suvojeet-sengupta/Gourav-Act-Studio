package com.suvojeet.gauravactstudio.util

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerializationException

// STEP 1: Request object se keys hata di. Sirf data jaayega.
@Serializable
data class EmailRequest(
    val service_id: String,
    val template_id: String,
    val template_params: TemplateParams
    // user_id aur accessToken yahaan se HATA DIYE HAIN.
)

@Serializable
data class TemplateParams(
    val name: String,
    val phone: String,
    val event_type: String,
    val date: String,
    val package_name: String,
    val notes: String,
    val user_email: String
)

class EmailService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
        expectSuccess = true
    }

    // STEP 2: Sirf Private Key ki zaroorat hai. Public Key hata di.
    private val PRIVATE_KEY = "nqvC8yyG26lrXjr-vTSa8"

    suspend fun sendEmail(
        name: String,
        phone: String,
        eventType: String,
        otherEventType: String,
        date: String,
        notes: String,
        packageName: String
    ) {
        val finalEventType = if (eventType == "Other") otherEventType else eventType

        val request = EmailRequest(
            service_id = "service_ovxd5wh",
            template_id = "template_z67rf0m",
            template_params = TemplateParams(
                name = name,
                phone = phone,
                event_type = finalEventType,
                date = date,
                package_name = packageName,
                notes = notes,
                user_email = "gauravkumarpjt@gmail.com"
            )
        )

        try {
            val jsonPayload = Json.encodeToString(EmailRequest.serializer(), request)
            Log.d("EmailService", "Sending payload: $jsonPayload")
        } catch (e: SerializationException) {
            Log.e("EmailService", "Failed to serialize request", e)
        }

        // STEP 3: ASLI FIX YEH HAI
        val response = client.post("https://api.emailjs.com/api/v1.0/email/send") {
            // Private Key ko JSON body ki jagah HEADER mein bhej rahe hain
            header(HttpHeaders.Authorization, "Bearer $PRIVATE_KEY")
            contentType(ContentType.Application.Json)
            setBody(request) // Body mein ab sirf data hai, keys nahi
        }

        Log.d("EmailService", "Got response: ${response.status} ${response.bodyAsText()}")
    }
}
