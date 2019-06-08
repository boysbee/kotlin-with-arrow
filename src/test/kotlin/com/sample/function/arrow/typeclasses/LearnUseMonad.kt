package com.sample.function.arrow.typeclasses

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.Try
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

class LearnUseMonad : DescribeSpec({
    /**
     * Monad is a typeclass that abstracts over sequential execution of code.
     */
    describe("Option flatMap") {
        it("""should result is Some(3) when computation value of Some(a) and Some(b)""") {
            val a: Option<Int> = Option.just(1)
            val b: Option<Int> = Option.just(2)
            val result = a.flatMap { f1 -> b.flatMap { f2 -> Some(f1 + f2) } }
            result shouldBe Some(3)
        }

        it("""should be None when flatMap None""") {
            None.flatMap { f1 -> Some("Not none") } shouldBe None
        }

        it("""should result is None when computation value of a is None and Some(b)""") {
            val a: Option<Int> = None
            val b: Option<Int> = Option.just(2)
            val result = a.flatMap { f1 -> b.flatMap { f2 -> Some(f1 + f2) } }
            result shouldBe None
        }
    }

    describe("Try flatMap") {
        it("""should result is Try.Success(3) when computation value of Try.Success(1) and Try.Success(2)""") {
            val a: Try<Int> = Try { 1 }
            val b: Try<Int> = Try { 2 }
            val result = a.flatMap { f1 -> b.flatMap { f2 -> Try.Success( f1 + f2) } }
            result shouldBe Try.Success(3)
        }
    }


})