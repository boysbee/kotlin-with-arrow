package com.sample.function

import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object LearnFunctionalTest : Spek({
    describe("Learn basic function in kotlin in basic demo") {
        val demo = BasicIKnew()
        describe("function convert Integer to String") {
            it("should return a message") {
                val intToMessage = demo.convIntToMessage(
                    1,
                    fn = { i -> "function received function as parameter and convert to string message $i" })

                assertEquals("function received function as parameter and convert to string message 1", intToMessage)
            }
        }
    }
})
