package com.sample.function

import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.Assertions.assertEquals

object LearnFunctionalTest : DescribeSpec({
    describe("Learn basic function in kotlin in basic demo") {
        val demo = BasicIKnew()
        describe("function convert Integer to String") {
            it("should return a message") {
                val intToMessage = demo.convIntToMessage(
                    1,
                    fn = { i -> "function received function as parameter and convert to string message $i" })

                assertEquals("function received function as parameter and convert to string message 1", intToMessage)
            }
        }
        describe("Local function") {
            it("should return that call from inside main function") {
                val innerFunResult = demo.sampleInnerFunInsideMainFun(50)
                assertEquals("Called inner fun -> 50", innerFunResult)
            }
        }

        describe("High Order Function") {
            describe("Send function as parameter and return function") {
                it("should return \"1\" when invoke function with \"1\" as string") {
                    val fn1 = demo.fn1(fn = { i -> i.toString() })
                    assertEquals("1", fn1.invoke(1))
                }
                it("should return \"4\" when invoke function with \"1\" as string") {
                    val fn2 = demo.fn2(3, fn = { i -> i.toString() })
                    assertEquals("4", fn2.invoke(1))
                }
                it("should return 3 when invoke function with 1 and invoke nested function with \"2\" as string") {
                    val fn3 = demo.fn3()
                    assertEquals(3, fn3.invoke(1).invoke("2"))
                }
                it("should return 5 when invoke function with 1 and invoke nested funtion with \"4\" as string") {
                    val fn4 = demo.fn4(fn = { i -> i.toString() })
                    assertEquals(5, fn4.invoke(1).invoke("4"))
                }
            }
        }

        describe("Compose function") {
            fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
                return { x -> f(g(x)) }
            }
            it("should compose function `addOne` and `addTwo` function") {
                val addOne: (Int) -> Int = { i -> i + 1 }
                val addTwo: (Int) -> Int = { j -> j + 2 }
                val composed = compose(addOne, addTwo)
                val resultComposed = composed.invoke(1)
                assertEquals(4, resultComposed)
            }
        }
    }

})
