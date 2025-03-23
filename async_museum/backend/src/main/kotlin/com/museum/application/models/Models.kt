package com.museum.application.models

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
data class Exhibit(
    val id: Int? = null,
    val title: String,
    val description: String,
    val image: String,
    val category: String,
    val featured: Boolean,
    val details: String? = null
)

@Serializable
data class Event(
    val id: Int? = null,
    val title: String,
    val date: String, // ISO format: YYYY-MM-DD
    val description: String,
    val location: String? = null,
    val price: String? = null
)

@Serializable
data class VisitorInfo(
    val hours: String,
    val admission: Admission,
    val location: String,
    val contact: Contact
)

@Serializable
data class Admission(
    val adult: Double,
    val child: Double,
    val senior: Double
)

@Serializable
data class Contact(
    val phone: String,
    val email: String
)

@Serializable
data class User(
    val id: Int? = null,
    val email: String,
    val displayName: String? = null,
    val providerId: String,
    val providerUserId: String
)

@Serializable
data class ContactSubmission(
    val id: Int? = null,
    val name: String,
    val email: String,
    val subject: String? = null,
    val message: String,
    val submittedAt: String? = null,
    val isRead: Boolean = false
)

@Serializable
data class AuthResponse(
    val token: String,
    val user: User
)

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null
)