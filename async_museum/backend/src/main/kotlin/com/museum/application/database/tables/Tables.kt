package com.museum.application.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime

// Exhibits table
object ExhibitsTable : IntIdTable("exhibits") {
    val title = varchar("title", 255)
    val description = text("description")
    val image = varchar("image", 255)
    val category = varchar("category", 50)
    val featured = bool("featured")
    val details = text("details").nullable()
    val createdAt = datetime("created_at").clientDefault { java.time.LocalDateTime.now() }
    val updatedAt = datetime("updated_at").clientDefault { java.time.LocalDateTime.now() }
}

// Events table
object EventsTable : IntIdTable("events") {
    val title = varchar("title", 255)
    val date = date("date")
    val description = text("description")
    val location = varchar("location", 255).nullable()
    val price = varchar("price", 50).nullable()
    val createdAt = datetime("created_at").clientDefault { java.time.LocalDateTime.now() }
    val updatedAt = datetime("updated_at").clientDefault { java.time.LocalDateTime.now() }
}

// Visitor information table
object VisitorInfoTable : Table("visitor_info") {
    val id = integer("id").autoIncrement()
    val hours = varchar("hours", 255)
    val adultAdmission = decimal("adult_admission", 10, 2)
    val childAdmission = decimal("child_admission", 10, 2)
    val seniorAdmission = decimal("senior_admission", 10, 2)
    val location = varchar("location", 255)
    val phone = varchar("phone", 50)
    val email = varchar("email", 255)
    val updatedAt = datetime("updated_at").clientDefault { java.time.LocalDateTime.now() }

    override val primaryKey = PrimaryKey(id)
}

// Users table
object UsersTable : IntIdTable("users") {
    val email = varchar("email", 255).uniqueIndex()
    val displayName = varchar("display_name", 255).nullable()
    val providerId = varchar("provider_id", 50) // "google", "facebook", etc.
    val providerUserId = varchar("provider_user_id", 255)
    val createdAt = datetime("created_at").clientDefault { java.time.LocalDateTime.now() }
    val lastLogin = datetime("last_login").clientDefault { java.time.LocalDateTime.now() }
}

// Contact submissions table
object ContactSubmissionsTable : IntIdTable("contact_submissions") {
    val name = varchar("name", 255)
    val email = varchar("email", 255)
    val subject = varchar("subject", 255).nullable()
    val message = text("message")
    val submittedAt = datetime("submitted_at").clientDefault { java.time.LocalDateTime.now() }
    val isRead = bool("is_read").default(false)
}