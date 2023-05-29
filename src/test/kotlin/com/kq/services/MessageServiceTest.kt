package com.kq.services

import com.kq.entities.MessageEntity
import com.kq.repositories.MessageRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import org.junit.Before
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import java.util.*

class MessageServiceTest {
    private var messageRepository = mockk<MessageRepository>()
    private val messageService = MessageService(messageRepository)
    @Test
    @DisplayName("List all mocked items from repository")
    fun listMessages1() {
        val entity = MessageEntity()
        entity.message = "lmao"
        entity.id = UUID.randomUUID()

        every { messageRepository.listAll() } returns listOf(entity)

        val result = messageService.listMessages()

        assertEquals(result.count(), 1)
    }
}