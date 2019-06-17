package com.sample.function.arrow.typeclasses

import arrow.core.Either
import arrow.core.Try
import arrow.core.extensions.`try`.applicativeError.raiseError
import arrow.core.extensions.`try`.monadError.ensure
import arrow.core.extensions.either.applicativeError.raiseError
import com.sample.function.arrow.datatypes.BadRequestException
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

class LearnUseMonadError : DescribeSpec({
    // From document
    // MonadError is the typeclass used to explicitly represent errors during sequential execution.
    // It is parametrized to an error type E, which means the datatype has at least a “success” and a “failure” version.
    // These errors can come in the form of Throwable, Exception, or any other type hierarchy of the user’s choice.

    // MonadError extends from ApplicativeError.
    // MonadError inherits all the combinators available in ApplicativeError and Monad.
    // It also adds one of its own.

    // So it can use "raiseError".
    /**
     * A.raiseError, Inherited from ApplicativeError. A constructor function. It lifts an exception into the computational context of a type constructor.
     *
     */
    describe("Try.raiseError") {
        it("""should be true return Error.raiseError is failure""") {
            val tryRequest: Try<String> = BadRequestException().raiseError()
            tryRequest.isFailure() shouldBe true
        }
    }

    describe("Either.raiseError") {
        it("""should be true return Error.raiseError is left""") {
            var either: Either<BadRequestException, String> =
                BadRequestException().raiseError<BadRequestException, String>()
            either.isLeft() shouldBe true
        }
    }

    /**
     * ensure,Tests a predicate against the object, and if it fails it executes a function to create an error.
     */
    describe("Try.ensure") {
        it("""should be isSuccess when Try.ensure with "ok" is "ok" """) {
            val tryRequest: Try<String> = Try { "ok" }.ensure({ BadRequestException() }, { it === "ok" })
            tryRequest.isSuccess() shouldBe true
        }
        it("""should be isFailure when Try.ensure with "not ok" is "ok" """) {
            val tryRequest: Try<String> = Try { "not ok" }.ensure({ BadRequestException() }, { it === "ok" })
            tryRequest.isFailure() shouldBe true
        }
    }

})