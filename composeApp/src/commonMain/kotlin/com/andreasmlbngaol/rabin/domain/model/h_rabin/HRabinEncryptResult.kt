package com.andreasmlbngaol.rabin.domain.model.h_rabin

data class HRabinEncryptResult(
    val message: Long,
    val p: Long,
    val q: Long,
    val r: Long,
    val n: Long,
    val ciphertext: Long,
    val steps: List<String>
)