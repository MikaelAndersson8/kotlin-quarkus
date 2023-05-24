package com.kq.repositories

import com.kq.entities.MessageEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import java.util.UUID

@ApplicationScoped
class MessageRepository: PanacheRepositoryBase<MessageEntity, UUID> {
}