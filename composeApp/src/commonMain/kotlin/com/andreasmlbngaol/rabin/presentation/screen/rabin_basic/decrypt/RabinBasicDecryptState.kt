package com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.decrypt

import com.andreasmlbngaol.rabin.data.RabinBasicValidator

data class RabinBasicDecryptState(
    val pAsString: String = "",
    val qAsString: String = "",
    val ciphertextAsString: String = "",
    val errors: Map<String, String> = emptyMap(),
    val result: RabinBasicDecryptionResult? = null
) {
    val p: Int? get() = pAsString.toIntOrNull()
    val q: Int? get() = qAsString.toIntOrNull()
    val ciphertext: Int? get() = ciphertextAsString.toIntOrNull()

    val isPValid: Boolean get() {
        val (valid, _) = RabinBasicValidator.validateP(pAsString)
        return valid
    }
    val isQValid: Boolean get() {
        val (valid, _) = RabinBasicValidator.validateQ(qAsString)
        return valid
    }
    val isCiphertextValid: Boolean get() {
        val (valid, _) = RabinBasicValidator.validateCiphertext(ciphertextAsString, p, q)
        return valid
    }

    val isAllValid: Boolean get() = isPValid && isQValid && isCiphertextValid
    val n: Int? get() = if (isPValid && isQValid) p!! * q!! else null
}
