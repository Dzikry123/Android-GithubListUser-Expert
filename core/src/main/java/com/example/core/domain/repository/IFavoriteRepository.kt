package com.example.core.domain.repository

import androidx.lifecycle.LiveData
import com.example.core.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository {

    fun getAllFavUser(): Flow<MutableList<GithubUser.Item>>
    suspend fun insert(fav: GithubUser.Item)
    suspend fun delete(fav: GithubUser.Item)
    suspend fun findById(id: Int): GithubUser.Item?
}