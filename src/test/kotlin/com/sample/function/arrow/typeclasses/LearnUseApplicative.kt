package com.sample.function.arrow.typeclasses

import arrow.core.*
import arrow.core.extensions.either.applicative.ap
import arrow.core.extensions.either.applicative.just
import arrow.core.extensions.option.applicative.applicative
import arrow.core.extensions.option.applicative.map2
import arrow.data.ListK
import arrow.data.k
import com.sample.function.arrow.datatypes.BadRequestException
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

class LearnUseApplicative : DescribeSpec({
    /**
     * The Applicative typeclass abstracts the ability to lift values
     * and apply functions over the computational context of a type constructor
     */

    // A constructor function, also known as pure in other languages.
    // It lifts a value into the computational context of a type constructor.
    describe("Option.just") {
        it("""should result is Some(1) when Option.just(1)""") {
            val a: Option<Int> = Option.just(1)
            a shouldBe Some(1)
        }
        it("""should be result is ListK(1) when ListK.just(1)""") {
            val a: ListK<Int> = ListK.just(1)
            a shouldBe listOf(1).k()
        }
        it("""should be result is Right("success") """) {
            val a: Either<Int, String> = "success".just()
            a shouldBe Either.right("success")
        }
        /**
         * Can not test try.just because complier can not choose among the following candidates without completing type inference:
        @JvmName public fun <A> String.just(): Try<String> defined in arrow.core.extensions.`try`.applicative
        @JvmName public fun <L, A> String.just(): Either<???, String> defined in arrow.core.extensions.either.applicative

         * it("""should be result is Try.Success("success") """) {
        val a: Try<String> = "success".just()
        a shouldBe Try.Success("success")
        }
         */

    }

    // Apply a function inside the type constructor’s context
    describe("Option.ap") {
        it("""should be Some(2) when apply function { n + 1 } after Some(1)""") {
            Option.applicative().run { Some(1).ap(Some({ n: Int -> n + 1 })) } shouldBe Some(2)
        }
        it("""should be None when apply function { n: Int -> n + 1 } from None""") {
            Option.applicative().run { None.ap(Some({ n: Int -> n + 1 })) } shouldBe None
        }

    }

    describe("Either.ap") {

        it("""should be Either.Right(2) when apply function { n + 1 } after Either.Right(1)""") {
            Either.run { Either.Right(1).ap(Either.Right({ n: Int -> n + 1 })) } shouldBe Either.Right(2)
        }


    }

    describe("Try.ap") {

        it("""should be 2 when apply function { n + 1 } after Try.Success(1)""") {
            (Try.run { Try.Success(1).ap(Try.Success { n: Int -> n + 1 }) }).orNull() shouldBe 2
        }

        it("""should be Try.Failure when apply Try.Success with function { n + 1 } after Try.Failure(Error)""") {
            (Try.run { Try.Failure(BadRequestException()).ap(Try.Success { n: Int -> n + 1 }) }).isFailure() shouldBe true
        }


    }

    describe("Option.map2") {
        it("""should be return Some(1x) when apply function to map tuple with Some(1) and Some("x") """) {
            Option.applicative().run { Some(1).map2(Some("x")) { z: Tuple2<Int, String> -> "${z.a}${z.b}" } } shouldBe Some(
                "1x"
            )
        }
        it("""should be return None when apply function to map tuple with Some(1) and None """) {
            Option.applicative().run { Some(1).map2(None) { z: Tuple2<Int, String> -> "${z.a}${z.b}" } } shouldBe None
        }
    }

    describe("Option.map2Eval") {
        it("""should be return Some(1x) when apply function to map tuple with Some(1) and Some("x") then invoke with .value()""") {
            Option.applicative().run {
                Some(1).map2Eval(
                    Eval.later { Some("x") }
                ) { z: Tuple2<Int, String> -> "${z.a}${z.b}" }

            }.value() shouldBe Some(
                "1x"
            )
        }

        it("""should be return None when apply function to map tuple with Some(1) and None then invoke with .value()""") {
            Option.applicative().run {
                Some(1).map2Eval(
                    Eval.later { None }
                ) { z: Tuple2<Int, String> -> "${z.a}${z.b}" }

            }.value() shouldBe None
        }
    }

})