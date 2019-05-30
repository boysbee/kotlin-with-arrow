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
            val tryRequestOtpFailure = Try { requestOtpFailure() }
            "request otp but operation this failure" - {
                "So result of request otp is failure" - {
                    "Try.failure should be" {
                        assertSoftly {
                            tryRequestOtpFailure.isFailure() shouldBe true
                            tryRequestOtpFailure.isSuccess() shouldNotBe true
                        }
                    }

                }
            }
            val tryRequestOtpSuccess = Try { requestOtpSuccessfully() }
            "request otp but operation this successfully" - {
                "So result of request otp is success" - {
                    "Try.success should be" {
                        assertSoftly {
                            tryRequestOtpSuccess.isSuccess() shouldBe true
                            tryRequestOtpSuccess.isFailure() shouldNotBe true
                        }
                    }

                }
            }
        }


    }
})