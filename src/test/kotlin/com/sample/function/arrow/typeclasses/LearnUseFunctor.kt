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

        /**
         * From source code
         * Lifts a function `A -> B` to the [F] structure returning a polymorphic function
         * that can be applied over all [F] values in the shape of Kind<F, A>
         *
         * `A -> B -> Kind<F, A> -> Kind<F, B>`
         */
        """Functor.lift method""" - {
            """Option.Functor.lift""" - {
                """F of A to F of B""" - {
                    // F is ForOption
                    // A is Int
                    // B is Int
                    // `A -> B -> Kind<F, A> -> Kind<F, B>`
                    """should be lift from Some(1) to Some(2)""" {
                        val optionFunctor = Option.functor()
                        val lifted = optionFunctor.lift<Int, Int>({ n: Int -> n + 1 })
                        lifted(Option(1)) shouldBe Some(2)
                    }

                    """should be lift from Option(1) to Some("2")""" {
                        val optionFunctor = Option.functor()
                        val lifted = optionFunctor.lift<Int, String>({ n: Int -> (n + 1).toString() })
                        lifted(Option(1)) shouldBe Some("2")
                    }
                }
            }

            """Try.Functor.lift""" - {
                """F of A to F of B""" - {
                    // F is ForTry
                    // A is Int
                    // B is Int
                    // `A -> B -> Kind<F, A> -> Kind<F, B>`
                    """should be lift from Try.Success(1) to Try.Success(2)""" {
                        val tryFunctor = Try.functor()
                        val lifted = tryFunctor.lift<Int, Int>({ n: Int -> n + 1 })
                        lifted(Try { 1 }) shouldBe Try.Success(2)
                    }
                    // F is ForTry
                    // A is Int
                    // B is String
                    """should be lift from Try.Success(1) to Try.Success("2") """ {
                        val tryFunctor = Try.functor()
                        val lifted = tryFunctor.lift<Int, String>({ n: Int -> (n + 1).toString() })
                        lifted(Try { 1 }) shouldBe Try.Success("2")
                    }
                }
            }

            """Either.Functor.lift""" - {
                """F of A to F of B""" - {
                    // F is ForTry
                    // A is Int
                    // B is Int
                    // `A -> B -> Kind<F, A> -> Kind<F, B>`
                    """should be lift from Either.Right(1) to Either.Right(2)""" {
                        val eitherFunctor = Either.functor<Int>()
                        val lifted = eitherFunctor.lift<Int, Int>({ n: Int -> n + 1 })
                        lifted(Either.right(1)) shouldBe Either.right(2)
                    }
                    // F is ForTry
                    // A is Int
                    // B is String
                    """should be lift from Either.Right(1) to Either.Right("2") """ {
                        val eitherFunctor = Either.functor<Int>()
                        val lifted = eitherFunctor.lift<Int, String>({ n: Int -> (n + 1).toString() })
                        lifted(Either.right(1)) shouldBe Either.right("2")
                    }
                }
            }
        }

    }
})