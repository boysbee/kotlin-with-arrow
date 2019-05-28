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

    describe("Initial Option method of") {
        // This initial function like method "some"
        val ofA = Option.of("a")
        it("should just some value a") {
            ofA.orElse("b") shouldBe "a"
        }
        it("should be present") {
            ofA.isPresent shouldBe true
        }
    }

    describe("""You can initial null with method "of" with null value""") {

        val ofNull: Option<String> = Option.of(null)
        it("""should be null even use method "orElse" or "orElseGet" with supplier function """) {
            ofNull.orElseGet { "b" } shouldBe null
        }

        it("""should be present!!!""") {
            ofNull.isPresent shouldBe true
        }
    }


})