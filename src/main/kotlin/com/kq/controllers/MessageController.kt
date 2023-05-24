package com.kq.controllers

import com.kq.dtos.MessageCreateDTO
import com.kq.dtos.MessageEditDTO
import com.kq.services.MessageService
import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.util.UUID

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class MessageController(@Inject var messageService: MessageService) {
    @GET
    @Path(value = "/{id}")
    fun getMessage(@PathParam("id") id: UUID) = this.messageService.getMessage(id)

    @GET
    fun getAllMessages() = this.messageService.listMessages()

    @POST
    fun createMessage(@Valid dto: MessageCreateDTO): Response = Response
        .status(Response.Status.CREATED)
        .entity(this.messageService.createMessage(dto))
        .build()

    @PUT
    fun editMessage(@Valid dto: MessageEditDTO): Response = Response
        .ok(this.messageService.editMessage(dto))
        .build()

    @DELETE
    @Path(value = "/{id}")
    fun deleteMessage(@PathParam("id") id: UUID): Response {
        this.messageService.deleteMessage(id)

        return Response.noContent().build();
    }

}