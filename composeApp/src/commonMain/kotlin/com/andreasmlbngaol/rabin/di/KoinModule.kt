package com.andreasmlbngaol.rabin.di

import com.andreasmlbngaol.rabin.data.service.RabinValidatorImpl
import com.andreasmlbngaol.rabin.domain.service.RabinValidator
import com.andreasmlbngaol.rabin.presentation.navigation.navigationModule
import com.andreasmlbngaol.rabin.presentation.screen.h_rabin.decrypt.HRabinDecryptViewModel
import com.andreasmlbngaol.rabin.presentation.screen.h_rabin.encrypt.HRabinEncryptViewModel
import com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.decrypt.RabinBasicDecryptViewModel
import com.andreasmlbngaol.rabin.presentation.screen.rabin_basic.encrypt.RabinBasicEncryptViewModel
import com.andreasmlbngaol.rabin.presentation.screen.rabin_p.decrypt.RabinPDecryptViewModel
import com.andreasmlbngaol.rabin.presentation.screen.rabin_p.encrypt.RabinPEncryptViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainModules = module {
    single<RabinValidator> { RabinValidatorImpl }

    viewModel { RabinBasicEncryptViewModel(get()) }
    viewModel { RabinBasicDecryptViewModel(get()) }

    viewModel { RabinPEncryptViewModel(get()) }
    viewModel { RabinPDecryptViewModel(get()) }

    viewModel { HRabinEncryptViewModel(get()) }
    viewModel { HRabinDecryptViewModel(get()) }
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