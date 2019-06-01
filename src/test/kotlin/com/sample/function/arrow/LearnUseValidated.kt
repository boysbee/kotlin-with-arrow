package com.sample.function.arrow

import arrow.data.Validated
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

class LearnUseValidated : DescribeSpec({

    describe("""Let's learn data type "Validated" """) {
        val validResult = Validated.Valid("Success")
        val inValidResult = Validated.Invalid("Failure")
        it("should return true when check Validated.isValid of valid result") {
            validResult.isValid shouldBe true
        }

        it("should return false when check Validated.isInvalid of invalid result") {
            inValidResult.isInvalid shouldBe true
        }

    }

    describe("""Learn how to use with simple function like check login user""") {
        fun validateLogin(u: String): Validated<String, String> {
            if (u.isBlank() or u.isEmpty()) {
                return Validated.Invalid("Username is empty")
            }
            return Validated.Valid("Username is valid")
        }
        it("""should valid when username is not blank""") {
            validateLogin("test").isValid shouldBe true
        }
    }

    describe("""Validate should be transform to Either""") {
        it("""should be Either.Right when Validate.Valid""") {
            val validToEither = Validated.Valid("Success").toEither()
            validToEither.isRight() shouldBe true
        }

        it("""should be Either.Left when Validate.Invalid""") {
            val validToEither = Validated.Invalid("failure").toEither()
            validToEither.isLeft() shouldBe true
        }
    }

})