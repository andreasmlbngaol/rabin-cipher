package com.andreasmlbngaol.rabin.presentation.screen.h_rabin.encrypt

import com.andreasmlbngaol.rabin.data.service.RabinValidatorImpl
import com.andreasmlbngaol.rabin.domain.model.h_rabin.HRabinEncryptResult

data class HRabinEncryptState(
    val pAsString: String = "",
    val qAsString: String = "",
    val rAsString: String = "",
    val messageAsString: String = "",
    val errors: Map<String, String> = emptyMap(),
    val result: HRabinEncryptResult? = null
) {
    val p: Long? get() = pAsString.toLongOrNull()
    val q: Long? get() = qAsString.toLongOrNull()
    val r: Long? get() = rAsString.toLongOrNull()
    val message: Long? get() = messageAsString.toLongOrNull()

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

    val isMessageValid: Boolean get() {
        val (valid, _) = RabinValidatorImpl.validateHMessage(messageAsString, p, q, r)
        return valid
    }

    val isAllValid: Boolean get() = isPValid && isQValid && isRValid && isMessageValid
    val n: Long? get() = if (isPValid && isQValid && isRValid) p!! * q!! * r!! else null
}