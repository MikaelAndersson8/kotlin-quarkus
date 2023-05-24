package com.kq.entities


import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "ktl")
class MessageEntity {
    @Id
    lateinit var id: UUID;
    lateinit var message: String
}
