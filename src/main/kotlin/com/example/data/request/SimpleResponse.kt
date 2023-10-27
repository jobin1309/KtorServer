package com.example.data.request

data class SimpleResponse<T> (
    val status: Boolean,
    val message: String,
    val data: T
    )

