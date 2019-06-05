package com.sample.function.arrow.typeclasses

import arrow.core.*
import arrow.core.extensions.`try`.semigroup.semigroup
import arrow.core.extensions.function0.semigroup.semigroup
import arrow.core.extensions.function1.semigroup.semigroup
import arrow.core.extensions.monoid
import arrow.core.extensions.option.semigroup.semigroup
import arrow.core.extensions.semigroup
import arrow.data.ListK
import arrow.data.extensions.list.semigroupK.combineK
import arrow.data.extensions.listk.semigroup.semigroup
import arrow.data.k
import com.sample.function.arrow.datatypes.BadRequestException
import io.kotlintest.assertSoftly
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
            "Int.semigroup of 1 combine 2" - {
                "it should be 3" {
                    // 1 combine 2
                    Int.semigroup().run { 1.combine(2) } shouldBe 3
                }
            }
            """or use `+` instead `combine` operation""" - {
                "So 1 + 2" - {
                    "it should be 3 too" {
                        // 1 combine 2
                        Int.semigroup().run { 1 + 2 } shouldBe 3
                    }
                }

            }
        }
        """Let's do with Option""" - {
            """combine 2 Option(Int)""" - {
                val result = Option.semigroup(Int.semigroup()).run { Option(1).combine(Option(2)) }
                """it should be Option(3)""" {
                    result shouldBe Option(3)
                }
            }
        }

        """Try with ListK""" - {
            "use combine 2 list" - {
                val result = ListK.semigroup<Int>().run {
                    listOf(1, 2, 3).k().combine(listOf(4, 5, 6).k())
                }
                "it should be List[1,2,3,4,5,6]" {
                    result shouldBe listOf(1, 2, 3, 4, 5, 6)
                }
            }

            "use `+` 2 list" - {
                val result = ListK.semigroup<Int>().run {
                    listOf(1, 2, 3).k() + listOf(4, 5, 6).k()
                }
                "it should be List[1,2,3,4,5,6]" {
                    result shouldBe listOf(1, 2, 3, 4, 5, 6)
                }
            }

            "use `combineK` 2 list" - {
                val result = ListK.semigroup<Int>().run {
                    listOf(1, 2, 3).combineK(listOf(4, 5, 6).k())
                }
                "it should be List[1,2,3,4,5,6]" {
                    result shouldBe listOf(1, 2, 3, 4, 5, 6)
                }
            }

        }

        """Semigroup with Function0""" - {
            """Function0 { 1 } combine with Function0 {1}""" - {
                val fn = Function0.semigroup(Int.semigroup()).run {
                    Function0 { 1 }.combine(Function0 { 1 })
                }
                "When we invoke function" - {
                    "it should be 2" {
                        // Like option when we invoke then a result of 2 function will `combine` a value
                        fn.invoke() shouldBe 2
                    }
                }
            }
            """Let try with function that return string""" - {
                val fnString = Function0.semigroup(String.semigroup()).run {
                    Function0 { "It should be" }.combine(Function0 { " " }).combine(Function0 { "Ok." })
                }
                "When we invoke function" - {
                    "it should be \"It shoudl be Ok\"" {

                        fnString.invoke() shouldBe "It should be Ok."
                    }
                }
            }
            "Semigroup of Function0<A> is Function0<Semigroup<A>>" - {

                val fn1 = Function0.semigroup(Int.semigroup()).run {
                    Function0 { 1 }.combine(Function0 { 1 })
                }
                val fn2 = Int.semigroup().run { Function0 { 1.combine(1) } }
                fn1.invoke() shouldBe fn2.invoke()
            }
        }

        """Semigroup with Function1""" - {
            """Function1 { 1 } combine with Function1 {1}""" - {
                val fn = Function1.semigroup<Int, Int>(Int.semigroup()).run {
                    Function1<Int, Int> { 1 }.combine(Function1 { 1 })
                }
                "When we invoke function" - {
                    "it should be 2" {
                        // Like option when we invoke then a result of 2 function will `combine` a value
                        fn.invoke(1) shouldBe 2
                    }
                }

            }
            "Semigroup of Function1<A> is Function1<Semigroup<A>>" - {
                val fn1 = Function1.semigroup<Int, Int>(Int.semigroup()).run {
                    Function1<Int, Int> { it }.combine(Function1 { it })
                }
                val fn2 = Function1<Int, Int> { Int.monoid().run { it.combine(it) } }
                fn1.invoke(1) shouldBe fn2.invoke(1)
            }

        }
        """Semigroup with Try""" - {
            """When combine 2 Try.Success""" - {
                val t1 = Try { "Success" }
                val t2 = Try { "Twice" }
                val resultTry = Try.semigroup(String.semigroup()).run { t1.combine(t2) }
                """It should return Try.Success""" {
                    assertSoftly {

                        resultTry.isSuccess() shouldBe true
                        resultTry.getOrElse { None } shouldBe "SuccessTwice"
                    }
                }
            }
            """When combine with Try.Success and Try.Failure""" - {
                val t1 = Try { "Success" }
                val t2 = Try { throw BadRequestException() }
                val resultTry = Try.semigroup(String.semigroup()).run { t1.combine(t2) }
                """It should return Try.Failure""" {
                    assertSoftly {
                        resultTry.isFailure() shouldBe true
                        resultTry.getOrElse { None } shouldBe None
                    }
                }
            }
        }


    }
})

