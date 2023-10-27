package com.example.data

import com.example.data.model.Employee
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.KMongo
import org.litote.kmongo.reactivestreams.*
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.util.idValue

private val client = KMongo.createClient()
private val database = client.getDatabase(
    "CompanyDatabase")

private val employees = database.getCollection<Employee>()


suspend fun getEmployeeForId(id: String): Employee? {
    return employees.findOneById(id)
}

suspend fun createEmployeeOrUpdateEmployeeForId(employee: Employee): Boolean {
    val employeeExists = employees.findOneById(employee.id) != null
    return if(employeeExists) {
        employees.updateOneById(employee.id, employee).wasAcknowledged()  //was acknowledged returns true if query was successfully
    }
    else {
        employee.id = ObjectId().toString()
        employees.insertOne(employee).wasAcknowledged()
    }
}


suspend fun deleteEmployeeForId(employeeId: String): Boolean {
    val employee = employees.findOne(Employee::id eq employeeId)
    employee?.let {employee ->
        return employees.deleteOneById(employee.id).wasAcknowledged()

    } ?: return false
}




