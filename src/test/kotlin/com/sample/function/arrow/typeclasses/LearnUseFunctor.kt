package com.sample.function.arrow.typeclasses

import arrow.core.*
import arrow.core.extensions.option.semiring.empty
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
            // Option<String>.map(f: (String) -> Int): Option<Int>
            val result: Option<Int> = Option<String>("2").map { it.toInt() }
            result shouldBe Some(2)
        }
        """Try.map""" {
            // F is Try
            // A is String
            // B is Int
            // Try<String>.map(f: (String) -> Int): Try<Int>
            val result: Try<Int> = Try<String> { "2" }.map { it.toInt() }
            result shouldBe Try.Success(2)
        }
        """Either.map""" {
            // F is Either
            // A is String
            // B is String
            // C is Int
            // Either<String,String>.map(f: (String) -> Int): Either<String,Int>
            val result: Either<String, Int> = Either.cond(true, { "2" }, { "0" }).map { it.toInt() }
            result shouldBe Either.Right(2)
        }
    }
})