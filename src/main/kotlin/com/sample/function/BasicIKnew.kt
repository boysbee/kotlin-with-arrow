package com.sample.function

class BasicIKnew {
    fun convToString(n: Int): String {
        val fn: (Int) -> String = { i -> i.toString() }
        return fn.invoke(n)
    }
}

fun main(args: Array<String>) {
    val demo = BasicIKnew()
    println(demo.convToString(1))
}
