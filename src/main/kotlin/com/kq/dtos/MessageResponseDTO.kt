package com.kq.dtos

import java.util.UUID

data class MessageResponseDTO(
    var id: UUID,
    val message: String
)
