package com.museum.application.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import com.museum.application.database.tables.ContactSubmissionsTable
import com.museum.application.models.ApiResponse
import com.museum.application.models.ContactSubmission
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Data class for contact form submission
@kotlinx.serialization.Serializable
data class ContactFormRequest(
    val name: String,
    val email: String,
    val subject: String? = null,
    val message: String
)

fun Route.contactRoutes() {
    route("/contact") {
        // Submit contact form
        post {
            val formRequest = call.receive<ContactFormRequest>()

            val id = transaction {
                ContactSubmissionsTable.insert {
                    it[name] = formRequest.name
                    it[email] = formRequest.email
                    it[subject] = formRequest.subject
                    it[message] = formRequest.message
                    it[submittedAt] = LocalDateTime.now()
                    it[isRead] = false
                } get ContactSubmissionsTable.id
            }

            call.respond(
                HttpStatusCode.Created,
                ApiResponse<Unit>(
                    success = true,
                    message = "Your message has been sent successfully. We'll get back to you soon!"
                )
            )
        }

        // Get all contact submissions (admin only)
        get {
            // In a real implementation, this would be protected by authentication
            // and would only be accessible to admins

            val submissions = transaction {
                ContactSubmissionsTable.selectAll().orderBy(ContactSubmissionsTable.submittedAt, SortOrder.DESC).map { row ->
                    ContactSubmission(
                        id = row[ContactSubmissionsTable.id].value,
                        name = row[ContactSubmissionsTable.name],
                        email = row[ContactSubmissionsTable.email],
                        subject = row[ContactSubmissionsTable.subject],
                        message = row[ContactSubmissionsTable.message],
                        submittedAt = row[ContactSubmissionsTable.submittedAt].format(DateTimeFormatter.ISO_DATE_TIME),
                        isRead = row[ContactSubmissionsTable.isRead]
                    )
                }
            }

            call.respond(ApiResponse(success = true, data = submissions))
        }

        // Mark contact submission as read (admin only)
        put("/{id}/read") {
            // In a real implementation, this would be protected by authentication
            // and would only be accessible to admins

            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse<Unit>(success = false, message = "Invalid ID format")
                )
                return@put
            }

            val updated = transaction {
                ContactSubmissionsTable.update({ ContactSubmissionsTable.id eq id }) {
                    it[isRead] = true
                } > 0
            }

            if (updated) {
                call.respond(
                    ApiResponse<Unit>(success = true, message = "Marked as read")
                )
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    ApiResponse<Unit>(success = false, message = "Contact submission not found")
                )
            }
        }
    }
}
