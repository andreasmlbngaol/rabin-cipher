package com.andreasmlbngaol.rabin.presentation.screen.rabin_p.encrypt

import com.andreasmlbngaol.rabin.data.service.RabinValidatorImpl
import com.andreasmlbngaol.rabin.domain.model.rabin_p.RabinPEncryptResult

data class RabinPEncryptState(
    val pAsString: String = "",
    val qAsString: String = "",
    val messageAsString: String = "",
    val errors: Map<String, String> = emptyMap(),
    val result: RabinPEncryptResult? = null
) {
    val p: Long? get() = pAsString.toLongOrNull()
    val q: Long? get() = qAsString.toLongOrNull()
    val message: Long? get() = messageAsString.toLongOrNull()

    val isPValid: Boolean get() {
        val (valid, _) = RabinValidatorImpl.validateP(pAsString)
        return valid
    }
    val isQValid: Boolean get() {
        val (valid, _) = RabinValidatorImpl.validateQ(qAsString)
        return valid
    }
    val isMessageValid: Boolean get() {
        val (valid, _) = RabinValidatorImpl.validatePMessage(messageAsString, p)
        return valid
    }

    val isAllValid: Boolean get() = isPValid && isQValid && isMessageValid
    val pSquared: Long? get() = if (isPValid) p!! * p!! else null
    val n: Long? get() = if (isPValid && isQValid) p!! * p!! * q!! else null
}
