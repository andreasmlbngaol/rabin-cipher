package com.andreasmlbngaol.rabin.presentation.screen.rabin_p.decrypt

import com.andreasmlbngaol.rabin.data.service.RabinValidatorImpl
import com.andreasmlbngaol.rabin.domain.model.rabin_p.RabinPDecryptResult

data class RabinPDecryptState(
    val pAsString: String = "",
    val ciphertextAsString: String = "",
    val errors: Map<String, String> = emptyMap(),
    val result: RabinPDecryptResult? = null
) {
    val p: Long? get() = pAsString.toLongOrNull()
    val ciphertext: Long? get() = ciphertextAsString.toLongOrNull()

    val isPValid: Boolean get() {
        val (valid, _) = RabinValidatorImpl.validateP(pAsString)
        return valid
    }
    val isCiphertextValid: Boolean get() {
        val (valid, _) = RabinValidatorImpl.validatePCipher(ciphertextAsString, p)
        return valid
    }

    val isAllValid: Boolean get() = isPValid && isCiphertextValid
    val pSquared: Long? get() = if (isPValid) p!! * p!! else null
}
