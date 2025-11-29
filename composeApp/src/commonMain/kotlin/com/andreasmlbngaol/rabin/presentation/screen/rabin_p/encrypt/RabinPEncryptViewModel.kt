package com.andreasmlbngaol.rabin.presentation.screen.rabin_p.encrypt

import androidx.lifecycle.ViewModel
import com.andreasmlbngaol.rabin.domain.service.RabinValidator
import com.andreasmlbngaol.rabin.domain.model.rabin_p.RabinPEncryptResult
import com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.decrypt.superscript
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RabinPEncryptViewModel(
    private val validator: RabinValidator
) : ViewModel() {
    private val _state = MutableStateFlow(RabinPEncryptState())
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

    private fun validateAndUpdate(state: RabinPEncryptState): RabinPEncryptState {
        val errors = mutableMapOf<String, String>()

        if (state.pAsString.isNotEmpty()) {
            val (_, error) = validator.validateP(state.pAsString)
            error?.let { errors["p"] = it }
        }

        if (state.qAsString.isNotEmpty()) {
            val (_, error) = validator.validateQ(state.qAsString)
            error?.let { errors["q"] = it }
        }

        if (state.messageAsString.isNotEmpty()) {
            val (_, error) = validator.validatePMessage(state.messageAsString, state.p)
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

        steps.add("1. Hitung Public Key: n = p${superscript("2")} · q\n   n = $p${superscript("2")} · $q\n   n = ${p * p * q}")

        steps.add("2. Private Key: p\n   Private Key = $p")

        val mSquared = m * m
        val ciphertext = mSquared % n
        steps.add("3. Enkripsi: c = m${superscript("2")} mod n\n   c = $m${superscript("2")} mod $n\n   c = $mSquared mod $n\n   c = $ciphertext")

        val result = RabinPEncryptResult(
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
