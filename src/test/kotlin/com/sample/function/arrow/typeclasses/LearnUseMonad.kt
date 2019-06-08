package com.sample.function.arrow.typeclasses

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.Try
import arrow.core.extensions.option.monad.flatten
import com.sample.function.arrow.datatypes.BadRequestException
import io.kotlintest.matchers.types.shouldBeSameInstanceAs
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
            val result = a.flatMap { f1 -> b.flatMap { f2 -> Try.Success(f1 + f2) } }
            result shouldBe Try.Success(3)
        }

        it("""should result is Try.Failure(Error) when computation value of Try.Success(1) and Try.Failure(Error)""") {
            val a: Try<Int> = Try { 1 }
            val b: Try<Int> = Try.Failure(BadRequestException())
            val result = a.flatMap { f1 -> b.flatMap { f2 -> Try.Success(f1 + f2) } }
            result.isFailure() shouldBe true
        }
    }

    describe("Option flatten should combine nested container into one") {
        it("should be return Some(2) when combine Some(Some(2))") {
            val a = Option(Option(2))
            a.flatten() shouldBe Some(2)
        }

        it("should be return Some(2) when combine Some(None)") {
            val a = Option(None)
            a.flatten() shouldBe None
        }
    }


})