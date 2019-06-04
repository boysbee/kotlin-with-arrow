package com.sample.function.arrow.typeclasses

import arrow.core.*
import arrow.core.extensions.combine
import arrow.core.extensions.either.monoid.monoid
import arrow.core.extensions.monoid
import arrow.core.extensions.option.monoid.monoid
import arrow.data.ListK
import arrow.data.extensions.list.foldable.foldMap
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
                        assertSoftly {
                            result.isEmpty() shouldBe true
                            result shouldBe ListK.empty()
                            result shouldBe emptyList<String>().k()
                            result.toList() shouldBe emptyList()
                        }
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
        """Monoid in Either""" - {
            """should be return right of empty of inner type""" - {
                Either.monoid(String.monoid(), String.monoid()).run {
                    empty()
                } shouldBe Either.Right("")
            }
            """should be return combine of 2 Right""" {
                assertSoftly {
                    Either.right("a").combine(
                        String.monoid(),
                        String.monoid(),
                        Either.right("b")
                    ) shouldBe Either.Right("ab")

                    Either.monoid(String.monoid(), String.monoid()).run {
                        Either.Right("a").combine(Either.right("b"))
                    } shouldBe Either.Right("ab")
                }

            }

            """should be return 1 value of Right side when combine of Right and empty""" {
                assertSoftly {
                    Either.monoid(String.monoid(), String.monoid()).run {
                        Either.Right("a").combine(empty())
                    } shouldBe Either.Right("a")
                }

            }

            """should be return 1 value of Left side when combine of Left and empty""" {
                assertSoftly {
                    Either.monoid(String.monoid(), String.monoid()).run {
                        Either.Left("failure").combine(empty())
                    } shouldBe Either.Left("failure")
                }

            }

            """should return Left side when combine with Right and Left""" - {
                assertSoftly {
                    Either.monoid(String.monoid(), String.monoid()).run {
                        Either.Left("failure").combine(Either.Right("a"))
                    } shouldBe Either.Left("failure")
                }
            }

            """should return Left when combine with Left and Left""" - {
                assertSoftly {
                    Either.monoid(String.monoid(), String.monoid()).run {
                        Either.Left("failure").combine(Either.Left("failure too"))
                    } shouldBe Either.Left("failurefailure too")
                }
            }
        }

        """Monoid with "foldMap" """ - {
            """Int.monoid""" - {
                """it should be 15""" {
                    listOf(1, 2, 3, 4, 5).k().foldMap(Int.monoid(), ::identity) shouldBe 15
                }
            }
            """String.monoid""" - {
                """it should be 12345""" {
                    listOf(1, 2, 3, 4, 5).k().foldMap(String.monoid(), { it.toString() }) shouldBe "12345"
                }
            }

        }
    }

})
