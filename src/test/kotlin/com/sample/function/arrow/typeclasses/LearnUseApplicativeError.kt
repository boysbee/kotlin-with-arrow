package com.sample.function.arrow.typeclasses

import arrow.Kind
import arrow.core.*
import arrow.core.extensions.`try`.applicativeError.applicativeError
import arrow.core.extensions.either.applicative.ap
import arrow.core.extensions.either.applicative.just
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.extensions.either.foldable.get
import arrow.core.extensions.either.monad.flatten
import arrow.core.extensions.option.applicative.applicative
import arrow.core.extensions.option.applicative.map2
import arrow.data.ListK
import arrow.data.k
import com.sample.function.arrow.datatypes.BadRequestException
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import kotlin.Function1

class LearnUseApplicativeError : DescribeSpec({

    /**
     * A.raiseError, A constructor function. It lifts an exception into the computational context of a type constructor.
     *
     */

    describe("Try.raiseError") {
        it("""should be return Try.Failure when Try<A>.raiseError(throwable)""") {
            Try.applicativeError().raiseError<String>(BadRequestException()).isFailure() shouldBe true
        }
    }

    describe("Either.raiseError") {
        it("""should be return Either.Left when Either<A,B>.raiseError(B)""") {
            Either.applicativeError<String>().raiseError<String>("Failure") shouldBe Left("Failure")
        }
    }

})