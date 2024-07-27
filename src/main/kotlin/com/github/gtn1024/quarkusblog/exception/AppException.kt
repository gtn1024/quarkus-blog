package com.github.gtn1024.quarkusblog.exception

open class AppException(
    override val message: String,
    val code: Int,
) : RuntimeException(message)
