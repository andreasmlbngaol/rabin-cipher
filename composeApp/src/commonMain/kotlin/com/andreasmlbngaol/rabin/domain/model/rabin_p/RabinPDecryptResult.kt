package com.andreasmlbngaol.rabin.domain.model.rabin_p

data class RabinPDecryptResult(
    val ciphertext: Long,
    val p: Long,
    val n: Long,
    val message: Long,
    val steps: List<String>
)