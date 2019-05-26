package com.sample.function

class BasicIKnew {
    fun convToString(n: Int): String {
        val fn: (Int) -> String = { i -> i.toString() }
        return fn.invoke(n)
    }

    // received function as parameter
    // (Int) -> String :: Function received integer value and return to string
    fun convIntToMessage(i: Int, fn: (Int) -> String): String {
        return fn.invoke(i)
    }

    //
    fun sampleInnerFunInsideMainFun(i: Int): String {
        fun innerFun(i: Int, fn: (Int) -> String): String {
            return fn(i)
        }

        val fn = { i: Int -> "Called inner fun -> $i" }
        return innerFun(i, fn);
    }


    // Test sample ```High Order Function```
    // Function as parameter and Function return Function
    fun fn1(fn: (Int) -> String): (Int) -> String {
        return { m: Int -> fn(m) }
    }

    fun fn2(m: Int, fn: (Int) -> String): (Int) -> String {
        return { i: Int -> fn(m + i) }
    }

    fun fn3(): (Int) -> (String) -> Int {
        return { i: Int -> { j: String -> j.toInt() + i } }
    }

    fun fn4(fn: (Int) -> String): (Int) -> (String) -> Int {
        return { i: Int -> { j: String -> j.toInt() + fn(i).toInt() } }
    }


}

fun main() {
    val demo = BasicIKnew()

    println(demo.convToString(1))

    val intToMessage = demo.convIntToMessage(
        1,
        fn = { i -> "function received function as parameter and convert to string message $i" })

    println(intToMessage)

    val innerFun = demo.sampleInnerFunInsideMainFun(50)
    println(innerFun)


    val result1 = demo.fn1(fn = { i -> i.toString() })
    val result2 = demo.fn2(3, fn = { i -> i.toString() })
    val result3 = demo.fn3()
    val result4 = demo.fn4(fn = { i -> i.toString() })
    println(result1.invoke(3))
    println(result2.invoke(3))
    println(result3.invoke(1).invoke("2"))
    println(result4.invoke(1).invoke("4"))

    // Test compose function
    val addOne: (Int) -> Int = { i -> i + 1 }
    val addTwo: (Int) -> Int = { j -> j + 2 }

    fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
        return { x -> f(g(x)) }
    }

    val composed = compose(addOne, addTwo)
    val resultComposed = composed.invoke(1)
    println(resultComposed)

}
