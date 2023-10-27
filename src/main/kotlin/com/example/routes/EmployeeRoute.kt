package com.example.routes

import com.example.data.createEmployeeOrUpdateEmployeeForId
import com.example.data.deleteEmployeeForId
import com.example.data.getEmployeeForId
import com.example.data.model.Employee
import com.example.data.request.DeleteEmployeeRequest
import com.example.data.request.EmployeeRequest
import com.example.data.request.SimpleResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.employeeRoutes() {
    route("/get-employee") {
        get {
           val employeeId = call.receive<EmployeeRequest>().id
            val employee = getEmployeeForId(employeeId)

            employee?.let {
                call.respond(
                    HttpStatusCode.OK,
                    SimpleResponse(true, "Response success", it)
                )
            } ?: call.respond(
                HttpStatusCode.OK,
                SimpleResponse(false, "Response failed", Unit)

            )
        }
    }

    route("/create-update-employee") {
        post {
            val request = try {
                call.receive<Employee>()

            } catch (e:ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            if(createEmployeeOrUpdateEmployeeForId(request)) {
                call.respond(
                    HttpStatusCode.OK,
                    SimpleResponse(true, "Employee successfully created/updated", it)
                    )
            } else {
                call.respond(HttpStatusCode.Conflict)
            }
        }
    }

    route("/delete-employee") {
        post {
            val request = try {
                call.receive<DeleteEmployeeRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            if(deleteEmployeeForId(request.id)) {
                call.respond(
                    HttpStatusCode.OK,
                    SimpleResponse(true, "Employee successfully deleted", Unit)
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    SimpleResponse(false, "Employee not found", Unit)
                )
            }
        }
    }

}
