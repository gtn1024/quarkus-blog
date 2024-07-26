package com.github.gtn1024.exception

open class AppException(
    override val message: String,
    val code: Int,
) : RuntimeException(message)
