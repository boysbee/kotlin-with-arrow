package com.sample.function.arrow

import arrow.core.None
import arrow.data.Ior
import arrow.data.getOrElse
import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

class LearnUseIor : DescribeSpec({

    describe("""Let's learn data type "Ior""") {
        it("""should be return "Right" side """) {
            val r = Ior.Right("Ok")
            assertSoftly {
                r.isRight shouldBe true
                r.map { "It is $it" }.getOrElse { None } shouldBe "It is Ok"
            }
        }
    }
})
