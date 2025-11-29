package com.andreasmlbngaol.rabin.domain.service

interface RabinValidator {
    fun validateP(p: String): Pair<Boolean, String?>
    fun validateQ(q: String): Pair<Boolean, String?>
    fun validateR(r: String): Pair<Boolean, String?>
    fun validateBasicMessage(m: String, p: Long?, q: Long?): Pair<Boolean, String?>
    fun validatePMessage(m: String, p: Long?): Pair<Boolean, String?>
    fun validateHMessage(m: String, p: Long?, q: Long?, r: Long?): Pair<Boolean, String?>
    fun validateBasicCipher(c: String, p: Long?, q: Long?): Pair<Boolean, String?>
    fun validatePCipher(c: String, p: Long?): Pair<Boolean, String?>
    fun validateHCipher(c: String, p: Long?, q: Long?, r: Long?): Pair<Boolean, String?>
}