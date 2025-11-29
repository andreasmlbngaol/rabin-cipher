package com.andreasmlbngaol.rabin.domain.model.rabin_p

data class RabinPEncryptResult(
    val message: Long,
    val p: Long,
    val q: Long,
    val n: Long,
    val ciphertext: Long,
    val steps: List<String>
)