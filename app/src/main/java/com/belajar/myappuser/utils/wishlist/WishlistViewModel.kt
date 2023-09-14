package com.belajar.myappuser.utils.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.belajar.myappuser.data.model.User
import com.belajar.myappuser.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class WishlistViewModel (private val repository: UserRepository): ViewModel(){

    val itemsFavorite : Flow<PagingData<User>> = repository
        .getFavoriteUser()
        .cachedIn(viewModelScope)

}

class WishlistViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WishlistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WishlistViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}