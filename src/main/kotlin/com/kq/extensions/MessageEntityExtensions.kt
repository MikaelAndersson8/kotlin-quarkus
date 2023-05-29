package com.kq.extensions

import com.kq.dtos.MessageResponseDTO
import com.kq.entities.MessageEntity

fun MessageEntity.mapToDTO() = MessageResponseDTO(id = this.id, message = this.message)
fun List<MessageEntity>.mapToDTOs() = this.map {
    it.mapToDTO()
}