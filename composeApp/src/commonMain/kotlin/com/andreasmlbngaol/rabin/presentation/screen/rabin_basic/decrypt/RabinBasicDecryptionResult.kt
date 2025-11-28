package com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.decrypt

data class RabinBasicDecryptionResult(
    val ciphertext: Int,
    val p: Int,
    val q: Int,
    val n: Int,
    val candidates: List<Long>,
    val steps: List<String>
)
