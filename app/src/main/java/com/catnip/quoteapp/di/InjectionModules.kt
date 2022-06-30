package com.catnip.quoteapp.di

import org.koin.dsl.module

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object InjectionModules {
    fun getModules() = listOf(network, dataSource, database, repository, useCases, viewModels)

    private val network = module {

    }
    private val dataSource = module {

    }
    private val repository = module {

    }
    private val database = module {

    }
    private val useCases = module {

    }
    private val viewModels = module {

    }
}