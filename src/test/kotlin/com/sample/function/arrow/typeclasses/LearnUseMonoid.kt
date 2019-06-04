package com.sample.function.arrow.typeclasses

import arrow.core.Option
import arrow.core.Some
import arrow.core.extensions.monoid
import arrow.core.extensions.option.monoid.monoid
import arrow.data.ListK
import arrow.data.extensions.listk.monoid.monoid
import arrow.data.k
import io.kotlintest.assertSoftly
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.FreeSpec

class LearnUseMonoid : FreeSpec({
    /**
     * Monoid extends the Semigroup type class, adding an empty method to semigroup’s combine.
     * The empty method must return a value that when combined with any other instance of that type returns the other instance, i.e.
     *
     * (combine(x, empty) == combine(empty, x) == x)
     */

    """Let's learn use Monoid""" - {

        """String monoid""" - {
            """string monoid with empty string""" - {
                val result = String.monoid().run {
                    empty()
                }
                """it should be return empty String"""  {
                    result shouldBe ""
                }
            }

            """combine string of a and b""" - {
                val result = String.monoid().run {
                    "a".combine("b")
                }
                """it should be return ab"""  {
                    result shouldBe "ab"
                }
            }

            """combine string of a and empty""" - {
                val result = String.monoid().run {
                    "a".combine(empty())
                }
                """it should be return a"""  {
                    result shouldBe "a"
                }
            }
        }


        """List monoid""" - {
            """list with String as inner type""" - {
                """with empty""" - {
                    "it should return empty list" {
                        val result = ListK.monoid<String>().run {
                            empty()
                        }
                        result.isEmpty() shouldBe true
                    }
                }
                """combine empty with list("a") """ - {
                    """it should return list("a")""" {
                        val result = ListK.monoid<String>().run {
                            listOf("a").k().combine(empty())
                        }
                        assertSoftly {
                            result shouldBe listOf("a")
                            result.isEmpty() shouldNotBe true
                        }
                    }
                }

                """combine empty with some of list("a"), list("b") and list("c")""" - {
                    """it should return list("a","b","c") """ {
                        val result = ListK.monoid<String>().run {
                            listOf("a") + empty() + listOf("b") + listOf("c")
                        }
                        assertSoftly {
                            result shouldBe listOf("a", "b", "c")
                            result.isEmpty() shouldNotBe true
                        }
                    }


                }
                """List with map as innner type""" - {
                    """combine list(map) and other map""" - {
                        val result = ListK.monoid<Map<String, Int>>().run {
                            mapOf("a" to 1, "b" to 2).k() + mapOf("c" to 3).k()
                        }
                        """It should be Map{"a" = 1, "b"=2 , "c"= 3} """ {
                            result shouldBe mapOf("a" to 1, "b" to 2, "c" to 3)
                        }
                    }
                }


            }


        }

        """Option monoid""" - {
            """with empty and some value""" - {
                "it should be return Some(9)" {
                    Option.monoid(Int.monoid()).run {
                        listOf(
                            empty(),
                            Some(2),
                            Some(3),
                            Some(4),
                            empty()
                        ).combineAll()
                    } shouldBe Some(9)
                }
            }
            """with many some value then combine all together""" - {
                "it should be return Some(15)" {
                    Option.monoid(Int.monoid()).run {
                        listOf(
                            Some(1),
                            Some(2),
                            Some(3),
                            Some(4),
                            Some(5)
                        ).combineAll()
                    } shouldBe Some(15)
                }
            }
        }
    }

})