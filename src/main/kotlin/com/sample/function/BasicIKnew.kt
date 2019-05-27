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
