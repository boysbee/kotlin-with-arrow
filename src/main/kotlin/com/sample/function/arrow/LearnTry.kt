package com.sample.function.arrow


open class CustomException : Exception()

class BadRequestException : CustomException()

fun requestOtp() {
    validateRequest()

}

fun validateRequest() {
    throw BadRequestException()
}
