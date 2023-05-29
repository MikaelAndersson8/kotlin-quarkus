package com.kq.extensions

import com.kq.fixtures.validMessageEntityFixture
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class MessageEntityExtensionsKtTest {
    @Test
    fun mapToDTO() {
        val result = validMessageEntityFixture.mapToDTO()

        assertEquals("Some nice text", result.message)
        assertNotNull(result.id)
    }
}