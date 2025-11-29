package com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.decrypt

import androidx.lifecycle.ViewModel
import com.andreasmlbngaol.rabin.domain.service.RabinValidator
import com.andreasmlbngaol.rabin.domain.model.rabin_basic.RabinBasicDecryptResult
import com.andreasmlbngaol.rabin.presentation.utils.extendedGCD
import com.andreasmlbngaol.rabin.presentation.utils.modPow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RabinBasicDecryptViewModel(
    private val validator: RabinValidator
) : ViewModel() {
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
            val (_, error) = validator.validateP(state.pAsString)
            error?.let { errors["p"] = it }
        }

        if (state.qAsString.isNotEmpty()) {
            val (_, error) = validator.validateQ(state.qAsString)
            error?.let { errors["q"] = it }
        }

        if (state.ciphertextAsString.isNotEmpty()) {
            val (_, error) = validator.validateBasicCipher(
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

        steps.add(
            "Input:\n" +
                    "   p = $p\n" +
                    "   q = $q\n" +
                    "   c = $c"
        )

        val exponent1 = (p + 1) / 4
        val r = c.modPow(exponent1, p)
        steps.add(
            "1. Hitung r = c^((p+1)/4) mod p\n" +
                    "   r = $c^${(exponent1.toString())} mod $p\n" +
                    "   r = $r"
        )

        val exponent2 = (q + 1) / 4
        val s = c.modPow(exponent2, q)
        steps.add(
            "2. Hitung s = c^((q+1)/4) mod q\n" +
                    "   s = $c^${(exponent2.toString())} mod $q\n" +
                    "   s = $s"
        )

        val (x, y) = extendedGCD(p, q)
        steps.add(
            "3. Persamaan Diophantine: p · x + q · y = 1\n" +
                    "   $p · x + $q · y = 1\n" +
                    "   x = $x, y = $y"
        )

        val m1Raw = x * p * s + y * q * r
        val m1 = (m1Raw % n).let { if (it < 0) (it + n) else it }
        val m2 = (n - m1)

        val m3Raw = x * p * s - y * q * r
        val m3 = (m3Raw % n).let { if (it < 0) (it + n) else it }
        val m4 = (n - m3)

        steps.add(
            "4. Kandidat Pesan (M1-M4):\n" +
                    "   M1 = (x · p · s + y · q · r) mod n = ($x · $p · $s + $y · $q · $r) mod $n = $m1\n" +
                    "   M2 = n - M1 = $m2\n" +
                    "   M3 = (x · p · s - y · q · r) mod n = ($x · $p · $s - $y · $q · $r) mod $n = $m3\n" +
                    "   M4 = n - M3 = $m4"
        )

        val candidates = listOf(m1, m2, m3, m4)

        val result = RabinBasicDecryptResult(
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
