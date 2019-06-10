package com.sample.function.arrow.typeclasses

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.extensions.either.applicative.just
import arrow.core.extensions.option.applicative.applicative
import arrow.data.ListK
import arrow.data.k
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

    describe("Option.ap") {
        // Apply a function inside the type constructor’s context
        it("""should be Some(2) when apply function { n + 1 } after Some(1)""") {
            Option.applicative().run { Some(1).ap(Some({ n: Int -> n + 1 })) } shouldBe Some(2)
        }
        it("""should be None when apply function { n: Int -> n + 1 } from None""") {
            Option.applicative().run { None.ap(Some({ n: Int -> n + 1 })) } shouldBe None
        }

    }


})