package com.github.gtn1024.quarkusblog.model

import jakarta.ws.rs.core.Response

data class Resp<T>(
    val error: String? = null,
    val data: T? = null,
    val pagination: Pagination? = null,
) {
    companion object {
        fun <T> success(
            data: T? = null,
            pagination: Pagination? = null,
        ): Response {
            return Response.status(200).entity(Resp(null, data, pagination)).build()
        }

        fun fail(
            error: String,
            code: Int,
        ): Response {
            return Response.status(code).entity(Resp(error, null, null)).build()
        }

        fun notFound(): Response {
            return fail("Not Found", 404)
        }
    }
}

data class Pagination(
    val current: Int,
    val pageSize: Int,
    val total: Long,
    val pages: Long,
)

data class Page<T>(
    val content: List<T>,
    val pagination: Pagination,
)
