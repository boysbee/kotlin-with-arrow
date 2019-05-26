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
}

fun main(args: Array<String>) {
    val demo = BasicIKnew()

    println(demo.convToString(1))

    val intToMessage = demo.convIntToMessage(
        1,
        fn = { i -> "function received function as parameter and convert to string message $i" })

    println(intToMessage)
}
