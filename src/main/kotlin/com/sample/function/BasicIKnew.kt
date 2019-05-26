package com.sample.function

class BesicIKnew {
    fun convToString(n: Int): String {
        val fn: (Int) -> String = { i -> i.toString() }
        return fn.invoke(n)
    }
}

fun main(args: Array<String>) {
    val demo = BesicIKnew()
    println(demo.convToString(1))
}
