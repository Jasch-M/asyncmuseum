package com.museum.application

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.museum.application.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    // Configure plugins
    configureSerialization()
    configureCORS()
    configureSecurity()
    configureRouting()
    
    // Configure database
    configureDatabases()
}