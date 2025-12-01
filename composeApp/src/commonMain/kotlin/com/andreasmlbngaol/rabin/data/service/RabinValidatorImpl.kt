package com.andreasmlbngaol.rabin.data.service

import com.andreasmlbngaol.rabin.domain.service.RabinValidator

object RabinValidatorImpl : RabinValidator {
    private fun isCoprime(m: Long, n: Long): Boolean {
        var a = m
        var b = n
        while (b != 0L) {
            val temp = b
            b = a % b
            a = temp
        }
        return a == 1L
    }

    private fun isqrt(n: Long): Long {
        var x = n
        var y = (x + 1) / 2
        while (y < x) {
            x = y
            y = (x + n / x) / 2
        }
        return x
    }

    private fun isPrime(n: Long): Boolean {
        if (n < 2L) return false
        if (n == 2L) return true
        if (n % 2L == 0L) return false
        for (i in 3..isqrt(n) step 2) {
            if (n % i == 0L) return false
        }
        return true
    }

    private fun isCongruentTo3Mod4(n: Long): Boolean = n % 4L == 3L

    override fun validateP(p: String): Pair<Boolean, String?> {
        val num = p.toLongOrNull() ?: return false to "P harus angka"
        if (num <= 0) return false to "P harus positif"
        if (!isPrime(num)) return false to "P harus prima"
        if (!isCongruentTo3Mod4(num)) return false to "P harus kongruen dengan 3 dalam mod 4"
        return true to null
    }

    override fun validateQ(q: String): Pair<Boolean, String?> {
        val num = q.toLongOrNull() ?: return false to "Q harus angka"
        if (num <= 0) return false to "Q harus positif"
        if (!isPrime(num)) return false to "Q harus prima"
        if (!isCongruentTo3Mod4(num)) return false to "Q harus kongruen dengan 3 dalam mod 4"
        return true to null
    }

    override fun validateR(r: String): Pair<Boolean, String?> {
        val num = r.toLongOrNull() ?: return false to "R harus angka"
        if (num <= 0) return false to "R harus positif"
        if (!isPrime(num)) return false to "R harus prima"
        if (!isCongruentTo3Mod4(num)) return false to "R harus kongruen dengan 3 dalam mod 4"
        return true to null
    }

    override fun validateBasicMessage(
        m: String,
        p: Long?,
        q: Long?
    ): Pair<Boolean, String?> {
        val num = m.toLongOrNull() ?: return false to "Message harus angka"
        if (num <= 0) return false to "Message harus positif"
        if (p != null && q != null) {
            val n = p * q
            if (num >= n) return false to "Message harus < $n"
        }
        return true to null
    }

    override fun validatePMessage(m: String, p: Long?): Pair<Boolean, String?> {
        val num = m.toLongOrNull() ?: return false to "Message harus angka"
        if (num <= 0) return false to "Message harus positif"
        if (p != null) {
            val threshold = (p * p) / 2L

            if (num > threshold) return false to "Message harus < $threshold"
            if (!isCoprime(num, p))
                return false to "Message harus relatif prima dengan p"
        }
        return true to null
    }

    override fun validateHMessage(m: String, p: Long?, q: Long?, r: Long?): Pair<Boolean, String?> {
        val num = m.toLongOrNull() ?: return false to "Message harus angka"
        if (num <= 0) return false to "Message harus positif"
        if (p != null && q != null && r != null) {
            val n = p * q * r
            if (num >= n) return false to "Message harus < $n"
        }
        return true to null
    }

    override fun validateBasicCipher(
        c: String,
        p: Long?,
        q: Long?
    ): Pair<Boolean, String?> {
        val num = c.toLongOrNull() ?: return false to "Ciphertext harus angka"
        if (num <= 0) return false to "Ciphertext harus positif"
        if (p != null && q != null) {
            val n = p * q
            if (num >= n) return false to "Ciphertext harus < $n"
        }
        return true to null
    }

    override fun validatePCipher(c: String, p: Long?): Pair<Boolean, String?> {
        val num = c.toLongOrNull() ?: return false to "Ciphertext harus angka"
        if (num <= 0) return false to "Ciphertext harus positif"
        if (p != null) {
            if(!isCoprime(num, p))
                return false to "Ciphertext harus relatif prima dengan p"
        }
        return true to null
    }

    override fun validateHCipher(c: String, p: Long?, q: Long?, r: Long?): Pair<Boolean, String?> {
        val num = c.toLongOrNull() ?: return false to "Ciphertext harus angka"
        if (num <= 0) return false to "Ciphertext harus positif"
        if (p != null && q != null && r != null) {
            val n = p * q * r
            if (num >= n) return false to "Ciphertext harus < $n"
        }
        return true to null
    }
}