package com.sample.function.arrow.effect

import arrow.core.Right
import arrow.effects.IO
import com.sample.function.arrow.datatypes.BadRequestException
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

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
    describe("""IO.map transform IO<A> to IO<B>""") {
        it("should return 1 when transform IO(\"1\") to IO(1) with map") {
            IO.just("1").map { it.toInt() }.unsafeRunSync() shouldBe 1
        }
    }
})