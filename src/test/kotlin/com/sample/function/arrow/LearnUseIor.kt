package com.sample.function.arrow

import arrow.core.None
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
})
