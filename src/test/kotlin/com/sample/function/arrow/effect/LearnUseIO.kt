package com.sample.function.arrow.effect

import arrow.core.Left
import arrow.core.Right
import arrow.core.Some
import arrow.core.getOrElse
import arrow.effects.IO
import arrow.effects.typeclasses.milliseconds
import com.sample.function.arrow.datatypes.BadRequestException
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import java.util.*

class LearnUseIO : DescribeSpec({
    //IO is the most common data type used to represent side-effects in functional languages.
    // This means IO is the data type of choice when interacting with the external environment: databases, network, operative systems, files…


    // Construct

    // just,Used to wrap single values. It creates anIOthat returns an existing value.
    describe("""IO.just creates an IO that returns an existing value""") {
        it("""should be return "oK'"""") {
            val content = IO.just("ok")
            content.unsafeRunSync() shouldBe "ok"
        }
    }
    // raiseError, Used to notify of errors during execution. It creates an IO that returns an existing exception.
    describe("""IO.raiseError creates an IO that returns an existing exception""") {
        it("""should be return exception when raiseError """) {
            val contentError = IO.raiseError<String>(BadRequestException())
            contentError.attempt().unsafeRunSync().isLeft() shouldBe true
        }
    }

    describe("""invoke IO,  Creates an IO that invokes one lambda function when run""") {
        it("""should return"oK" when invoke with unsafeRunSync""") {
            val contentIo = IO { "ok" }
            contentIo.unsafeRunSync() shouldBe IO.invoke { "ok" }.unsafeRunSync()
        }
    }


    describe("""IO.defer (IO.suspend in document) to defer the evaluation of an existing IO""") {
        it("""should return defer "ok" when warp with IO.just """) {
            val ioJust = IO.just("ok")
            val ioSuspend = IO.defer { ioJust }
            ioSuspend.unsafeRunSync() shouldBe "ok"
        }
    }


    describe("""IO.map transform IO<A> to IO<B>""") {
        it("should return 1 when transform IO(\"1\") to IO(1) with map") {
            IO.just("1").map { it.toInt() }.unsafeRunSync() shouldBe 1
        }
    }

    describe("""IO.attempt transform to Either<E,A>""") {
        it("""should return Right side when result is success""") {
            IO { "ok" }.attempt().unsafeRunSync() shouldBe Right("ok")
        }

        it("""should return Left side when result is failure""") {
            IO { throw BadRequestException() }.attempt().unsafeRunSync().isLeft() shouldBe true
        }

    }

    describe("""IO.runAsync,It runs the current IO asynchronously, calling the callback parameter on completion and returning its result""") {
        it("""should return "it's ok" when callback function run success""") {
            // use stack instead callback ; :P
            val stack = Stack<String>()
            IO { "ok" }.runAsync { result ->
                result.fold(
                    { IO { stack.push("error"); return@IO } },
                    { IO { stack.push("it's ok");return@IO } }
                )
            }.unsafeRunSync()
            stack.pop() shouldBe "it's ok"
        }

        it("""should return "it's error" when callback function run failure""") {
            val stack = Stack<String>()
            IO { throw BadRequestException() }.runAsync { result ->
                result.fold(
                    { IO { stack.push("it's error"); return@IO } },
                    { IO { stack.push("it's ok");return@IO } }
                )
            }.unsafeRunSync()
            stack.pop() shouldBe "it's error"
        }
    }

    describe("""IO.unsafeRunAsync,It runs the current IO asynchronously, calling the callback parameter on completion""") {
        it("""return it's ok when run success""") {
            val stack = Stack<String>()
            IO { "ok" }.unsafeRunAsync { result ->
                result.fold(
                    { stack.push("it's error") },
                    { stack.push("it's ok") }
                )
            }
            stack.pop() shouldBe "it's ok"
        }
    }
    describe("""IO.unsafeRunSync,it runs IO synchronously and returning its result blocking the current thread""") {
        // To avoid crashing use attempt() first.
        it("""should return Right("ok") when use attempt()""") {
            IO { "ok" }.attempt().unsafeRunSync() shouldBe Right("ok")

        }
        // Not avoid crashing,not use attempt()
        it("""should return "ok" when not use attempt()""") {
            IO { "ok" }.unsafeRunSync() shouldBe "ok"

        }

        it("""should return left(error) when IO throw error """) {
            IO { throw BadRequestException() }.attempt().unsafeRunSync().isLeft() shouldBe true

        }
    }
    describe("""IO.unsafeRunTimed, it runs IO synchronously and returns an Option<A> blocking the current thread""") {
        it("""should return Some(Right(ok)""") {
            IO { "ok" }
                .attempt()
                .unsafeRunTimed(3.milliseconds) shouldBe Some(Right("ok"))
        }

        it("""should return Some(Left(BadRequestException)""") {
            val result = IO { throw BadRequestException() }
                .attempt()
                .unsafeRunTimed(3.milliseconds)
            result.getOrElse { Left("something happened") }.isLeft() shouldBe true
        }
    }
})