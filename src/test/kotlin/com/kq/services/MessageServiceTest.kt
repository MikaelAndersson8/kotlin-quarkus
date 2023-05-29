package com.kq.services

import com.kq.fixtures.validMessageEntityFixture
import com.kq.repositories.MessageRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import java.util.*

class MessageServiceTest {
    private var messageRepository = mockk<MessageRepository>()
    private val messageService = MessageService(messageRepository)

    @Test
    @DisplayName("List all mocked items from repository and check length and text")
    fun listMessages() {
        every { messageRepository.listAll() } returns listOf(validMessageEntityFixture)

        val result = messageService.listMessages()

        assertEquals(result.count(), 1)
        assertEquals(result.firstOrNull()?.message, validMessageEntityFixture.message)
    }

    @Test
    @DisplayName("Get specific item and check text")
    fun getMessage() {
        every { messageRepository.findById(any()) } returns validMessageEntityFixture

        val result = messageService.getMessage(UUID.randomUUID())

        assertEquals(result.message, validMessageEntityFixture.message)
    }
}