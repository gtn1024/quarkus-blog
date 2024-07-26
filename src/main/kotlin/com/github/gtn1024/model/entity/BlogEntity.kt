package com.github.gtn1024.model.entity

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.util.UUID

@Entity(name = "t_blog")
class BlogEntity : BaseEntity() {
    companion object : PanacheCompanion<BlogEntity> {
        fun findById(id: UUID): BlogEntity? {
            return find("id", id).firstResult()
        }
    }

    @Column(nullable = false)
    lateinit var title: String

    @Column(nullable = false, columnDefinition = "TEXT")
    lateinit var content: String
}
