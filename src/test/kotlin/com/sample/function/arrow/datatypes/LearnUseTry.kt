package com.sample.function.arrow.datatypes

import arrow.core.*
import arrow.core.extensions.`try`.monad.binding
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
            """Or you can use "fold" when you want to handle both cases success and failure""" - {
                """when use "fold" when Try.success""" - {
                    val r: Try<String> = Try {
                        "Success"
                    }
                    val result = r.fold(
                        { { "this failure" } },
                        { { "this success" } })
                    "should return a new value for success" {
                        // use "fold" will return function that you have to invoke function before it will be evaluate to actual value
                        result.invoke() shouldBe "this success"
                    }
                }

                """when use "fold" when Try.failure""" - {
                    val rFailure: Try<String> = Try {
                        throw BadRequestException()
                    }
                    val result = rFailure.fold(
                        { { "this failure" } },
                        { { "this success" } })
                    "should return a new value for failure" {
                        result.invoke() shouldBe "this failure"
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
                    val result = r.flatMap { f: String -> Try.Success(f.toUpperCase()) }.getOrElse { "not success" }
                    "when try is success it should return \"SUCCESS\"" {
                        result shouldBe "SUCCESS"
                    }
                }

            }

            "when we try computation is failure" - {
                val r = Try {
                    throw BadRequestException()
                }
                "Then use flatMap" - {
                    val result = r.flatMap { f: String -> Try.Success(f.toUpperCase()) }
                        .getOrElse { "not success".toUpperCase() }
                    "when try is failure it should return \"NOT SUCCESS\"" {
                        result shouldBe "NOT SUCCESS"
                    }
                }

            }
        }

        """Try with "Monad.binding" computation """ - {
            "when we try computation is success" - {
                val r1 = Try {
                    "once"
                }
                val r2 = Try {
                    "twice"
                }
                "Then use binding" - {
                    val result = binding {
                        val (a) = r1
                        val (b) = r2
                        ("success $a $b").toUpperCase()
                    }.getOrElse { "NOT SUCCESS" }
                    "when try is success it should return \"SUCCESS ONCE TWICE\"" {
                        result shouldBe "SUCCESS ONCE TWICE"
                    }
                }

            }

            "when we try computation is failure" - {
                val rFailure = Try {
                    throw BadRequestException()
                }
                val rSuccess = Try {
                    throw BadRequestException()
                }

                "Then use binding" - {
                    val result = binding {
                        val (a) = rFailure
                        val (b) = rSuccess
                        ("success $a $b").toUpperCase()
                    }.getOrElse { "NOT SUCCESS" }
                    "when try is failure it should return \"NOT SUCCESS\"" {
                        result shouldBe "NOT SUCCESS"
                    }
                }

            }
        }
    }
})