package com.andreasmlbngaol.rabin.presentation.screen.h_rabin.decrypt

import com.andreasmlbngaol.rabin.data.service.RabinValidatorImpl
import com.andreasmlbngaol.rabin.domain.model.h_rabin.HRabinDecryptResult

data class HRabinDecryptState(
    val pAsString: String = "",
    val qAsString: String = "",
    val rAsString: String = "",
    val ciphertextAsString: String = "",
    val errors: Map<String, String> = emptyMap(),
    val result: HRabinDecryptResult? = null
) {
    val p: Long? get() = pAsString.toLongOrNull()
    val q: Long? get() = qAsString.toLongOrNull()
    val r: Long? get() = rAsString.toLongOrNull()
    val ciphertext: Long? get() = ciphertextAsString.toLongOrNull()

    val isPValid: Boolean get() {
        val (valid, _) = RabinValidatorImpl.validateP(pAsString)
        return valid
    }
    val isQValid: Boolean get() {
        val (valid, _) = RabinValidatorImpl.validateQ(qAsString)
        return valid
    }
    val isRValid: Boolean get() {
        val (valid, _) = RabinValidatorImpl.validateR(rAsString)
        return valid
    }
    val isCiphertextValid: Boolean get() {
        val (valid, _) = RabinValidatorImpl.validateHCipher(ciphertextAsString, p, q, r)
        return valid
    }

    val isAllValid: Boolean get() = isPValid && isQValid && isRValid && isCiphertextValid
    val n: Long? get() = if (isPValid && isQValid && isRValid) p!! * q!! * r!! else null
}