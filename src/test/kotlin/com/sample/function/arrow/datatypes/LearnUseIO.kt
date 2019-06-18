package com.sample.function.arrow.datatypes

import arrow.effects.IO
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

class LearnUseIO : DescribeSpec({
    //IO is the most common data type used to represent side-effects in functional languages.
    // This means IO is the data type of choice when interacting with the external environment: databases, network, operative systems, files…


    // Construct

    // just,Used to wrap single values. It creates anIOthat returns an existing value.
    describe("IO.just") {
        it("""just return "ok"""") {
            val content = IO.just("ok")
            content.unsafeRunSync() shouldBe "ok"
        }
    }

})