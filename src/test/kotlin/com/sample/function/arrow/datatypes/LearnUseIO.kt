package com.sample.function.arrow.datatypes

import arrow.effects.IO
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

})