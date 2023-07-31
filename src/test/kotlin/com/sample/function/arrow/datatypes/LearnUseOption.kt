package com.sample.function.arrow.datatypes

import arrow.core.*
import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.Assertions.*


object LearnUseOption : DescribeSpec({

    describe("Learn Option here") {
        describe("Initial Option with just a value") {
            val a = "a".toOption()
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

        describe("""Option with "Some""") {
            val someA = Some("a")
            it("should just value a") {
                assertEquals("a", someA.orNull())
            }
            it("""should be "Some("a")" """) {
                assertEquals(someA, Some("a"))
            }
            it("should be defined") {
                assertTrue(someA.isDefined())
            }
            it("should be not empty") {
                assertFalse(someA.isEmpty())
            }
            it("""should return "a" when "getOrElse" from just "a"""") {
                assertEquals("a", someA.getOrElse { "b" })
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

            it("should be undefined") {
                assertFalse(nullable.isDefined())
            }

            it("should be empty") {
                assertTrue(nullable.isEmpty())
            }
        }

        describe("""Option from "None" """) {
            val none = None
            it("""should be "None" """) {
                assertEquals(none, None)
            }

            it("""should be return other value from "None""") {
                assertEquals("default", none.getOrElse { "default" })
            }

            it("should be undefined") {
                assertFalse(none.isDefined())
            }

            it("should be empty") {
                assertTrue(none.isEmpty())
            }
        }

        describe("You can create Option from null value with arrow-kt") {
            it("should be initial from nullable value") {
                val nullable: String? = null
                val nullableOption = nullable.toOption()
                assertEquals(None, nullableOption)
            }
        }

        describe("You can use when statement to check option") {
            val aFromJustA = "a".toOption()
            val bFromNullable = Option.fromNullable(null)
            // lambda
            val checkType: (Option<String>) -> Boolean = { c: Option<String> ->
                when (c) {
                    is Some -> true
                    is None -> false
                    else -> false
                }
            }
            it("""should return "a" when we use when statement to check a""") {
                assertTrue(
                    checkType(aFromJustA)
                )
            }

            it("""should retun "is null" when use statement to check b """) {
                assertFalse(checkType(bFromNullable))
            }
        }

        describe("""Use flatMap to sequence computation""") {
            val a: Option<Int> = 1.toOption()
            val b: Option<Int> = 2.toOption()
            val c: Option<Int> = None
            it("""should result is 3 when computation value of a and b""") {
                val result = a.flatMap { f1 -> b.flatMap { f2 -> Some(f1 + f2) } }
                assertEquals(3, result.getOrElse { 0 })
            }

            it("""should "0" computation value of a, b and c when c is "None" """) {
                val result = a.flatMap { f1 -> b.flatMap { f2 -> c.flatMap { f3 -> Some(f1 + f2 + f3) } } }
                assertEquals(0, result.getOrElse { 0 })
            }
        }

        describe("""Use Monad ( binding )  to sequence computation""") {
            val a: Option<Int> = 1.toOption()
            val b: Option<Int> = 2.toOption()
            val c: Option<Int> = None
            // Look like the same as `flatMap` operation but like sequential computation
            it("""should result is 3 when computation value of a and b""") {
                val result = a.flatMap { f1 ->
                    b.flatMap { f2 ->
                        (f1 + f2).toOption()
                    }
                }
                assertEquals(3, result.getOrElse { 0 })
            }

            it("""should "0" computation value of a, b and c when c is "None" """) {
                val result = a.flatMap { f1 ->
                    b.flatMap { f2 ->
                        c.flatMap { f3 ->
                            (f1 + f2 + f3).toOption()
                        }
                    }
                }
                assertEquals(0, result.getOrElse { 0 })
            }
        }

    }
})