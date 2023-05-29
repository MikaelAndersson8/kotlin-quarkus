package com.kq.services

import com.kq.dtos.MessageCreateDTO
import com.kq.dtos.MessageEditDTO
import com.kq.dtos.MessageResponseDTO
import com.kq.entities.MessageEntity
import com.kq.exceptions.NotFoundException
import com.kq.extensions.mapToDTO
import com.kq.extensions.mapToDTOs
import com.kq.repositories.MessageRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.jboss.logging.Logger
import java.util.*

@ApplicationScoped
class MessageService(@Inject private var messageRepository: MessageRepository) {
    private val logger: Logger = Logger.getLogger(MessageService::class.java)

    @Transactional
    fun listMessages(): List<MessageResponseDTO> = this.messageRepository.listAll().mapToDTOs()

    @Transactional
    fun getMessage(id: UUID): MessageResponseDTO {
        val entity = this.getEntityById(id)

        return entity.mapToDTO()
    }

    @Transactional
    fun createMessage(dto: MessageCreateDTO): UUID {
        this.logger.info("Creating new entity with message: ${dto.message}")

        val entity = MessageEntity(id = UUID.randomUUID(), message = dto.message)

        this.messageRepository.persist(entity)

        return entity.id
    }


    @Transactional
    fun editMessage(dto: MessageEditDTO): MessageResponseDTO {
        this.logger.info("Editing entity with id: ${dto.id}, setting new message to: ${dto.message}")

        val entity = this.getEntityById(dto.id)
        entity.message = dto.message

        messageRepository.persist(entity)

        return entity.mapToDTO()
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