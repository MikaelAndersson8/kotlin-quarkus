package com.kq.entities


import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "ktl")
class MessageEntity() {
    constructor(id: UUID, message: String) : this() {
        this.id = id
        this.message = message
    }

    @Id
    lateinit var id: UUID;
    lateinit var message: String
}
