package com.museum.application.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import com.museum.application.database.tables.ExhibitsTable
import com.museum.application.models.ApiResponse
import com.museum.application.models.Exhibit

fun Route.exhibitRoutes() {
    route("/exhibits") {
        // Get all exhibits
        get {
            val exhibits = transaction {
                ExhibitsTable.selectAll().map { row ->
                    Exhibit(
                        id = row[ExhibitsTable.id].value,
                        title = row[ExhibitsTable.title],
                        description = row[ExhibitsTable.description],
                        image = row[ExhibitsTable.image],
                        category = row[ExhibitsTable.category],
                        featured = row[ExhibitsTable.featured],
                        details = row[ExhibitsTable.details]
                    )
                }
            }
            call.respond(ApiResponse(success = true, data = exhibits))
        }

        // Get exhibit by ID
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse<Unit>(success = false, message = "Invalid ID format")
                )
                return@get
            }

            val exhibit = transaction {
                ExhibitsTable.select { ExhibitsTable.id eq id }.singleOrNull()?.let { row ->
                    Exhibit(
                        id = row[ExhibitsTable.id].value,
                        title = row[ExhibitsTable.title],
                        description = row[ExhibitsTable.description],
                        image = row[ExhibitsTable.image],
                        category = row[ExhibitsTable.category],
                        featured = row[ExhibitsTable.featured],
                        details = row[ExhibitsTable.details]
                    )
                }
            }

            if (exhibit != null) {
                call.respond(ApiResponse(success = true, data = exhibit))
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    ApiResponse<Unit>(success = false, message = "Exhibit not found")
                )
            }
        }

        // Create new exhibit
        post {
            val exhibit = call.receive<Exhibit>()
            val id = transaction {
                ExhibitsTable.insert {
                    it[title] = exhibit.title
                    it[description] = exhibit.description
                    it[image] = exhibit.image
                    it[category] = exhibit.category
                    it[featured] = exhibit.featured
                    it[details] = exhibit.details
                } get ExhibitsTable.id
            }

            val newExhibit = exhibit.copy(id = id.value)
            call.respond(
                HttpStatusCode.Created,
                ApiResponse(success = true, data = newExhibit)
            )
        }

        // Update exhibit
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse<Unit>(success = false, message = "Invalid ID format")
                )
                return@put
            }

            val exhibit = call.receive<Exhibit>()
            val updated = transaction {
                ExhibitsTable.update({ ExhibitsTable.id eq id }) {
                    it[title] = exhibit.title
                    it[description] = exhibit.description
                    it[image] = exhibit.image
                    it[category] = exhibit.category
                    it[featured] = exhibit.featured
                    it[details] = exhibit.details
                    it[updatedAt] = java.time.LocalDateTime.now()
                } > 0
            }

            if (updated) {
                call.respond(
                    ApiResponse(success = true, data = exhibit.copy(id = id))
                )
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    ApiResponse<Unit>(success = false, message = "Exhibit not found")
                )
            }
        }

        // Delete exhibit
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse<Unit>(success = false, message = "Invalid ID format")
                )
                return@delete
            }

            val deleted = transaction {
                // Check if the record exists
                val exists = ExhibitsTable.select { ExhibitsTable.id eq id }.count() > 0
                if (exists) {
                    // Use raw SQL for deletion
                    exec("DELETE FROM exhibits WHERE id = $id")
                    true
                } else {
                    false
                }
            }

            if (deleted) {
                call.respond(
                    ApiResponse<Unit>(success = true, message = "Exhibit deleted successfully")
                )
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    ApiResponse<Unit>(success = false, message = "Exhibit not found")
                )
            }
        }
    }
}
