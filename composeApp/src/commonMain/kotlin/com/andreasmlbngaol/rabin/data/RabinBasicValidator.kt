package com.andreasmlbngaol.rabin.data

object RabinBasicValidator {
    fun isPrime(n: Int): Boolean {
        if (n < 2) return false
        if (n == 2) return true
        if (n % 2 == 0) return false
        for (i in 3..kotlin.math.sqrt(n.toDouble()).toInt() step 2) {
            if (n % i == 0) return false
        }
        return true
    }

    fun isCongruentTo3Mod4(n: Int): Boolean = n % 4 == 3

    fun validateP(p: String): Pair<Boolean, String?> {
        val num = p.toIntOrNull() ?: return false to "P harus angka"
        if (num <= 0) return false to "P harus positif"
        if (!isPrime(num)) return false to "P harus prima"
        if (!isCongruentTo3Mod4(num)) return false to "P harus  ≡ 3 (mod 4)"
        return true to null
    }

    fun validateQ(q: String): Pair<Boolean, String?> {
        val num = q.toIntOrNull() ?: return false to "Q harus angka"
        if (num <= 0) return false to "Q harus positif"
        if (!isPrime(num)) return false to "Q harus prima"
        if (!isCongruentTo3Mod4(num)) return false to "Q harus ≡ 3 (mod 4)"
        return true to null
    }

    fun validateMessage(m: String, p: Int?, q: Int?): Pair<Boolean, String?> {
        val num = m.toIntOrNull() ?: return false to "Message harus angka"
        if (num <= 0) return false to "Message harus positif"
        if (p != null && q != null) {
            val n = p * q
            if (num >= n) return false to "Message harus < $n"
        }
        return true to null
    }

    fun validateCiphertext(c: String, p: Int?, q: Int?): Pair<Boolean, String?> {
        val num = c.toIntOrNull() ?: return false to "Ciphertext harus angka"
        if (num <= 0) return false to "Ciphertext harus positif"
        if (p != null && q != null) {
            val n = p * q
            if (num >= n) return false to "Ciphertext harus < $n"
        }
        return true to null
    }
}
