package com.andreasmlbngaol.rabin.domain.model.h_rabin

data class HRabinDecryptResult(
    val ciphertext: Long,
    val p: Long,
    val q: Long,
    val r: Long,
    val n: Long,
    val candidates: List<Long>,
    val steps: List<String>
)