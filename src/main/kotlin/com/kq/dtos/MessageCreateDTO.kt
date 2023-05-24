package com.kq.dtos

import jakarta.validation.constraints.Size

data class MessageCreateDTO(
    @field:Size(min = 2, max = 250) val message: String
)
