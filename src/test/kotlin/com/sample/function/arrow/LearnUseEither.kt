package com.sample.function.arrow

import arrow.core.*
import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.DescribeSpec

class LearnUseEither : DescribeSpec({
    describe("""Either, learn to use arrow-kt data type "Either" """) {
        // Let's start with how to initiate an instance of either data type.
        // Right side for successful value
        val r: Either<String, String> = Either.right("success")
        // Left side for failure value
        val l: Either<String, String> = Either.left("failure")
        it("should return true if right is right side") {
            r.isRight() shouldBe true
        }
        it("should return false if right is not left side") {
            r.isLeft() shouldNotBe true
        }
        it("should return true if left is left side") {
            l.isLeft() shouldBe true
        }

        it("should return false if left is not right side") {
            l.isRight() shouldNotBe true
        }

    }
    describe("""Either in arrow-kt can initiate instance with arbitrary data types""") {
        it("should initiate with Int") {
            val r: Either<Nothing, Int> = 7.right()
            val l: Either<Int, Int> = (-1).left()
            /*
            left() with Nothing can not compile.
            val lFromNothing: Either<Nothing, Int> = Nothing.left()
            */
            assertSoftly {
                r.isRight() shouldBe true
                r.getOrElse { -1 } shouldBe (7)
                l.isLeft() shouldBe true
                // mapLeft should be return Either.Left
                l.mapLeft { it + 0 } shouldBe Either.Left((-1))
            }
        }
    }
    describe("""Either can initiate instance based on a predicate""") {
        fun evenOrOdd(x: Int): Boolean = x % 2 == 0
        it("should be initiate right side when condition is true") {
            val r = Either.cond(evenOrOdd(2), { "Odd" }, { "Even" })
            assertSoftly {
                r.isRight() shouldBe true
                r.isLeft() shouldNotBe true
                r.toOption().orNull() shouldBe "Odd"
            }
        }
        it("should be initiate left side when condition is false") {
            val l = Either.cond(evenOrOdd(3), { "Odd" }, { "Even" })
            assertSoftly {
                l.isLeft() shouldBe true
                l.isRight() shouldNotBe true
                // Use "getOrHandle" when you would like to handle the left value.
                // If you don not handle the a left value you can use "getOrElse" which is supplier function instead.
                l.getOrHandle { "It's $it" } shouldBe "It's Even"
                l.getOrElse { "Default" } shouldBe "Default"
            }
        }
    }
})