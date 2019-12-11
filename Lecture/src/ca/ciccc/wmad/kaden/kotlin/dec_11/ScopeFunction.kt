package ca.ciccc.wmad.kaden.kotlin.dec_11

import kotlin.random.Random

// Scope Functions in Kotlin
// - let
// - run (o)
// - with
// - apply (o)
// - also

// =========================================
// Function     Obj ref     Returns
// =========================================
// let           it         lambda result
// run          this        lambda result
// run           -          lambda result
// with         this        lambda result
// apply        this        context obj
// also          it         context obj
// =========================================

data class Person(var name: String = "", var age: Int = 0, var city: String = "")

val rick = Person("Rick").apply {
    // context: 'this'
    age = 28
    city = "Vancouver"
}

fun getRandomInt() = Random.nextInt(100).also {
    // context: 'it'
    println("Random num is $it")
}

fun main() {
    println(rick)
    val r = getRandomInt()
    println(r)
    "Vancouver".run {
        // context: 'this'
        println("Hello, $this")
    }

    val ages = mutableListOf<Int>()
    ages
        .also {
            println("Populating the age list!")
            // returns the context object
        }
        .apply {
            add(28)
            add(29)
            add(30)
            add(15)
            add(17)
            // returns the context object
        }
        .also { println("I will sort the age list.") }
        .sort()

    val numbers = mutableListOf("one", "two", "three")
    val countE = numbers.run {
        add("four")
        add("five")
        add("six")
        count { it.endsWith("e") }
        // returns lambda result
    }
    println("Count: $countE")

    val nums = mutableListOf("one", "two", "three")
    with(nums) {
        println("FirstItem: ${first()}, LastItem: ${last()}")
        // returns lambda result
    }

    // "let" is often used for executing a nullable code block
    val str: String? = "Vancouver"
    val length = str?.let {
        println("let() is called on $it")
        it.length
        // returns lambda result
    }
    println(length)

}