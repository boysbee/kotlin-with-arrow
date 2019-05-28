package com.sample.function.cyclops

import cyclops.control.Option
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec


class CompareCyclopsOption : DescribeSpec({
    describe("Initial Option with just a value") {
        val a = Option.some("a")
        it("should just value a") {
            a.orElse("b") shouldBe "a"
        }
    }
})