package com.kq.controllers

import com.kq.dtos.MessageCreateDTO
import com.kq.dtos.MessageEditDTO
import com.kq.dtos.MessageResponseDTO
import com.kq.fixtures.validMessageEntityFixture
import com.kq.repositories.MessageRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS


@QuarkusTest
@TestHTTPEndpoint(MessageController::class)
@TestInstance(PER_CLASS)
class MessageControllerTest {
    private final val messageRepository = mockk<MessageRepository>()

    @BeforeAll
    fun setupMocks() {
        QuarkusMock.installMockForType(messageRepository, MessageRepository::class.java)
    }

    @Test
    @DisplayName("List all items, expect list with 1 item")
    fun getAll() {
        every { messageRepository.listAll() } returns listOf(validMessageEntityFixture)

        given()
            .`when`()
            .get()
            .then()
            .statusCode(200)
            .body("size()", equalTo(1))
    }

    @Test
    @DisplayName("Get item by id, expect specific text")
    fun getById() {
        every { messageRepository.findById(any()) } returns validMessageEntityFixture

        given()
            .`when`()
            .get("/bf79f251-0d1d-4ddb-bdae-956b2c02887d")
            .then()
            .body("message", equalTo("Some nice text"))
            .statusCode(200)
    }

    @Test
    @DisplayName("Post item, expect 201 created")
    fun createItem() {
        every { messageRepository.persist(entity = any()) } just Runs

        given()
            .contentType(ContentType.JSON)
            .body(MessageCreateDTO(message = "Test"))
            .`when`()
            .post()
            .then()
            .statusCode(201)
    }

    @Test
    @DisplayName("Edit text on item, expect 200 and text on response to be set from request")
    fun editMessage() {
        val message = "New Message";

        every { messageRepository.findById(any()) } returns validMessageEntityFixture
        every { messageRepository.persist(entity = any()) } just Runs

        val result = given()
            .contentType(ContentType.JSON)
            .body(MessageEditDTO(id = validMessageEntityFixture.id, message = message))
            .`when`()
            .put()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(MessageResponseDTO::class.java)

        assertNotNull(result)
        assertEquals(message, result.message)
    }
}