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
import kotlinx.serialization.SerialName // <-- YEH HAI ASLI HERO

// STEP 1: Saari fields ko @SerialName se annotate kar diya
@Serializable
data class EmailRequest(
    @SerialName("service_id") val service_id: String,
    @SerialName("template_id") val template_id: String,
    @SerialName("user_id") val user_id: String, // R8 isko 'a' bhi bana de, JSON mein 'user_id' hi jaayega
    @SerialName("accessToken") val accessToken: String, // R8 isko 'b' bhi bana de, JSON mein 'accessToken' hi jaayega
    @SerialName("template_params") val template_params: TemplateParams
)

@Serializable
data class TemplateParams(
    @SerialName("name") val name: String,
    @SerialName("phone") val phone: String,
    @SerialName("event_type") val event_type: String,
    @SerialName("date") val date: String,
    @SerialName("package_name") val package_name: String,
    @SerialName("notes") val notes: String,
    @SerialName("user_email") val user_email: String,
    @SerialName("custom_package_details") val custom_package_details: String? = null,
    @SerialName("location") val location: String,
    @SerialName("booking_request_number") val booking_request_number: String? = null // New field
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

    private val PUBLIC_KEY = "TbJu090thBBTkuHRO"
    private val PRIVATE_KEY = "nqvC8yyG26lrXjr-vTSa8"

    suspend fun sendEmail(
        name: String,
        phone: String,
        eventType: String,
        otherEventType: String,
        date: String,
        notes: String,
        packageName: String,
        customPackageDetails: String? = null,
        location: String,
        bookingRequestNumber: String? = null // New parameter
    ) {
        val finalEventType = if (eventType == "Other") otherEventType else eventType

        // STEP 2: Request object ab R8-proof hai
        val request = EmailRequest(
            service_id = "service_ovxd5wh",
            template_id = "template_z67rf0m",
            user_id = PUBLIC_KEY,
            accessToken = PRIVATE_KEY,
            template_params = TemplateParams(
                name = name,
                phone = phone,
                event_type = finalEventType,
                date = date,
                package_name = packageName,
                notes = notes,
                user_email = "gauravkumarpjt@gmail.com",
                custom_package_details = customPackageDetails,
                location = location,
                booking_request_number = bookingRequestNumber // Pass booking number
            )
        )

        try {
            val response = client.post("https://api.emailjs.com/api/v1.0/email/send") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            Log.d("EmailService", "Got response: ${response.status} ${response.bodyAsText()}")
        } catch (e: Exception) {
            Log.e("EmailService", "Failed to send email", e)
            throw Exception("Failed to send booking request. Please try again later.") // Updated error message
        }
    }
}