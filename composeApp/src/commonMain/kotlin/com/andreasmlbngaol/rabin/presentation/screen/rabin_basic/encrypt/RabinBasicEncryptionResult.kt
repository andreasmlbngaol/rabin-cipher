package com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.encrypt

data class RabinBasicEncryptionResult(
    val message: Int,
    val p: Int,
    val q: Int,
    val n: Int,
    val ciphertext: Long,
    val steps: List<String>
)
