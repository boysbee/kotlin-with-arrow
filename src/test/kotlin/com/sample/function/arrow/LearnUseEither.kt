package com.sample.function.arrow

import arrow.core.Either
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
})