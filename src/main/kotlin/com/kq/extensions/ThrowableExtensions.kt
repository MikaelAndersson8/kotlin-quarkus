package com.kq.extensions

import com.kq.exceptions.NotFoundException
import jakarta.validation.ValidationException
import jakarta.ws.rs.core.Response

val Throwable.statusCode: Response.Status
    get() {
        if (this is ValidationException) {
            return Response.Status.BAD_REQUEST
        } else if (this is NotFoundException) {
            return Response.Status.NOT_FOUND
        }

        return Response.Status.INTERNAL_SERVER_ERROR
    }
