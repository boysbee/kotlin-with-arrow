package com.sample.function.arrow

import arrow.core.Option
import arrow.core.Some
import arrow.core.getOrElse
import org.junit.jupiter.api.Assertions.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe


object LearnUseOption : Spek({

    describe("Learn Option here") {
        describe("Initial Option with just a value") {
            val a = Option.just("a")
            it("should just value a") {
                assertEquals("a", a.orNull())
            }
            it("""should be "Some("a")" """) {
                assertEquals(a, Some("a"))
            }
            it("should be defined") {
                assertTrue(a.isDefined())
            }
            it("should be not empty") {
                assertFalse(a.isEmpty())
            }
            it("""should return "a" when "getOrElse" from just "a"""") {
                assertEquals("a", a.getOrElse { "b" })
            }
        }

    }
})