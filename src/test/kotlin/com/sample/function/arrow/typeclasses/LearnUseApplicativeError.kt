package com.sample.function.arrow.typeclasses

import arrow.core.*
import arrow.core.extensions.`try`.applicativeError.applicativeError
import arrow.core.extensions.`try`.applicativeError.handleError
import arrow.core.extensions.`try`.applicativeError.handleErrorWith
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.extensions.option.applicativeError.applicativeError
import com.sample.function.arrow.datatypes.BadRequestException
import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.DescribeSpec

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

    describe("Option.raiseError") {
        it("""should be return None when Option.raiseError()""") {
            Option.applicativeError().run { Unit.raiseError<String>() } shouldBe None
        }
        it("""should be return None when raiseError with Unit """) {
            Option.applicativeError().raiseError<String>(Unit) shouldBe None
        }
    }


    /**
     * handleErrorWith, This method requires a function that creates a new datatype from an error, (E) -> Kind<F, A>.
     * This function is used as a catch + recover clause for the current instance, allowing it to return a new computation after a failure.
     */

    describe("Try.handleErrorWith") {
        it("""should be return Try.Success when Try<A>.Failure(throwable).handleErrorWith to recover error case""") {
            val result = Try.Failure(BadRequestException()).handleErrorWith { Try { "Changed to success" } }
            assertSoftly {
                result.isFailure() shouldNotBe true
                result shouldBe Success("Changed to success")
            }
        }
    }

    // found runtime error, java.lang.NoSuchMethodError: arrow.core.EitherKt.handleErrorWith(Larrow/Kind;Lkotlin/jvm/functions/Function1;)Larrow/core/Either;
    // will back to find more information to fix this issue agian.
//    describe("Either.handleErrorWith") {
//        it("""should be return Either.Right when Either.Left<E>.handleErrorWith to recover error case""") {
//
//            val result:Either<Throwable, String> = Either.Left(BadRequestException()).handleErrorWith { Either.Right("Changed to success") }
//            assertSoftly {
//                result.isLeft() shouldNotBe true
//            }
//        }
//    }

    /**
     * handleError, Similar to handleErrorWith, except the function can return any regular value. This value will be wrapped and used as a return.
     */

    describe("Try.handleError") {
        it("""should be return Try.Success when Try<A>.Failure(throwable).handleError to recover error case""") {
            val result = Try.Failure(BadRequestException()).handleError { "After handleError then success" }
            assertSoftly {
                result.isFailure() shouldNotBe true
                result shouldBe Success("After handleError then success")
            }
        }

        it("""should be return Try<A>.Success(A) when Try<A>.Success(A) handleError""") {
            val result = Try.Success("Ok").handleError { "This is Ok." }
            assertSoftly {
                result.isFailure() shouldNotBe true
                result shouldBe Success("Ok")
                result shouldNotBe Success("This is Ok.")
            }
        }
    }
})