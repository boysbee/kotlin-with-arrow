package com.sample.function.cyclops

import cyclops.control.Option
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec


class CompareCyclopsOption : DescribeSpec({
    describe("Initial Option method some") {
        // Option in cyclops is initial with method "some"
        val a = Option.some("a")
        it("should just value a") {
            a.orElse("b") shouldBe "a"
        }
        // Cyclops has not function "isDefined" but has function "isPresent" to check value is present.
        it("should be present") {
            a.isPresent shouldBe true
        }
    }
})