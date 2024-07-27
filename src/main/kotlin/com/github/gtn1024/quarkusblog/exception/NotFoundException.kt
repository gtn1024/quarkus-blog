package com.github.gtn1024.quarkusblog.exception

class NotFoundException(
    override val message: String,
) : AppException(message, 404)
