package com.sample.function.arrow

import arrow.core.Option
import arrow.core.Some
import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe


object LearnUseOption : Spek({

    describe("Learn Option here") {
        describe("Initial Option with just a value") {
            val a = Option.just("a")
            it("should just value a") {
                assertEquals("a", a.orNull())
            }
            it("should be `some` \"a\"") {
                assertEquals(a, Some("a"))
            }
        }

    }
})