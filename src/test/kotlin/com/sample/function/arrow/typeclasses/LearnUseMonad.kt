package com.sample.function.arrow.typeclasses

import arrow.core.*
import arrow.core.extensions.either.monad.flatten
import arrow.core.extensions.option.monad.flatten
import arrow.core.extensions.option.monad.followedBy
import arrow.core.extensions.option.monad.mproduct
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
    // flatten() , Combines two nested elements into one Kind<F, A>
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

    describe("Try flatten should combine nested container into one") {
        it("should be return Try.Success(1) when combine Try.Success(Try.Success(1))") {
            val a = Try.Success(Try.Success(1))
            a.flatten() shouldBe Try.Success(1)
        }

        it("should be return Try.Failure(Error) when combine Try.Success(Try.Failure(Error))") {
            val a = Try.Success(Try.Failure(BadRequestException()))
            a.flatten().isFailure() shouldBe true
        }
    }

    describe("Either flatten should combine nested container into one") {
        it("should be return Either.Right(1) when combine Either.Right(Either.Right(1))") {
            val a = Either.Right(Either.Right(1))
            a.flatten() shouldBe Either.Right(1)
        }

        it("should be return Either.Left(1) when combine Either.Right(Either.Left(1))") {
            val a = Either.Right(Either.Left(1))
            a.flatten() shouldBe Either.Left(1)
        }

    }

    // mpproduct ,Like flatMap, but it combines the two sequential elements in a Tuple2.
    describe("""Option.mpproduct""") {
        it("""should return Some(Tuple2("a","b")""") {
            Some("a").mproduct { Some("b") } shouldBe Some(Tuple2("a", "b"))
        }

        it("""should return Some(Tuple2("1",10)""") {
            Some("1").mproduct { Some(it.toInt() * 10) } shouldBe Some(Tuple2("1", 10))
        }

        it("""should return None when mpproduct Some by None""") {
            Some("1").mproduct { None } shouldBe None
        }

        it("""should return None when mpproduct None by None""") {
            None.mproduct { None } shouldBe None
        }
    }
    // followedBy , Executes sequentially two elements that are independent from one another.
    describe("""Option.followedBy""") {
        it("""should return Some("b") when Some("a") followedBy Some("b") """) {
            Some("a").followedBy(Some("b")) shouldBe Some("b")
        }

        it("""should return None when Some("a") followedBy None""") {
            Some("a").followedBy(None) shouldBe None
        }

        it("""should return None when None followedBy None""") {
            None.followedBy(None) shouldBe None
        }
    }

})