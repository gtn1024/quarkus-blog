package com.github.gtn1024.util

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.PostgreSQLContainer

class PostgresTestResource : QuarkusTestResourceLifecycleManager {
    var pgvector: PostgreSQLContainer<*> = PostgreSQLContainer("pgvector/pgvector:pg16")

    override fun start(): Map<String, String> {
        pgvector.start()
        val conf: MutableMap<String, String> = HashMap()
        conf["quarkus.datasource.jdbc.url"] = pgvector.jdbcUrl
        conf["quarkus.datasource.username"] = pgvector.username
        conf["quarkus.datasource.password"] = pgvector.password
        conf["quarkus.hibernate-orm.database.generation"] = "drop-and-create"

        return conf
    }

    override fun stop() {
        pgvector.stop()
    }
}
