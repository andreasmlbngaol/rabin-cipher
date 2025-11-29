package com.andreasmlbngaol.rabin.presentation.screen.rabin_p.decrypt

import androidx.lifecycle.ViewModel
import com.andreasmlbngaol.rabin.domain.model.rabin_p.RabinPDecryptResult
import com.andreasmlbngaol.rabin.domain.service.RabinValidator
import com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.decrypt.superscript
import com.andreasmlbngaol.rabin.presentation.utils.modInverse
import com.andreasmlbngaol.rabin.presentation.utils.modPow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RabinPDecryptViewModel(
    private val validator: RabinValidator
) : ViewModel() {
    private val _state = MutableStateFlow(RabinPDecryptState())
    val state = _state.asStateFlow()

    fun onPChange(text: String) {
        val newState = _state.value.copy(pAsString = text, result = null)
        _state.value = validateAndUpdate(newState)
    }

    fun onCiphertextChange(text: String) {
        val newState = _state.value.copy(ciphertextAsString = text, result = null)
        _state.value = validateAndUpdate(newState)
    }

    private fun validateAndUpdate(state: RabinPDecryptState): RabinPDecryptState {
        val errors = mutableMapOf<String, String>()

        if (state.pAsString.isNotEmpty()) {
            val (_, error) = validator.validateP(state.pAsString)
            error?.let { errors["p"] = it }
        }

        if (state.ciphertextAsString.isNotEmpty()) {
            val (_, error) = validator.validatePCipher(state.ciphertextAsString, state.p)
            error?.let { errors["ciphertext"] = it }
        }

        return state.copy(errors = errors)
    }

    fun decrypt() {
        val currentState = _state.value
        if (!currentState.isAllValid) return

        val p = currentState.p!!
        val c = currentState.ciphertext!!
        val pSquared = currentState.pSquared!!

        val steps = mutableListOf<String>()

        steps.add("Input:\n   p = $p\n   n = p${superscript("2")} = $pSquared\n   c = $c")

        // Step 1: Hitung w = c mod p
        val w = (c % p)
        steps.add("1. Hitung w = c mod p\n   w = $c mod $p\n   w = $w")

        // Step 2: Hitung mp = w^((p+1)/4) mod p
        val exponent = (p + 1) / 4
        val mp = w.modPow(exponent, p)
        steps.add("2. Hitung mp = w^((p+1)/4) mod p\n   mp = $w^$exponent mod $p\n   mp = $mp")

        // Step 3: Hitung i = (c - mp^2) / p
        val mpSquared = mp * mp
        val numerator = c - mpSquared
        val i = numerator / p
        steps.add("3. Hitung i = (c - mp${superscript("2")}) / p\n   i = ($c - $mpSquared) / $p\n   i = $numerator / $p\n   i = $i")

        // Step 4: Hitung inverse modulo 2*mp mod p
        val twoMp = (2 * mp) % p
        val twoMpInverse = twoMp.modInverse(p)
        steps.add("4. Hitung inverse dari 2 · mp mod p\n   2 · mp = 2 · $mp = $twoMp\n   (2 · mp)^-1 mod $p = $twoMpInverse")

        // Step 5: Hitung j = i * (2*mp)^-1 mod p
        val j = (((i % p) * twoMpInverse) % p).let { if (it < 0) (it + p) else it }
        steps.add("5. Hitung j = i · (2 · mp)^-1 mod p\n   j = $i · $twoMpInverse mod $p\n   j = $j")

        // Step 6: Hitung m = mp + j * p
        var m = mp + j * p
        steps.add("6. Hitung m = mp + j · p\n   m = $mp + $j · $p\n   m = $m")

        // Step 7: Jika m >= p^2/2 maka m = p^2 - m
        val threshold = pSquared / 2
        if (m >= threshold) {
            val oldM = m
            m = (pSquared - m)
            steps.add("7. Karena m >= p${superscript("2")}/2, ubah:\n   m = p${superscript("2")} - m\n   m = $pSquared - $oldM\n   m = $m")
        } else {
            steps.add("7. Karena m < p${superscript("2")}/2, m tidak berubah\n   m = $m")
        }

        val result = RabinPDecryptResult(
            ciphertext = c,
            p = p,
            n = pSquared,
            message = m,
            steps = steps
        )

        _state.value = currentState.copy(result = result)
    }

    fun resetResult() {
        _state.value = _state.value.copy(result = null)
    }
}
