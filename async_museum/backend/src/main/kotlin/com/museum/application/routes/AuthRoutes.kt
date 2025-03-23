package com.museum.application.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import com.museum.application.database.tables.UsersTable
import com.museum.application.models.*
import com.museum.application.plugins.generateJwtToken
import java.time.LocalDateTime

// Data class for authentication requests
@kotlinx.serialization.Serializable
data class AuthRequest(
    val token: String,
    val email: String,
    val displayName: String? = null,
    val providerId: String,
    val providerUserId: String
)

fun Route.authRoutes() {
    route("/auth") {
        // Authenticate user (login or register if not exists)
        post("/login") {
            val authRequest = call.receive<AuthRequest>()
            
            // In a real implementation, you would verify the token with the provider (Google, Facebook)
            // For now, we'll trust the token and create/update the user
            
            val user = transaction {
                // Check if user exists
                val existingUser = UsersTable.select { 
                    (UsersTable.email eq authRequest.email) and 
                    (UsersTable.providerId eq authRequest.providerId) 
                }.singleOrNull()
                
                if (existingUser != null) {
                    // Update existing user's last login
                    UsersTable.update({ UsersTable.id eq existingUser[UsersTable.id] }) {
                        it[lastLogin] = LocalDateTime.now()
                        if (authRequest.displayName != null) {
                            it[displayName] = authRequest.displayName
                        }
                    }
                    
                    User(
                        id = existingUser[UsersTable.id].value,
                        email = existingUser[UsersTable.email],
                        displayName = existingUser[UsersTable.displayName],
                        providerId = existingUser[UsersTable.providerId],
                        providerUserId = existingUser[UsersTable.providerUserId]
                    )
                } else {
                    // Create new user
                    val id = UsersTable.insert {
                        it[email] = authRequest.email
                        it[displayName] = authRequest.displayName
                        it[providerId] = authRequest.providerId
                        it[providerUserId] = authRequest.providerUserId
                    } get UsersTable.id
                    
                    User(
                        id = id.value,
                        email = authRequest.email,
                        displayName = authRequest.displayName,
                        providerId = authRequest.providerId,
                        providerUserId = authRequest.providerUserId
                    )
                }
            }
            
            // Generate JWT token
            val jwtSecret = application.environment.config.property("jwt.secret").getString()
            val jwtIssuer = application.environment.config.property("jwt.issuer").getString()
            val jwtAudience = application.environment.config.property("jwt.audience").getString()
            
            val token = generateJwtToken(
                userId = user.id.toString(),
                jwtSecret = jwtSecret,
                jwtIssuer = jwtIssuer,
                jwtAudience = jwtAudience
            )
            
            call.respond(
                ApiResponse(
                    success = true,
                    data = AuthResponse(token = token, user = user)
                )
            )
        }
        
        // Get current user info
        get("/user") {
            // In a real implementation, this would be protected by JWT authentication
            // and would return the current user's info
            call.respond(
                HttpStatusCode.NotImplemented,
                ApiResponse<Unit>(success = false, message = "Not implemented yet")
            )
        }
        
        // Logout
        post("/logout") {
            // In a real implementation, this would invalidate the JWT token
            // For now, we'll just return success
            call.respond(
                ApiResponse<Unit>(success = true, message = "Logged out successfully")
            )
        }
    }
}