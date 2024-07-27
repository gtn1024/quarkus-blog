package com.github.gtn1024.resource

import com.github.gtn1024.model.Resp
import com.github.gtn1024.model.request.BlogRequest
import com.github.gtn1024.service.IBlogService
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.PATCH
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.Response
import java.util.UUID

@Path("/blog")
@Consumes("application/json")
@Produces("application/json")
class BlogResource
    @Inject
    constructor(
        private val blogService: IBlogService,
    ) {
        @GET
        @Path("{id}")
        fun get(id: UUID): Response {
            val blog = blogService.get(id)
            return Resp.success(blog)
        }

        @GET
        fun list(
            @QueryParam("page") page: Int = 1,
            @QueryParam("pageSize") pageSize: Int = 10,
        ): Response {
            val blogs = blogService.list(page, pageSize, true)

            return Resp.success(blogs.content, blogs.pagination)
        }

        @POST
        fun create(blogRequest: BlogRequest): Response {
            blogService.create(blogRequest)

            return Resp.success(null)
        }

        @PATCH
        @Path("{id}")
        fun patch(
            @PathParam("id") id: UUID,
            blogRequest: BlogRequest,
        ): Response {
            blogService.updateMany(id, blogRequest)

            return Resp.success(null)
        }

        @PUT
        @Path("{id}")
        fun put(
            @PathParam("id") id: UUID,
            blogRequest: BlogRequest,
        ): Response {
            blogService.updateAll(id, blogRequest)

            return Resp.success(null)
        }

        @DELETE
        @Path("{id}")
        fun delete(
            @PathParam("id") id: UUID,
        ): Response {
            blogService.remove(id)

            return Resp.success(null)
        }
    }
