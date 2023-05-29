package com.kq.exceptions

import com.kq.dtos.ErrorDTO
import com.kq.extensions.getStatusCode
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class ThrowableMapper : ExceptionMapper<Throwable> {
    override fun toResponse(throwable: Throwable?): Response = Response
            .status(throwable?.getStatusCode() ?: Response.Status.INTERNAL_SERVER_ERROR)
            .entity(ErrorDTO(message = throwable?.message))
            .build()
}
