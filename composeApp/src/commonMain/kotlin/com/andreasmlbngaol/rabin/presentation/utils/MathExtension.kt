package com.andreasmlbngaol.rabin.presentation.utils

/***
 * Modular exponentiation for Long
 * @param exp exponent
 * @param mod modulus
 * @return result
 * @author andreasmlbngaol
 *
 ***/
fun Long.modPow(exp: Long, mod: Long): Long {
    var result = 1L
    var b = this.mod(mod)
    var e = exp
    while (e > 0) {
        if (e.mod(2L) == 1L) result = (result * b).mod(mod)
        b = (b * b).mod(mod)
        e /= 2
    }
    return result
}

/***
 * Extended Euclidean Algorithm for Long
 * @param a first number
 * @param b second number
 * @return Pair of x and y
 * @author andreasmlbngaol
 */
fun extendedGCD(a: Long, b: Long): Pair<Long, Long> {
    if (b == 0L) return Pair(1L, 0L)
    val (x1, y1) = extendedGCD(b, a.mod(b))
    val x = y1
    val y = x1 - (a / b) * y1
    return Pair(x, y)
}

/***
 * Modular Inverse for Long
 * @param mod modulus
 * @return inverse
 * @author andreasmlbngaol
 */
fun Long.modInverse(mod: Long): Long {
    val (x, _) = extendedGCD(this, mod)
    return ((x.mod(mod)) + mod).mod(mod)
}