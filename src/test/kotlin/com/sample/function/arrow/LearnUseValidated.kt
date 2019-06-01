package com.sample.function.arrow

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.Try
import arrow.data.Validated
import io.kotlintest.assertSoftly
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
            val invalidToEither = Validated.Invalid("failure").toEither()
            invalidToEither.isLeft() shouldBe true
        }
    }

    describe("""Validated can initiate instance from other data type""") {
        it("""should initiate from Either""") {
            val valid = Validated.fromEither(Either.right("Success"))
            val invalid = Validated.fromEither(Either.left(BadRequestException()))
            assertSoftly {
                valid.isValid shouldBe true
                invalid.isInvalid shouldBe true
            }
        }

        it("""should initiate from Try""") {
            val valid = Validated.fromTry(Try.Success("Success"))
            val invalid = Validated.fromTry(Try.Failure(BadRequestException()))
            assertSoftly {
                valid.isValid shouldBe true
                invalid.isInvalid shouldBe true
            }
        }

        it("""should initiate from Option""") {
            val valid = Validated.fromOption(Option.just("Success"), { "Is null" })
            // If 1st parameter on method "fromOption" is Some(null) it should return validate
            val validFromOptionJustNullValue = Validated.fromOption(Option.just(null), { "Is null" })
            // If 1st parameter on method "fromOption" is None ( because Option.fromNullable(null) return "None" )
            // it should return Validated.Invalid
            val validFromOptionFromNullable = Validated.fromOption(Option.fromNullable(null), { "Is null" })
            // If 1st parameter on method "fromOption" is "None" then return Validated.Invalid
            val invalid = Validated.fromOption(None, { "Is null" })
            assertSoftly {
                valid.isValid shouldBe true
                validFromOptionJustNullValue.isValid shouldBe true
                validFromOptionFromNullable.isInvalid shouldBe true
                invalid.isInvalid shouldBe true
            }
        }
    }

})