package com.sample.function.arrow.typeclasses

import arrow.core.*
import arrow.core.extensions.`try`.functor.functor
import arrow.core.extensions.either.functor.functor
import arrow.core.extensions.option.functor.functor

import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec

class LearnUseFunctor : FreeSpec({
    /**
     * The Functor typeclass abstracts the ability to map over the computational context of a type constructor
     *
     * fun F<A>.map(f: (A) -> B): F<B>
     */
    """Learn learn Functor""" - {
        """Functor.map method""" - {
            """Option.map""" - {
                """should operate on success value""" {
                    // F is Option
                    // A is String
                    // B is Int
                    // Option<String>.map(f: (String) -> Int): Option<Int>
                    val result: Option<Int> = Option<String>("2").map { it.toInt() }
                    result shouldBe Some(2)
                }
                """Option.Functor instance""" - {
                    """should be transform inner content of A to B""" {
                        Option.functor().run { Option("2").map { it.toInt() } } shouldBe Some(2)
                    }

                }
            }
            """Try.map""" - {
                """should operate in the success value""" {
                    // F is Try
                    // A is String
                    // B is Int
                    // Try<String>.map(f: (String) -> Int): Try<Int>
                    val result: Try<Int> = Try<String> { "2" }.map { it.toInt() }
                    result shouldBe Try.Success(2)
                }
                """Try.Functor instance""" - {
                    """should be transform inner content of Success[A] to Success[B]""" {
                        Try.functor().run { Try { "2" }.map { it.toInt() } } shouldBe Try.Success(2)
                    }

                }

            }
            """Either.map""" - {
                """should operate in the right side value""" {
                    // F is Either
                    // A is String
                    // B is String
                    // C is Int
                    // Either<String,String>.map(f: (String) -> Int): Either<String,Int>
                    val result: Either<String, Int> = Either.cond(true, { "2" }, { "0" }).map { it.toInt() }
                    result shouldBe Either.Right(2)
                }
                """Either.Functor instance""" - {
                    """should be transform inner content of Right[A] to Right[B]""" {
                        Either.functor<String>().run { Either.right("2") }.map { it.toInt() } shouldBe Either.Right(
                            2
                        )
                    }

                }

            }
        }

        """Functor.lift method""" - {
            """Option.lift""" - {
                """F of A to F of B""" - {
                    /**
                     * From source code
                     * Lifts a function `A -> B` to the [F] structure returning a polymorphic function
                     * that can be applied over all [F] values in the shape of Kind<F, A>
                     *
                     * `A -> B -> Kind<F, A> -> Kind<F, B>`
                     */
                    // F is ForOption
                    // A is Int
                    // B is Int
                    // `A -> B -> Kind<F, A> -> Kind<F, B>`
                    """should be lift Some(Int) to Some(Int)""" {
                        val optionFunctor = Option.functor()
                        val lifted = optionFunctor.lift<Int, Int>({ n: Int -> n + 1 })
                        lifted(Option(1)) shouldBe Some(2)
                    }

                    """should be lift F of A to F of B""" {
                        val optionFunctor = Option.functor()
                        val lifted = optionFunctor.lift<Int, String>({ n: Int -> (n + 1).toString() })
                        lifted(Option(1)) shouldBe Some("2")
                    }

                }
            }
        }

    }
})