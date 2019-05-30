package com.sample.function.arrow

import arrow.core.Try
import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.FreeSpec

class LearnUseTry : FreeSpec({
    """Try, learn to use arrow-kt data type "Try" """ - {
        // Let's start with how to initiate an instance of Try data type.
        """Let's start with initiate instance of "Try" """ - {
            val tryRequestOtp = Try { requestOtp() }
            "request otp but operation this failure" - {
                "So result of request otp is failure" - {
                    "Try.failure should be" {
                        assertSoftly {
                            tryRequestOtp.isFailure() shouldBe true
                            tryRequestOtp.isSuccess() shouldNotBe true
                        }
                    }

                }
            }
        }


    }
})