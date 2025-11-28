package com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.decrypt

import androidx.lifecycle.ViewModel
import com.andreasmlbngaol.rabin.data.RabinBasicValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RabinBasicDecryptViewModel : ViewModel() {
    private val _state = MutableStateFlow(RabinBasicDecryptState())
    val state = _state.asStateFlow()

    fun onPChange(text: String) {
        val newState = _state.value.copy(pAsString = text)
        _state.value = validateAndUpdate(newState)
    }

    fun onQChange(text: String) {
        val newState = _state.value.copy(qAsString = text)
        _state.value = validateAndUpdate(newState)
    }

    fun onCiphertextChange(text: String) {
        val newState = _state.value.copy(ciphertextAsString = text)
        _state.value = validateAndUpdate(newState)
    }

    private fun validateAndUpdate(state: RabinBasicDecryptState): RabinBasicDecryptState {
        val errors = mutableMapOf<String, String>()

        if (state.pAsString.isNotEmpty()) {
            val (_, error) = RabinBasicValidator.validateP(state.pAsString)
            error?.let { errors["p"] = it }
        }

        if (state.qAsString.isNotEmpty()) {
            val (_, error) = RabinBasicValidator.validateQ(state.qAsString)
            error?.let { errors["q"] = it }
        }

        if (state.ciphertextAsString.isNotEmpty()) {
            val (_, error) = RabinBasicValidator.validateCiphertext(
                state.ciphertextAsString,
                state.p,
                state.q
            )
            error?.let { errors["ciphertext"] = it }
        }

        return state.copy(errors = errors)
    }

    fun decrypt() {
        val currentState = _state.value
        if (!currentState.isAllValid) return

        val p = currentState.p!!
        val q = currentState.q!!
        val c = currentState.ciphertext!!
        val n = currentState.n!!

        val steps = mutableListOf<String>()

        steps.add("Input:\n   p = $p\n   q = $q\n   c = $c")

        // Step 1: Hitung r = c^((p+1)/4) mod p
        val exponent1 = (p + 1) / 4
        val r = modPow(c.toLong(), exponent1.toLong(), p.toLong()).toInt()
        steps.add("1. Hitung r = c^((p+1)/4) mod p\n   r = $c^${(exponent1.toString())} mod $p\n   r = $r")

        // Step 2: Hitung s = c^((q+1)/4) mod q
        val exponent2 = (q + 1) / 4
        val s = modPow(c.toLong(), exponent2.toLong(), q.toLong()).toInt()
        steps.add("2. Hitung s = c^((q+1)/4) mod q\n   s = $c^${(exponent2.toString())} mod $q\n   s = $s")

        // Step 3: Extended Euclidean Algorithm untuk mencari x, y
        val (x, y) = extendedGCD(p, q)
        steps.add("3. Persamaan Diophantine: p · x + q · y = 1\n   $p · x + $q · y = 1\n   x = $x, y = $y")

        // Step 4: Hitung m1 - m4
        val m1Raw = x.toLong() * p * s + y.toLong() * q * r
        val m1 = (m1Raw % n).let { if (it < 0) (it + n) else it }.toInt()
        val m2 = (n - m1)

        val m3Raw = x.toLong() * p * s - y.toLong() * q * r
        val m3 = (m3Raw % n).let { if (it < 0) (it + n) else it }.toInt()
        val m4 = (n - m3)

        steps.add("4. Kandidat Pesan (M1-M4):\n   M1 = (x · p · s + y · q · r) mod n = ($x · $p · $s + $y · $q · $r) mod $n = $m1\n   M2 = n - M1 = $m2\n   M3 = (x · p · s - y · q · r) mod n = ($x · $p · $s - $y · $q · $r) mod $n = $m3\n   M4 = n - M3 = $m4")

        val candidates = listOf(m1.toLong(), m2.toLong(), m3.toLong(), m4.toLong())

        val result = RabinBasicDecryptionResult(
            ciphertext = c,
            p = p,
            q = q,
            n = n,
            candidates = candidates,
            steps = steps
        )

        _state.value = currentState.copy(result = result)
    }

    // Helper function untuk convert ke superscript

    fun resetResult() {
        _state.value = _state.value.copy(result = null)
    }

    private fun modPow(base: Long, exp: Long, mod: Long): Long {
        var result = 1L
        var b = base % mod
        var e = exp
        while (e > 0) {
            if (e % 2 == 1L) result = (result * b) % mod
            b = (b * b) % mod
            e /= 2
        }
        return result
    }

    private fun extendedGCD(a: Int, b: Int): Pair<Long, Long> {
        if (b == 0) return Pair(1L, 0L)
        val (x1, y1) = extendedGCD(b, a % b)
        val x = y1
        val y = x1 - (a / b).toLong() * y1
        return Pair(x, y)
    }
}

fun superscript(text: String): String {
    val superscriptMap = mapOf(
        '0' to '⁰',
        '1' to '¹',
        '2' to '²',
        '3' to '³',
        '4' to '⁴',
        '5' to '⁵',
        '6' to '⁶',
        '7' to '⁷',
        '8' to '⁸',
        '9' to '⁹',
        '+' to '⁺',
        '-' to '⁻',
        '=' to '⁼',
        '(' to '⁽',
        ')' to '⁾',
    )
    return text.map { superscriptMap[it] ?: it }.joinToString("")
}
