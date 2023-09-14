package com.belajar.myappuser.utils.list
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.belajar.myappuser.data.model.User
import com.belajar.myappuser.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    // untuk view aktivitas dan fragmen menyediakan metode untuk transaksi data
    private val repo: UserRepository,

): ViewModel() {

    //variable untuk menampung data flow paging dari function getUsers yang ada di repository
    val items : Flow<PagingData<User>> = repo
    .getUsers()
    .cachedIn(viewModelScope)
}

//untuk membuat instance viewmodel
class ProductListViewModelFactory(
    private val repository: UserRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}