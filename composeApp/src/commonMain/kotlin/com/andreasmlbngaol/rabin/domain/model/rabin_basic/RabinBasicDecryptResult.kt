package com.andreasmlbngaol.rabin.domain.model.rabin_basic

data class RabinBasicDecryptResult(
    val ciphertext: Long,
    val p: Long,
    val q: Long,
    val n: Long,
    val candidates: List<Long>,
    val steps: List<String>
)