package com.museum.application.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import com.museum.application.database.tables.VisitorInfoTable
import com.museum.application.models.*

fun Route.visitorInfoRoutes() {
    route("/visitor-info") {
        // Get visitor information
        get {
            val visitorInfo = transaction {
                VisitorInfoTable.selectAll().firstOrNull()?.let { row ->
                    VisitorInfo(
                        hours = row[VisitorInfoTable.hours],
                        admission = Admission(
                            adult = row[VisitorInfoTable.adultAdmission].toDouble(),
                            child = row[VisitorInfoTable.childAdmission].toDouble(),
                            senior = row[VisitorInfoTable.seniorAdmission].toDouble()
                        ),
                        location = row[VisitorInfoTable.location],
                        contact = Contact(
                            phone = row[VisitorInfoTable.phone],
                            email = row[VisitorInfoTable.email]
                        )
                    )
                }
            }

            if (visitorInfo != null) {
                call.respond(ApiResponse(success = true, data = visitorInfo))
            } else {
                // If no visitor info exists, create default one
                val defaultInfo = createDefaultVisitorInfo()
                call.respond(ApiResponse(success = true, data = defaultInfo))
            }
        }

        // Update visitor information
        put {
            val visitorInfoRequest = call.receive<VisitorInfo>()
            
            val updated = transaction {
                val existingRow = VisitorInfoTable.selectAll().firstOrNull()
                
                if (existingRow != null) {
                    // Update existing row
                    VisitorInfoTable.update({ VisitorInfoTable.id eq existingRow[VisitorInfoTable.id] }) {
                        it[hours] = visitorInfoRequest.hours
                        it[adultAdmission] = visitorInfoRequest.admission.adult.toBigDecimal()
                        it[childAdmission] = visitorInfoRequest.admission.child.toBigDecimal()
                        it[seniorAdmission] = visitorInfoRequest.admission.senior.toBigDecimal()
                        it[location] = visitorInfoRequest.location
                        it[phone] = visitorInfoRequest.contact.phone
                        it[email] = visitorInfoRequest.contact.email
                        it[updatedAt] = java.time.LocalDateTime.now()
                    }
                    true
                } else {
                    // Insert new row
                    VisitorInfoTable.insert {
                        it[hours] = visitorInfoRequest.hours
                        it[adultAdmission] = visitorInfoRequest.admission.adult.toBigDecimal()
                        it[childAdmission] = visitorInfoRequest.admission.child.toBigDecimal()
                        it[seniorAdmission] = visitorInfoRequest.admission.senior.toBigDecimal()
                        it[location] = visitorInfoRequest.location
                        it[phone] = visitorInfoRequest.contact.phone
                        it[email] = visitorInfoRequest.contact.email
                    }
                    true
                }
            }

            if (updated) {
                call.respond(
                    ApiResponse(success = true, data = visitorInfoRequest)
                )
            } else {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ApiResponse<Unit>(success = false, message = "Failed to update visitor information")
                )
            }
        }
    }
}

// Helper function to create default visitor information
private fun createDefaultVisitorInfo(): VisitorInfo {
    val defaultInfo = VisitorInfo(
        hours = "Monday - Sunday: 9:00 AM - 5:00 PM",
        admission = Admission(
            adult = 15.0,
            child = 8.0,
            senior = 10.0
        ),
        location = "123 Museum Street, City, Country",
        contact = Contact(
            phone = "(123) 456-7890",
            email = "info@museumofnaturalhistory.com"
        )
    )
    
    transaction {
        VisitorInfoTable.insert {
            it[hours] = defaultInfo.hours
            it[adultAdmission] = defaultInfo.admission.adult.toBigDecimal()
            it[childAdmission] = defaultInfo.admission.child.toBigDecimal()
            it[seniorAdmission] = defaultInfo.admission.senior.toBigDecimal()
            it[location] = defaultInfo.location
            it[phone] = defaultInfo.contact.phone
            it[email] = defaultInfo.contact.email
        }
    }
    
    return defaultInfo
}