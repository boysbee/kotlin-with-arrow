package com.sample.function.arrow

import arrow.core.*
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

        describe("Option from nullable") {
            val nullable = Option.fromNullable(null)
            it("should be return other value from nullable") {
                assertEquals("b", nullable.getOrElse { "b" })
            }

            it("""should be "None" from nullable""") {
                assertEquals(None, nullable)
            }
        }

        describe("You can create Option from null value with arrow-kt") {
            it("should be initial from nullable value") {
                val nullable: String? = null
                val nullableOption = nullable.toOption()
                assertEquals(None, nullableOption)
            }
        }

    }
})