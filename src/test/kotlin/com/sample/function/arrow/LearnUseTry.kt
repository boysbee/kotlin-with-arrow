package com.sample.function.arrow

import arrow.core.Try
import arrow.core.getOrDefault
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
        """You can use "getOrDefault" when we need a default value when we computation is return failure """ - {
            val defaultValue = "The default value"
            val expect = "The default value"
            val r = Try {
                throw BadRequestException()
            }
            "should be return a default value" {
                r.getOrDefault { defaultValue } shouldBe expect
            }
        }


    }
})