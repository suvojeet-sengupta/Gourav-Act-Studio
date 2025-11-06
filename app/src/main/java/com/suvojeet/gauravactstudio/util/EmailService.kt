package com.suvojeet.gauravactstudio.util

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class EmailRequest(
    val service_id: String,
    val template_id: String,
    val user_id: String,
    val template_params: TemplateParams
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
            })
        }
    }

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
            user_id = "TbJu090thBBTkuHRO",
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

        client.post("https://api.emailjs.com/api/v1.0/email/send") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }
}