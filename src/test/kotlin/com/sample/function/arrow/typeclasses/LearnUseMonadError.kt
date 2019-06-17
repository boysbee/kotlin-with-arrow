package com.sample.function.arrow.typeclasses

import arrow.core.Either
import arrow.core.Try
import arrow.core.extensions.`try`.applicativeError.raiseError
import arrow.core.extensions.either.applicativeError.raiseError
import com.sample.function.arrow.datatypes.BadRequestException
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

class LearnUseMonadError : DescribeSpec({

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
//            val eitherRequest: Either<BadRequestException, String> = BadRequestException().raiseError<Either<BadRequestException,String>>() as Either<BadRequestException, String>
            var either:Either<BadRequestException,String> = BadRequestException().raiseError<BadRequestException, String>()
            either.isLeft() shouldBe true
        }
    }

})