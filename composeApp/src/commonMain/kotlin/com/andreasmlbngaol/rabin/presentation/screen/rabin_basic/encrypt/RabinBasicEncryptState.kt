package com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.encrypt

import com.andreasmlbngaol.rabin.data.RabinBasicValidator

data class RabinBasicEncryptState(
    val pAsString: String = "",
    val qAsString: String = "",
    val messageAsString: String = "",
    val errors: Map<String, String> = emptyMap(),
    val result: RabinBasicEncryptionResult? = null
) {
    val p: Int? get() = pAsString.toIntOrNull()
    val q: Int? get() = qAsString.toIntOrNull()
    val message: Int? get() = messageAsString.toIntOrNull()

    val isPValid: Boolean get() {
        val (valid, _) = RabinBasicValidator.validateP(pAsString)
        return valid
    }
    val isQValid: Boolean get() {
        val (valid, _) = RabinBasicValidator.validateQ(qAsString)
        return valid
    }
    val isMessageValid: Boolean get() {
        val (valid, _) = RabinBasicValidator.validateMessage(messageAsString, p, q)
        return valid
    }

    val isAllValid: Boolean get() = isPValid && isQValid && isMessageValid
    val n: Int? get() = if (isPValid && isQValid) p!! * q!! else null
}
