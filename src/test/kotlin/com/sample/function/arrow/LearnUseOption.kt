package com.sample.function.arrow

import arrow.core.Option
import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe


object LearnUseOption : Spek({

    describe("Learn Option here") {
        val a = Option.just("a")
        it("should just value a") {
            assertEquals("a", a.orNull())
        }
    }
})