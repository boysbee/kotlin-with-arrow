package com.sample.function.arrow.typeclasses

import arrow.core.extensions.monoid
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec

class LearnUseMonoid : FreeSpec({
    /**
     * Monoid extends the Semigroup type class, adding an empty method to semigroup’s combine.
     * The empty method must return a value that when combined with any other instance of that type returns the other instance, i.e.
     *
     * (combine(x, empty) == combine(empty, x) == x)
     */

    """Let's learn use Monoid""" - {

        """from instance of String""" - {
            val result = String.monoid().run {
                empty()
            }
            """it should be return empty String"""  {
                result shouldBe ""
            }
        }
    }
})