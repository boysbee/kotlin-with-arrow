package com.sample.function.arrow

import arrow.core.*
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

        """How do we solve a failure when computation has been failure """ - {
            val defaultValue = "The default value"
            val expect = "The default value"
            val tryWithBadRequest = Try {
                throw BadRequestException()
            }
            "when use \"getOrDefault\"" - {
                "should be return a default value" {
                    tryWithBadRequest.getOrDefault { defaultValue } shouldBe expect
                    // getOrDefault received consume function that ignore throwable detail
                }
            }

            """Or you can use "recover" to solve failure """ - {
                "when we use \"recover\"" - {
                    "should be return a \"Success\" contain a new value" {
                        tryWithBadRequest.recover { ex: Throwable -> "a new value" } shouldBe Success("a new value")
                        // recover received supplier function that not ignore throwable detail
                        // you can rid throwable if you don want to use throwable.
                    }
                }
            }

            """Or you can use "getOrElse" """ - {
                "when we use \"getOrElse\"" - {
                    "should be return the new value like recover " {
                        tryWithBadRequest.getOrElse { ex: Throwable -> "a new value" } shouldBe "a new value"
                        // getOrElse like recover received supplier function that not ignore throwable detail
                        // you can rid throwable if you don want to use throwable.
                    }
                }
            }
        }

        """Try can transform to Either""" - {
            "when we need either type" - {
                "use \"toEither\" " - {
                    "when Try with computation success" - {
                        val r = Try {
                            "success"
                        }
                        "should return \"Right\" side of Either" {
                            r.toEither() shouldBe Either.right("success")
                        }
                    }
                    "when Try with computation is failure" - {
                        val badTry = Try {
                            throw BadRequestException()
                        }
                        "should return \"Left\" side of Either" {
                            badTry.toEither().isLeft() shouldBe true
                        }
                    }

                }
            }
        }

        """Try with "flatMap" computation """ - {
            "when we try computation is success" - {
                val r = Try {
                    "success"
                }
                "Then use flatMap" - {
                    "when try is success it should return \"SUCCESS\"" {
                        r.flatMap { f: String -> Try.Success(f.toUpperCase()) }.getOrElse { "not success" } shouldBe "SUCCESS"
                    }
                }

            }

            "when we try computation is failure" - {
                val r = Try {
                    throw BadRequestException()
                }
                "Then use flatMap" - {
                    "when try is failure it should return \"NOT SUCCESS\"" {
                        r.flatMap { f: String -> Try.Success(f.toUpperCase()) }.getOrElse { "not success".toUpperCase() } shouldBe "NOT SUCCESS"
                    }
                }

            }
        }
    }
})