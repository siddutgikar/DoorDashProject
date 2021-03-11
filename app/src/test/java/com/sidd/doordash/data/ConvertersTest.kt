package com.sidd.doordash.data

import org.junit.Assert
import org.junit.Test

class ConvertersTest {

    private val testString =
        "{\"asap_minutes_range\":[36,36]}"

    private val status = Status(arrayOf(36, 36))

    @Test
    fun stringToStatus() {
        Assert.assertEquals(status, Converters().statusFromString(testString))
    }

    @Test
    fun statusToString() {
        Assert.assertEquals(Converters().stringFromStatus(status), testString)
    }

}