package com.sample.function.arrow.typeclasses

import arrow.core.Option
import arrow.core.Some
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
    describe("just") {
        it("""should result is Some(1) when Option.just(1)""") {
            val a: Option<Int> = Option.just(1)
            a shouldBe Some(1)
        }
        it("""should be result is ListK(1) when ListK.just(1)""") {
            val a: ListK<Int> = ListK.just(1)
            a shouldBe listOf(1).k()
        }
    }
})