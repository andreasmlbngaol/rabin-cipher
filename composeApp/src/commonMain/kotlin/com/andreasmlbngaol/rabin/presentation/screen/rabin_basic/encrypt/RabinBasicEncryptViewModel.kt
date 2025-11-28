package com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.encrypt

import androidx.lifecycle.ViewModel
import com.andreasmlbngaol.rabin.data.RabinBasicValidator
import com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.decrypt.superscript
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RabinBasicEncryptViewModel: ViewModel() {
    private val _state = MutableStateFlow(RabinBasicEncryptState())
    val state = _state.asStateFlow()

    fun onPChange(text: String) {
        val newState = _state.value.copy(pAsString = text)
        _state.value = validateAndUpdate(newState)
    }

    fun onQChange(text: String) {
        val newState = _state.value.copy(qAsString = text)
        _state.value = validateAndUpdate(newState)
    }

    fun onMessageChange(text: String) {
        val newState = _state.value.copy(messageAsString = text)
        _state.value = validateAndUpdate(newState)
    }

    private fun validateAndUpdate(state: RabinBasicEncryptState): RabinBasicEncryptState {
        val errors = mutableMapOf<String, String>()

        if (state.pAsString.isNotEmpty()) {
            val (_, error) = RabinBasicValidator.validateP(state.pAsString)
            error?.let { errors["p"] = it }
        }

        if (state.qAsString.isNotEmpty()) {
            val (_, error) = RabinBasicValidator.validateQ(state.qAsString)
            error?.let { errors["q"] = it }
        }

        if (state.messageAsString.isNotEmpty()) {
            val (_, error) = RabinBasicValidator.validateMessage(
                state.messageAsString,
                state.p,
                state.q
            )
            error?.let { errors["message"] = it }
        }

        return state.copy(errors = errors)
    }

    fun encrypt() {
        val currentState = _state.value
        if (!currentState.isAllValid) return

        val p = currentState.p!!
        val q = currentState.q!!
        val m = currentState.message!!
        val n = currentState.n!!

        val steps = mutableListOf<String>()

        steps.add("Input:\n   p = $p\n   q = $q\n   m = $m")

        steps.add("1. Hitung Public Key: n = p · q\n   n = $p · $q\n   n = $n")

        steps.add("2. Private Key: (p, q)\n   Private Key = ($p, $q)")

        val mSquared = m.toLong() * m
        val ciphertext = mSquared % n
        steps.add("3. Enkripsi: c = m${superscript("2")} mod n\n   c = $m${superscript("2")} mod $n\n   c = $mSquared mod $n\n   c = $ciphertext")

        val result = RabinBasicEncryptionResult(
            message = m,
            p = p,
            q = q,
            n = n,
            ciphertext = ciphertext,
            steps = steps
        )

        _state.value = currentState.copy(result = result)
    }
    fun resetResult() {
        _state.value = _state.value.copy(result = null)
    }
}