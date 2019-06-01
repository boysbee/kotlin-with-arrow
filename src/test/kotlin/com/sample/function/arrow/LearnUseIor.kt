package com.sample.function.arrow

import arrow.core.None
import arrow.data.Ior
import arrow.data.getOrElse
import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.DescribeSpec

class LearnUseIor : DescribeSpec({

    describe("""Let's learn data type "Ior""") {
        it("""should be return "Right" side """) {
            val r = Ior.Right("Ok")
            assertSoftly {
                r.isRight shouldBe true
                r.isLeft shouldNotBe true
                r.isBoth shouldNotBe true
                r.map { "It is $it" }.getOrElse { None } shouldBe "It is Ok"
            }
        }

        it("""should be return "Left" side """) {
            val l = Ior.Left("Failure")
            assertSoftly {
                l.isLeft shouldBe true
                l.isRight shouldNotBe true
                l.isBoth shouldNotBe true
                // Left side can not map a value in container
                l.map { "It is $it" }.getOrElse { None } shouldBe None
            }
        }
    }
})
