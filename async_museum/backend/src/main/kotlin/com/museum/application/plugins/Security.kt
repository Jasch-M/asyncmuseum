package com.museum.application.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    // Hardcoded values for development
    val jwtSecret = "secure_random_jwt_secret_for_museum_app"
    val jwtIssuer = "http://0.0.0.0:8080/"
    val jwtAudience = "http://0.0.0.0:8080/museum"
    val jwtRealm = "Museum application"

    install(Authentication) {
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}

// Helper function to generate JWT tokens
fun generateJwtToken(userId: String, jwtSecret: String, jwtIssuer: String, jwtAudience: String): String {
    return JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .withClaim("userId", userId)
        .sign(Algorithm.HMAC256(jwtSecret))
}
