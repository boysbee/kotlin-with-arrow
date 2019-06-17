package com.sample.function.arrow.typeclasses

import arrow.core.extensions.`try`.applicativeError.raiseError
import com.sample.function.arrow.datatypes.BadRequestException
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

class LearnUseMonadError : DescribeSpec({

    /**
     * A.raiseError, Inherited from ApplicativeError. A constructor function. It lifts an exception into the computational context of a type constructor.
     *
     */

    describe("Try.raiseError") {
        it("""should be true return Error.raiseError""") {
            val tryRequest = BadRequestException().raiseError<String>()
            tryRequest.isFailure() shouldBe true
        }
    }

})