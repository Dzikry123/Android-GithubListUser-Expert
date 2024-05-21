package com.example.core.domain.usecase

import androidx.lifecycle.LiveData
import com.example.core.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {
    fun getAllFavUser(): Flow<MutableList<GithubUser.Item>>
    suspend fun insert(fav: GithubUser.Item)
    suspend fun delete(fav: GithubUser.Item)
    suspend fun findById(id: Int): GithubUser.Item?
}