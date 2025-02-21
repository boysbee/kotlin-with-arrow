package com.sample.function.arrow.datatypes

import arrow.core.*
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe


class LearnUseEither : DescribeSpec({
    describe("""Either, learn to use arrow-kt data type "Either" """) {
        // Let's start with how to initiate an instance of either data type.
        // Right side for successful value
        val r: Either<String, String> = Either.Right("success")
        // Left side for failure value
        val l: Either<String, String> = Either.Left("failure")
        it("should return true if right is right side") {
            r.isRight() shouldBe true
        }
        it("should return false if right is not left side") {
            r.isLeft() shouldNotBe true
        }
        it("should return true if left is left side") {
            l.isLeft() shouldBe true
        }

        it("should return false if left is not right side") {
            l.isRight() shouldNotBe true
        }

    }
    describe("""Either in arrow-kt can initiate instance with arbitrary data types""") {
        it("should initiate with Int") {
            val r: Either<Nothing, Int> = 7.right()
            val l: Either<Int, Int> = (-1).left()
            /*
            left() with Nothing can not compile.
            val lFromNothing: Either<Nothing, Int> = Nothing.left()
            */
            assertSoftly {
                r.isRight() shouldBe true
                r.getOrElse { -1 } shouldBe (7)
                l.isLeft() shouldBe true
                // mapLeft should be return Either.Left
                l.mapLeft { it + 0 } shouldBe Either.Left((-1))
            }
        }
    }
    describe("""Either can initiate instance based on a predicate""") {
        fun evenOrOdd(x: Int): Boolean = x % 2 == 0
        it("should be initiate right side when condition is true") {
            val r = if (evenOrOdd(2)) "Odd".right() else "Even".left()
            assertSoftly {
                r.isRight() shouldBe true
                r.isLeft() shouldNotBe true
                r.toOption().orNull() shouldBe "Odd".right()
            }
        }
        it("should be initiate left side when condition is false") {
            val l = if (evenOrOdd(3)) "Odd".right() else "Even".left()
            assertSoftly {
                l.isLeft() shouldBe true
                l.isRight() shouldNotBe true
                // Use "getOrHandle" when you would like to handle the left value.
                // If you don not handle the a left value you can use "getOrElse" which is supplier function instead.
                l.getOrHandle { "It's $it" } shouldBe "It's Even"
                l.getOrElse { "Default" } shouldBe "Default"
            }
        }
        it("""should return true when we use "when" statement to check """) {
            val r = "Successfully".right()
            val l = "Failure".left()
            val result: (Either<String, String>) -> Boolean = { e ->
                when (e) {
                    is Either.Right -> true
                    is Either.Left -> false
                    else -> false
                }
            }
            assertSoftly {
                result(r) shouldBe true
                result(l) shouldBe false
            }
        }
        it("""should return left side if right side is null value when check with "leftIfNull" """) {
            val r = Either.Right(null)
            r.flatMap { b -> b?.right() ?: "is null value".left() } shouldBe Either.Left("is null value")
        }
        it("""should return left side check null value with "rightIfNotNull" """) {
            val r = null?.right() ?: "is left side".left()
            r shouldBe Either.Left("is left side")
        }
    }
    describe("When we use operation \"flatMap\" with either data type ") {
        it("""should be continue the "Right" side when we use "flatMap" to computation """) {
            val r1 = 1.right()
            val r2 = 2.right()
            val r3 = 3.right()
            val resultOfSum = r1.flatMap { a -> r2.flatMap { b -> r3.flatMap { c -> (a + b + c).right() } } }
            resultOfSum.orNull() shouldBe 6
        }

        it("""should be return "Left" side when we have some "Left" side on a computation""") {
            val r1: Either<Int, Int> = 1.right()
            val r2: Either<Int, Int> = 2.right()
            val r3: Either<Int, Int> = 3.left()
            val resultOfSum = r1.flatMap { a -> r2.flatMap { b -> r3.flatMap { c -> (a + b + c).right() } } }
            assertSoftly {
                resultOfSum shouldBe Either.Left(3)
                resultOfSum.getOrElse { "Not continue be cause is r3 is on Left" } shouldBe "Not continue be cause is r3 is on Left"
            }
        }
    }
    describe("With Monad(\"binding\")  ") {
        it("""should be result of Monad("binding") same like "flatMap" to computation""") {
            val r1: Either<Int, Int> = 1.right()
            val r2: Either<Int, Int> = 2.right()
            val r3: Either<Int, Int> = 3.right()
            // should be fill the specific Type inference of the result Left and Right side because compiler will compile failed with message as below.
            /*
             Type inference failed: Not enough information to infer parameter L in fun <L, A> binding(arg0: suspend MonadContinuation<Kind<ForEither, L>, *>.() -> A): Either<L, A>
Please specify it explicitly.
             */
            val result: Either<Int, Int> = r1.flatMap { a ->
                r2.flatMap { b ->
                    r3.flatMap { c ->
                        (a + b + c).right()
                    }
                }
            }
            assertSoftly {
                result shouldBe Either.Right(6)
                result.orNull() shouldBe 6
            }
        }

        it("""should be result of Monad("binding") same like "flatMap" to computation the "Left" should not continue """) {
            val r1: Either<Int, Int> = 1.right()
            val r2: Either<Int, Int> = 2.right()
            val r3: Either<Int, Int> = 3.right()
            val r4: Either<Int, Int> = (-1).left()

            val result: Either<Int, Int> = r1.flatMap { a ->
                r2.flatMap { b ->
                    r3.flatMap { c ->
                        r4.flatMap { d ->
                            (a + b + c + d).right()
                        }
                    }
                }
            }
            assertSoftly {
                result shouldBe Either.Left(-1)
                result.getOrHandle { l -> l * 99 } shouldBe (-99)
            }
        }
    }

    describe("When either with \"fold\" ") {
        it("should be handle both side with \"fold\"") {
            // operation "fold" will extrac the value from either or provide a default if the value is Left
            val resultTrue = if (true) 10.right() else (-1).left()
            val resultFalse = if (false) 10.right() else (-1).left()
            assertSoftly {
                resultTrue.fold({ it * 99 }, { it * 99 }) shouldBe 990
                resultFalse.fold({ it * 99 }, { it * 99 }) shouldBe -99

            }
        }
    }

})