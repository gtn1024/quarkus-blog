package com.github.gtn1024.service

import com.github.gtn1024.exception.NotFoundException
import com.github.gtn1024.model.Page
import com.github.gtn1024.model.Pagination
import com.github.gtn1024.model.entity.BlogEntity
import com.github.gtn1024.model.request.BlogRequest
import io.quarkus.panache.common.Sort
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.util.UUID

interface IBlogService {
    fun get(id: UUID): BlogEntity?

    fun create(blogRequest: BlogRequest): BlogEntity

    fun updateMany(
        id: UUID,
        blogRequest: BlogRequest,
    ): BlogEntity

    fun updateAll(
        id: UUID,
        blogRequest: BlogRequest,
    ): BlogEntity

    fun remove(id: UUID)

    fun list(
        page: Int,
        pageSize: Int,
        desc: Boolean,
    ): Page<BlogEntity>
}

@ApplicationScoped
class BlogService : IBlogService {
    override fun get(id: UUID): BlogEntity? {
        return BlogEntity.findById(id)
    }

    @Transactional
    override fun create(blogRequest: BlogRequest): BlogEntity {
        val (title, content) = blogRequest
        require(!title.isNullOrBlank()) { "Title must not be empty" }
        require(!content.isNullOrBlank()) { "Content must not be empty" }

        val blog =
            BlogEntity().apply {
                this.title = title
                this.content = content
            }
        blog.persist()
        return blog
    }

    @Transactional
    override fun updateMany(
        id: UUID,
        blogRequest: BlogRequest,
    ): BlogEntity {
        val blog = BlogEntity.findById(id) ?: throw NotFoundException("Blog $id not found")

        val (title, content) = blogRequest
        if (!title.isNullOrBlank()) {
            blog.title = title
        }
        if (!content.isNullOrBlank()) {
            blog.content = content
        }
        blog.persist()

        return blog
    }

    @Transactional
    override fun updateAll(
        id: UUID,
        blogRequest: BlogRequest,
    ): BlogEntity {
        val blog = BlogEntity.findById(id) ?: throw NotFoundException("Blog $id not found")

        val (title, content) = blogRequest
        require(!title.isNullOrBlank()) { "Title must not be empty" }
        require(!content.isNullOrBlank()) { "Content must not be empty" }

        blog.title = title
        blog.content = content
        blog.persist()

        return blog
    }

    @Transactional
    override fun remove(id: UUID) {
        val blog = BlogEntity.findById(id) ?: throw NotFoundException("Blog $id not found")

        blog.delete()
    }

    override fun list(
        page: Int,
        pageSize: Int,
        desc: Boolean,
    ): Page<BlogEntity> {
        val sort =
            Sort.by("createdAt")
                .direction(if (desc) Sort.Direction.Descending else Sort.Direction.Ascending)
        val list =
            BlogEntity
                .findAll(sort)
                .page(page - 1, pageSize)
                .list()
        val allCount = BlogEntity.count()

        return Page(
            list,
            Pagination(
                page,
                pageSize,
                allCount,
                (allCount + pageSize - 1) / pageSize,
            ),
        )
    }
}
