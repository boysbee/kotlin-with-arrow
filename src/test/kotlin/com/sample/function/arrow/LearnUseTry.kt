package com.sample.function.arrow

import arrow.core.Try
import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.DescribeSpec

class LearnUseTry : DescribeSpec({
    /**
     * From "arrow-kt" document
     * "Try, which represents a computation that can result in an A result (as long as the computation is successful) or in an exception if something has gone wrong."
     */
    describe("""Try, learn to use arrow-kt data type "Try" """) {
        // Let's start with how to initiate an instance of Try data type.
        val tryRequestOtp = Try { requestOtp() }

        assertSoftly {
            tryRequestOtp.isFailure() shouldBe true
            tryRequestOtp.isSuccess() shouldNotBe true
        }
    }


})

open class CustomException : Exception()

class BadRequestException : CustomException()

fun requestOtp() {
    validateRequest()

}

fun validateRequest() {
    throw BadRequestException()
}
