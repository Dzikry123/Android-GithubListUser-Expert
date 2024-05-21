package com.example.favorite.ui.fav

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.data.repo.FavoriteRepository
import com.example.core.domain.usecase.FavoriteUseCase


class FavoriteViewModel (private val interactor: FavoriteUseCase) : ViewModel() {

    fun getAllFavUser() = interactor.getAllFavUser()
}

class FavoriteViewModelFactory(private val interactor: FavoriteUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(interactor) as T
        }
        throw IllegalAccessException("Unkwon ViewModel :" + modelClass.name)
    }
}