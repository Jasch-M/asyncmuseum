package com.museum.application.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import com.museum.application.database.tables.EventsTable
import com.museum.application.models.ApiResponse
import com.museum.application.models.Event
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Route.eventRoutes() {
    route("/events") {
        // Get all events
        get {
            val events = transaction {
                EventsTable.selectAll().map { row ->
                    Event(
                        id = row[EventsTable.id].value,
                        title = row[EventsTable.title],
                        date = row[EventsTable.date].toString(),
                        description = row[EventsTable.description],
                        location = row[EventsTable.location],
                        price = row[EventsTable.price]
                    )
                }
            }
            call.respond(ApiResponse(success = true, data = events))
        }

        // Get event by ID
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse<Unit>(success = false, message = "Invalid ID format")
                )
                return@get
            }

            val event = transaction {
                EventsTable.select { EventsTable.id eq id }.singleOrNull()?.let { row ->
                    Event(
                        id = row[EventsTable.id].value,
                        title = row[EventsTable.title],
                        date = row[EventsTable.date].toString(),
                        description = row[EventsTable.description],
                        location = row[EventsTable.location],
                        price = row[EventsTable.price]
                    )
                }
            }

            if (event != null) {
                call.respond(ApiResponse(success = true, data = event))
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    ApiResponse<Unit>(success = false, message = "Event not found")
                )
            }
        }

        // Create new event
        post {
            val event = call.receive<Event>()
            val eventDate = LocalDate.parse(event.date)

            val id = transaction {
                EventsTable.insert {
                    it[title] = event.title
                    it[date] = eventDate
                    it[description] = event.description
                    it[location] = event.location
                    it[price] = event.price
                } get EventsTable.id
            }

            val newEvent = event.copy(id = id.value)
            call.respond(
                HttpStatusCode.Created,
                ApiResponse(success = true, data = newEvent)
            )
        }

        // Update event
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse<Unit>(success = false, message = "Invalid ID format")
                )
                return@put
            }

            val event = call.receive<Event>()
            val eventDate = LocalDate.parse(event.date)

            val updated = transaction {
                EventsTable.update({ EventsTable.id eq id }) {
                    it[title] = event.title
                    it[date] = eventDate
                    it[description] = event.description
                    it[location] = event.location
                    it[price] = event.price
                    it[updatedAt] = java.time.LocalDateTime.now()
                } > 0
            }

            if (updated) {
                call.respond(
                    ApiResponse(success = true, data = event.copy(id = id))
                )
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    ApiResponse<Unit>(success = false, message = "Event not found")
                )
            }
        }

        // Delete event
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
                val exists = EventsTable.select { EventsTable.id eq id }.count() > 0
                if (exists) {
                    // Use raw SQL for deletion
                    exec("DELETE FROM events WHERE id = $id")
                    true
                } else {
                    false
                }
            }

            if (deleted) {
                call.respond(
                    ApiResponse<Unit>(success = true, message = "Event deleted successfully")
                )
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    ApiResponse<Unit>(success = false, message = "Event not found")
                )
            }
        }
    }
}
