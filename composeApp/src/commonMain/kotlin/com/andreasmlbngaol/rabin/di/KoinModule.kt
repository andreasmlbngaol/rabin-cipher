package com.andreasmlbngaol.rabin.di

import com.andreasmlbngaol.rabin.presentation.navigation.navigationModule
import com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.decrypt.RabinBasicDecryptViewModel
import com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.encrypt.RabinBasicEncryptViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainModules = module {
    viewModel { RabinBasicEncryptViewModel() }
    viewModel { RabinBasicDecryptViewModel() }
}

fun initKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(
            mainModules,
            navigationModule
        )
    }
}