package com.github.gtn1024.resource

import com.github.gtn1024.model.Resp
import com.github.gtn1024.model.entity.BlogEntity
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import jakarta.inject.Inject
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.UUID

@QuarkusTest
class BlogResourceTest {
    @Inject
    lateinit var entityManager: EntityManager
    val logger: Logger = LoggerFactory.getLogger(BlogResourceTest::class.java)

    companion object {
        var data: List<BlogEntity> = emptyList()
    }

    @BeforeEach
    @Transactional
    fun setUp() {
        // truncate table
        entityManager.createNativeQuery("TRUNCATE TABLE t_blog").executeUpdate()

        data = listOf(
            BlogEntity().apply {
                title = "title1"
                content = "content1"
            },
            BlogEntity().apply {
                title = "title2"
                content = "content2"
            },
            BlogEntity().apply {
                title = "title3"
                content = "content3"
            },
            BlogEntity().apply {
                title = "title4"
                content = "content4"
            },
            BlogEntity().apply {
                title = "title5"
                content = "content5"
            },
        )

        // insert data
        data.forEach { it.persist() }
    }

    @Test
    fun `test get not found`() {
        val id: String
        for (d in data) {
            logger.info("id: {}", d.id)
        }
        while (true) {
            val uuid = UUID.randomUUID().toString()
            if (data.none { it.id.toString() == uuid }) {
                id = uuid
                break
            }
        }
        logger.info("Generated id: {}", id)

        given()
            .`when`()
            .get("/blog/${id}")
            .then()
            .statusCode(404)
    }

    @Test
    fun `test get present`() {
        val id = data[0].id.toString()
        logger.info("id: {}", id)

        val resp: Resp<BlogEntity> = given()
            .`when`()
            .get("/blog/${id}")
            .then()
            .statusCode(200)
            .extract()
            .`as`(object : TypeRef<Resp<BlogEntity>>() { })

        assertEquals(data[0].title, resp.data?.title)
        assertEquals(data[0].content, resp.data?.content)
    }

    @Test
    fun `list without pagination`() {
        val resp: Resp<List<BlogEntity>> = given()
            .`when`()
            .get("/blog")
            .then()
            .statusCode(200)
            .extract()
            .`as`(object : TypeRef<Resp<List<BlogEntity>>>() { })

        assertEquals(data.size, resp.data?.size)
    }

    @Test
    fun `list with pagination`() {
        val resp: Resp<List<BlogEntity>> = given()
            .`when`()
            .get("/blog?page=1&pageSize=2")
            .then()
            .statusCode(200)
            .extract()
            .`as`(object : TypeRef<Resp<List<BlogEntity>>>() { })

        assertEquals(2, resp.data?.size)
    }

    @Test
    fun create() {
        val data = mapOf(
            "title" to "title6",
            "content" to "content6",
        )

        given()
            .contentType("application/json")
            .body(data)
            .`when`()
            .post("/blog")
            .then()
            .statusCode(200)

        val count = BlogEntity.count()
        assertEquals(6, count)
    }

    @Test
    fun patch() {
        val uuid = data[0].id!!
        val data = mapOf(
            "title" to "title6",
        )

        given()
            .contentType("application/json")
            .body(data)
            .`when`()
            .patch("/blog/${uuid}")
            .then()
            .statusCode(200)

        val blog = BlogEntity.findById(uuid)
        assertEquals(data["title"], blog?.title)
    }

    @Test
    fun put() {
        val uuid = data[0].id!!
        val data = mapOf(
            "title" to "title6",
            "content" to "content6",
        )

        given()
            .contentType("application/json")
            .body(data)
            .`when`()
            .put("/blog/${uuid}")
            .then()
            .statusCode(200)

        val blog = BlogEntity.findById(uuid)
        assertEquals(data["title"], blog?.title)
        assertEquals(data["content"], blog?.content)
    }

    @Test
    fun delete() {
        val uuid = data[0].id!!

        given()
            .`when`()
            .delete("/blog/${uuid}")
            .then()
            .statusCode(200)

        val count = BlogEntity.count()
        assertEquals(4, count)
    }
}
