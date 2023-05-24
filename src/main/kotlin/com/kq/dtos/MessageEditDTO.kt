package com.kq.dtos

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class MessageEditDTO(
    @field:NotNull var id: UUID,
    @field:Size(min = 2, max = 250) val message: String
)
