package com.sample.function.arrow.datatypes

import java.util.concurrent.ThreadLocalRandom


open class CustomException : Exception()

class BadRequestException : CustomException()

fun requestOtpFailure(): String? {
    validateRequestFailure()
    return null
}

fun requestOtpSuccessfully(): String {
    validateRequest()
    return generateOTP()
}

fun validateRequestFailure() {
    throw BadRequestException()
}

fun validateRequest(): Boolean {
    return true
}

fun generateOTP(): String {
    return ThreadLocalRandom.current().nextInt(0, 9999).toString()
}
