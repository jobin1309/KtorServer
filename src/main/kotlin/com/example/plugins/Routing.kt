package com.example.plugins

import com.example.routes.employeeRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        employeeRoutes()
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
