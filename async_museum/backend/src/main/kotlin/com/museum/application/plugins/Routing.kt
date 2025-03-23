package com.museum.application.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.museum.application.routes.*

fun Application.configureRouting() {
    routing {
        // Health check endpoint
        get("/health") {
            call.respond(mapOf("status" to "UP"))
        }

        // API routes
        route("/api") {
            exhibitRoutes()
            eventRoutes()
            visitorInfoRoutes()
            authRoutes()
            contactRoutes()
        }
    }
}