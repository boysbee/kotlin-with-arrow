package com.sample.function.arrow

import arrow.core.None
import arrow.core.getOrElse
import arrow.data.*
import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.DescribeSpec

class LearnUseIor : DescribeSpec({

    describe("""Let's learn data type "Ior""") {
        it("""should be return "Right" side """) {
            val r = Ior.Right("Ok")
            assertSoftly {
                r.isRight shouldBe true
                r.isLeft shouldNotBe true
                r.isBoth shouldNotBe true
                r.map { "It is $it" }.getOrElse { None } shouldBe "It is Ok"
            }
        }

        it("""should be return "Left" side """) {
            val l = Ior.Left("Failure")
            assertSoftly {
                l.isLeft shouldBe true
                l.isRight shouldNotBe true
                l.isBoth shouldNotBe true
                // Left side can not map a value in container
                l.map { "It is $it" }.getOrElse { None } shouldBe None
            }
        }

        it("""should be return "Both" side """) {
            val b = Ior.Both("Failure", "Ok")
            assertSoftly {
                b.isLeft shouldNotBe true
                b.isRight shouldNotBe true
                b.isBoth shouldBe true
                // Both side can not map a value in Right side like Either.
                b.map { "It is $it" }.getOrElse { None } shouldBe "It is Ok"
            }
        }
    }
    describe("""Initiate "Ior" from extension""") {
        it("""should initiate from method "rightIor" to "Ior.Right" """) {
            val r = "Success".rightIor()
            assertSoftly {
                r.isRight shouldBe true
                r shouldBe Ior.Right("Success")
            }
        }

        it("""should initiate from method "leftIor" to "Ior.Left" """) {
            val l = "Failure".leftIor()
            assertSoftly {
                l.isLeft shouldBe true
                l shouldBe Ior.Left("Failure")
            }
        }

        it("""should initiate from method "bothIor" to "Ior.Both" """) {
            val b = ("Failure" to "Success").bothIor()
            assertSoftly {
                b.isBoth shouldBe true
                b shouldBe Ior.Both("Failure", "Success")
            }
        }
    }
    describe("""We can convert Ior to Either, Validated and Option""") {
        val b = (None to "Success").bothIor()
        val r = "Success".rightIor()
        val l = None.leftIor()
        it("should be the Right side value when convert from Ior.Both to Option") {
            // Ior.Both it should convert to option with the Right side value
            b.toOption().orNull() shouldBe "Success"
            b.toOption().getOrElse { None } shouldBe "Success"
        }
        it("should be the Right side value when convert from Ior.Both to  Either") {
            b.toEither().isRight() shouldBe true
            b.toEither().isLeft() shouldNotBe true
        }
        it("should be the Right side value when convert from Ior.Both to  Validated") {
            b.toValidated().isValid shouldBe true
            b.toValidated().isInvalid shouldNotBe true
        }
        it("should be the Right side value when convert from Ior.Right to Option") {
            r.toOption().orNull() shouldBe "Success"
            r.toOption().getOrElse { None } shouldBe "Success"
        }
        it("should be the Right side value when convert from Ior.Right to  Either") {
            r.toEither().isRight() shouldBe true
            r.toEither().isLeft() shouldNotBe true
        }
        it("should be the Right side value when convert from Ior.Right to  Validated") {
            r.toValidated().isValid shouldBe true
            r.toValidated().isInvalid shouldNotBe true
        }

        it("should be the Left side value when convert from Ior.Left to Option") {
            l.toOption().orNull() shouldBe null
            l.toOption().getOrElse { None } shouldBe None
        }
        it("should be the Left side value when convert from Ior.Left to  Either") {
            l.toEither().isLeft() shouldBe true
            l.toEither().isRight() shouldNotBe true
        }
        it("should be the Left side value when convert from Ior.Left to  Validated") {
            l.toValidated().isInvalid shouldBe true
            l.toValidated().isValid shouldNotBe true
        }
    }
})
