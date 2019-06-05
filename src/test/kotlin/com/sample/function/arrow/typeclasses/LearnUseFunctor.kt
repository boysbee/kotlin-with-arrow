package com.sample.function.arrow.typeclasses

import arrow.core.Option
import arrow.core.Some
import arrow.core.Try
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec

class LearnUseFunctor : FreeSpec({
    /**
     * The Functor typeclass abstracts the ability to map over the computational context of a type constructor
     *
     * fun F<A>.map(f: (A) -> B): F<B>
     */
    """Learn learn Functor""" - {
        """Option.map""" {
            // F is Option
            // A is String
            // B is Int
            val result: Option<Int> = Option<String>("2").map { it.toInt() }
            result shouldBe Some(2)
        }
        """Try.map""" {
            // F is Try
            // A is String
            // B is Int
            val result: Try<Int> = Try<String> { "2" }.map { it.toInt() }
            result shouldBe Try.Success(2)
        }
    }
})