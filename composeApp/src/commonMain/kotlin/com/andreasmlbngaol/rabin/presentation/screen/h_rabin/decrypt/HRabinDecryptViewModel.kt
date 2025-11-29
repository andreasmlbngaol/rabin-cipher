package com.andreasmlbngaol.rabin.presentation.screen.h_rabin.decrypt

import androidx.lifecycle.ViewModel
import com.andreasmlbngaol.rabin.domain.model.h_rabin.HRabinDecryptResult
import com.andreasmlbngaol.rabin.domain.service.RabinValidator
import com.andreasmlbngaol.rabin.presentation.utils.modInverse
import com.andreasmlbngaol.rabin.presentation.utils.modPow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HRabinDecryptViewModel(
    private val validator: RabinValidator
): ViewModel() {
    private val _state = MutableStateFlow(HRabinDecryptState())
    val state = _state.asStateFlow()

    private fun validateAndUpdate(state: HRabinDecryptState): HRabinDecryptState {
        val errors = mutableMapOf<String, String>()

        if (state.pAsString.isNotEmpty()) {
            val (_, error) = validator.validateP(state.pAsString)
            error?.let { errors["p"] = it }
        }

        if (state.qAsString.isNotEmpty()) {
            val (_, error) = validator.validateQ(state.qAsString)
            error?.let { errors["q"] = it }
        }

        if (state.rAsString.isNotEmpty()) {
            val (_, error) = validator.validateR(state.rAsString)
            error?.let { errors["r"] = it }
        }

        if (state.ciphertextAsString.isNotEmpty()) {
            val (_, error) = validator.validateHCipher(
                state.ciphertextAsString,
                state.p,
                state.q,
                state.r
            )
            error?.let { errors["ciphertext"] = it }
        }

        return state.copy(errors = errors)
    }

    fun onPChange(text: String) {
        val newState = _state.value.copy(pAsString = text)
        _state.value = validateAndUpdate(newState)
    }

    fun onQChange(text: String) {
        val newState = _state.value.copy(qAsString = text)
        _state.value = validateAndUpdate(newState)
    }

    fun onRChange(text: String) {
        val newState = _state.value.copy(rAsString = text)
        _state.value = validateAndUpdate(newState)
    }

    fun onCiphertextChange(text: String) {
        val newState = _state.value.copy(ciphertextAsString = text)
        _state.value = validateAndUpdate(newState)
    }

    fun resetResult() {
        _state.value = _state.value.copy(result = null)
    }

    fun decrypt() {
        val currentState = _state.value
        if (!currentState.isAllValid) return

        val p = currentState.p!!
        val q = currentState.q!!
        val r = currentState.r!!
        val n = currentState.n!!
        val c = currentState.ciphertext!!

        val steps = mutableListOf<String>()

        steps.add(
            "Input:\n" +
                    "   p = $p\n" +
                    "   q = $q\n" +
                    "   r = $r\n" +
                    "   c = $c"
        )

        val exponentP = (p + 1) / 4
        val mp1 = c.modPow(exponentP, p)
        val mp2 = p - mp1
        steps.add(
            "1. Hitung mp1 & mp2\n" +
                    "   mp1 = c^((p+1)/4) mod p\n" +
                    "   mp1 = $c^${(exponentP.toString())} mod $p\n" +
                    "   mp1 = $mp1\n\n" +
                    "   mp2 = p - mp1\n" +
                    "   mp2 = $p - $mp1\n" +
                    "   mp2 = $mp2"
        )

        val exponentQ = (q + 1) / 4
        val mq1 = c.modPow(exponentQ, q)
        val mq2 = q - mq1
        steps.add(
            "2. Hitung mq1 & mq2\n" +
                    "   mq1 = c^((q+1)/4) mod q\n" +
                    "   mq1 = $c^${(exponentQ.toString())} mod $q\n" +
                    "   mq1 = $mq1\n\n" +
                    "   mq2 = q - mq1\n" +
                    "   mq2 = $q - $mq1\n" +
                    "   mq2 = $mq2"
        )

        val exponentR = (r + 1) / 4
        val mr1 = c.modPow(exponentR, r)
        val mr2 = r - mr1
        steps.add(
            "3. Hitung mr1 & mr2\n" +
                    "   mr1 = c^((r+1)/4) mod r\n" +
                    "   mr1 = $c^${(exponentR.toString())} mod $r\n" +
                    "   mr1 = $mr1\n\n" +
                    "   mr2 = r - mr1\n" +
                    "   mr2 = $r - $mr1\n" +
                    "   mr2 = $mr2"
        )

        val mp = n / p
        val mq = n / q
        val mr = n / r
        steps.add(
            "4. Hitung Mp, Mq, Mr\n" +
                    "   Mp = n / p = $n / $p = $mp\n" +
                    "   Mq = n / q = $n / $q = $mq\n" +
                    "   Mr = n / r = $n / $r = $mr"
        )

        val bp = mp.modInverse(p)
        val bq = mq.modInverse(q)
        val br = mr.modInverse(r)
        steps.add(
            "5. Hitung bp, bq, br\n" +
                    "   bp = Mp^(-1) mod p = $mp^(-1) mod $p = $bp\n" +
                    "   bq = Mq^(-1) mod q = $mq^(-1) mod $q = $bq\n" +
                    "   br = Mr^(-1) mod r = $mr^(-1) mod $r = $br"
        )

        val mbp = mp * bp
        val mbq = mq * bq
        val mbr = mr * br

        fun x(ap: Long, aq: Long, ar: Long): Long {
            return (mbp * ap + mbq * aq + mbr * ar).mod(n)
        }

        steps.add(
            "6. Substitusikan ke CRT\n" +
                    "   x = ap · Mp · bp + aq · Mq · bq + ar · Mr · br (mod n)\n" +
                    "   x = ap · $mp · $bp + aq · $mq · $bq + ar · $mr · $br (mod $n)\n" +
                    "   x = $mbp · ap + $mbq · aq + $mbr · ar (mod $n)"
        )

        val m1 = x(mp1, mq1, mr1)
        val m2 = n - m1
        val m3 = x(mp1, mq1, mr2)
        val m4 = n - m3
        val m5 = x(mp1, mq2, mr1)
        val m6 = n - m5
        val m7 = x(mp1, mq2, mr2)
        val m8 = n - m7

        steps.add(
            "7. Hitung x untuk semua kombinasi akar modulo M1 - M8\n" +
                    "   M1 = $mbp · mp1 + $mbq · mq1 + $mbr · mr1 (mod $n)\n" +
                    "      = $mbp · $mp1 + $mbq · $mq1 + $mbr · $mr1 (mod $n) = $m1\n" +
                    "   M2 = n - M1 = $n - $m1 = $m2\n" +
                    "   M3 = $mbp · mp1 + $mbq · mq1 + $mbr · mr2 (mod $n)\n" +
                    "      = $mbp · $mp1 + $mbq · $mq1 + $mbr · $mr2 (mod $n) = $m3\n" +
                    "   M4 = n - M3 = $n - $m3 = $m4\n" +
                    "   M5 = $mbp · mp1 + $mbq · mq2 + $mbr · mr1 (mod $n)\n" +
                    "      = $mbp · $mp1 + $mbq · $mq2 + $mbr · $mr1 (mod $n) = $m5\n" +
                    "   M6 = n - M5 = $n - $m5 = $m6\n" +
                    "   M7 = $mbp · mp1 + $mbq · mq2 + $mbr · mr2 (mod $n)\n" +
                    "      = $mbp · $mp1 + $mbq · $mq2 + $mbr · $mr2 (mod $n) = $m7\n" +
                    "   M8 = n - M7 = $n - $m7 = $m8"
        )

        val candidates = listOf(m1, m2, m3, m4, m5, m6, m7, m8)
        val result = HRabinDecryptResult(
            ciphertext = c,
            p = p,
            q = q,
            r = r,
            n = n,
            candidates = candidates,
            steps = steps
        )

        _state.value = currentState.copy(result = result)
    }
}