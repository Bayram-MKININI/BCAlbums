package fr.leboncoin.bcalbums.di

import fr.leboncoin.bcalbums.controllers.MainFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainFragmentViewModel(
            application = get(),
            dataRepository = get(),
            networkHelper = get()
        )
    }
}