package com.andreasmlbngaol.rabin.presentation.screen.h_rabin.encrypt

import androidx.lifecycle.ViewModel
import com.andreasmlbngaol.rabin.domain.model.h_rabin.HRabinEncryptResult
import com.andreasmlbngaol.rabin.domain.service.RabinValidator
import com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.decrypt.superscript
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HRabinEncryptViewModel(
    private val validator: RabinValidator
): ViewModel() {
    private val _state = MutableStateFlow(HRabinEncryptState())
    val state = _state.asStateFlow()

    private fun validateAndUpdate(state: HRabinEncryptState): HRabinEncryptState {
        val errors = mutableMapOf<String, String>()

        if (state.pAsString.isNotEmpty()) {
            val (_, error) = validator.validateP(state.pAsString)
            error?.let { errors["p"] = it }
        }

        if (state.qAsString.isNotEmpty()) {
            val (_, error) = validator.validateQ(state.qAsString)
            error?.let { errors["q"] = it }
        }

        if (state.rAsString.isNotEmpty()) {
            val (_, error) = validator.validateR(state.rAsString)
            error?.let { errors["r"] = it }
        }

        if (state.messageAsString.isNotEmpty()) {
            val (_, error) = validator.validateHMessage(
                state.messageAsString,
                state.p,
                state.q,
                state.r
            )
            error?.let { errors["message"] = it }
        }

        return state.copy(errors = errors)

    }

    fun onPChange(text: String) {
        val newState = _state.value.copy(pAsString = text)
        _state.value = validateAndUpdate(newState)
    }

    fun onQChange(text: String) {
        val newState = _state.value.copy(qAsString = text)
        _state.value = validateAndUpdate(newState)
    }

    fun onRChange(text: String) {
        val newState = _state.value.copy(rAsString = text)
        _state.value = validateAndUpdate(newState)
    }

    fun onMessageChange(text: String) {
        val newState = _state.value.copy(messageAsString = text)
        _state.value = validateAndUpdate(newState)
    }

    fun resetResult() {
        _state.value = _state.value.copy(result = null)
    }

    fun encrypt() {
        val currentState = _state.value
        if (!currentState.isAllValid) return

        val p = currentState.p!!
        val q = currentState.q!!
        val r = currentState.r!!
        val m = currentState.message!!
        val n = currentState.n!!

        val steps = mutableListOf<String>()

        steps.add(
            "Input:\n" +
                "   p = $p\n" +
                "   q = $q\n" +
                "   r = $r\n" +
                "   m = $m"
        )

        steps.add(
            "1. Hitung Public Key: n = p · q · r\n" +
                    "   n = $p  · $q · $r\n" +
                    "   n = $n"
        )

        steps.add(
            "2. Private Key: (p, q, r)\n" +
                    "   Private Key = ($p, $q, $r)"
        )

        val mSquared = m * m
        val ciphertext = mSquared.mod(n)

        steps.add(
            "3. Enkripsi: c = m${superscript("2")} mod n\n" +
                    "   c = $m${superscript("2")} mod $n\n" +
                    "   c = $mSquared mod $n\n" +
                    "   c = $ciphertext"
        )

        val result = HRabinEncryptResult(
            message = m,
            p = p,
            q = q,
            r = r,
            n = n,
            ciphertext = ciphertext,
            steps = steps
        )

        _state.value = currentState.copy(result = result)
    }
}