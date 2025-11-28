package com.andreasmlbngaol.rabin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform