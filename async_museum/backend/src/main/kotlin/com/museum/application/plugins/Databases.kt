package com.museum.application.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import com.museum.application.database.tables.*

fun Application.configureDatabases() {
    val config = HikariConfig().apply {
        driverClassName = environment.config.property("database.driverClassName").getString()
        jdbcUrl = environment.config.property("database.jdbcURL").getString()
        username = environment.config.property("database.username").getString()
        password = environment.config.property("database.password").getString()
        maximumPoolSize = environment.config.property("database.maxPoolSize").getString().toInt()
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }

    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)

    // Create tables if they don't exist
    transaction {
        SchemaUtils.create(
            ExhibitsTable,
            EventsTable,
            VisitorInfoTable,
            UsersTable,
            ContactSubmissionsTable
        )
    }
}