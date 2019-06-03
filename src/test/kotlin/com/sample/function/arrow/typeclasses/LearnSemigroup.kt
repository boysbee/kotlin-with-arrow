package com.sample.function.arrow.typeclasses

import arrow.core.extensions.semigroup
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec

class LearnSemigroup : FreeSpec({
    /**
     * From document say "A semigroup for some given type `A` has a single operation (which we will call combine),
     * which takes two values of type `A`, and returns a value of type `A`.
     * This operation must be guaranteed to be associative.
     *
     * ((a combine b) combine c)
     * must be the same as
     * (a combine (b combine c))
     */

    """Lets start to understand "Semigroup"""" - {
        """Begin with with Int.semigroup""" - {
            "semigroup of Int 1 combine 2" - {
                "it should be 3" {
                    // 1 combine 2
                    Int.semigroup().run { 1.combine(2) } shouldBe 3
                }
            }
            """or use + instead combine operation""" - {
                "So 1 + 2" - {
                    "it should be 3 too" {
                        // 1 combine 2
                        Int.semigroup().run { 1 + 2 } shouldBe 3
                    }
                }

            }
        }


    }
})