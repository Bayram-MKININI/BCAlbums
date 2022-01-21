package fr.leboncoin.bcalbums.di

import fr.leboncoin.bcalbums.presenters.MainFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainFragmentViewModel(
            dataRepository = get(),
            networkHelper = get()
        )
    }
}