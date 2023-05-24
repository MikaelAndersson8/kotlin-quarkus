package com.kq.services

import com.kq.dtos.MessageCreateDTO
import com.kq.repositories.MessageRepository
import com.kq.dtos.MessageResponseDTO
import com.kq.dtos.MessageEditDTO
import com.kq.entities.MessageEntity
import com.kq.exceptions.NotFoundException
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional

import org.jboss.logging.Logger
import java.util.UUID

@ApplicationScoped
class MessageService(@Inject private var messageRepository: MessageRepository) {
    private val logger: Logger = Logger.getLogger(MessageService::class.java)

    @Transactional
    fun listMessages(): List<MessageResponseDTO> = this.messageRepository.listAll().map {
        MessageResponseDTO(message = it.message, id = it.id)
    }

    @Transactional
    fun getMessage(id: UUID): MessageResponseDTO {
        val entity = this.getEntityById(id)

        return MessageResponseDTO(id = entity.id, message = entity.message)
    }

    @Transactional
    fun createMessage(dto: MessageCreateDTO): MessageResponseDTO {
        this.logger.info("Creating new entity with message: ${dto.message}")

        val entity = MessageEntity();
        entity.id = UUID.randomUUID()
        entity.message = dto.message

        this.messageRepository.persist(entity)

        return MessageResponseDTO(entity.id, message = entity.message)
    }


    @Transactional
    fun editMessage(dto: MessageEditDTO): MessageResponseDTO {
        this.logger.info("Editing entity with id: ${dto.id}, setting new message to: ${dto.message}")

        val entity = this.getEntityById(dto.id)
        entity.message = dto.message

        messageRepository.persist(entity)

        return MessageResponseDTO(entity.id, message = entity.message)
    }

    @Transactional
    fun deleteMessage(id: UUID) {
        this.logger.info("Deleting entity with id: $id")

        val success = this.messageRepository.deleteById(id)
        if (!success) {
            throw NotFoundException("entity with id: $id not found")
        }
    }

    private fun getEntityById(id: UUID) = this.messageRepository.findById(id)
        ?: throw NotFoundException("entity with id: $id not found")
}