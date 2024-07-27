package com.github.gtn1024.quarkusblog.config

import com.github.gtn1024.quarkusblog.exception.AppException
import com.github.gtn1024.quarkusblog.model.Resp
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Provider
class GlobalIllegalArgumentExceptionHandler(
    private val logger: Logger = LoggerFactory.getLogger(GlobalIllegalArgumentExceptionHandler::class.java),
) : ExceptionMapper<IllegalArgumentException> {
    override fun toResponse(exception: IllegalArgumentException): Response {
        logger.error("IllegalArgumentException: ${exception.message}", exception)
        return Resp.fail(exception.message ?: "Illegal Argument", 400)
    }
}

@Provider
class GlobalAppExceptionHandler(
    private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java),
) : ExceptionMapper<AppException> {
    override fun toResponse(exception: AppException): Response {
        logger.error("AppException: ${exception.message}", exception)
        return Resp.fail(exception.message, exception.code)
    }
}

@Provider
class GlobalExceptionHandler(
    private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java),
) : ExceptionMapper<Exception> {
    override fun toResponse(exception: Exception): Response {
        logger.error("Exception: ${exception.message}", exception)
        return Resp.fail(exception.message ?: "Internal Server Error", 500)
    }
}
