package com.belajar.myappuser.utils.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.belajar.myappuser.data.model.User
import com.belajar.myappuser.data.repository.UserRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repo: UserRepository
): ViewModel() {

    val resultSuccessFavorite = MutableLiveData<Boolean>()
    val resultDeleteFavorite = MutableLiveData<Boolean>()

    private val _detailUser = MutableLiveData<User>()
    val detailUser: LiveData<User>
        get() = _detailUser

    fun getDetailUser(username: String?) {
        viewModelScope.launch {
            val detailUser = repo.getListUser(username)
            setUser(detailUser)

        }
    }

    fun setUser(listUser: User){
        viewModelScope.launch {
            repo.gerFavorite(listUser.id).collect{
                if(it == null){
                    _detailUser.postValue(listUser)

                }else{
                    _detailUser.postValue(it)
                }
            }
        }
    }
    fun setFavorite() {
        viewModelScope.launch {
            if(_detailUser.value != null){
                if (_detailUser.value?.isFavorite == true){
                    repo.setFavoriteDelete(_detailUser.value!!)
                }else{
                    repo.setFavoriteInsert(_detailUser.value!!)
                }
            }
        }
    }

}

class DetailViewModelFactory(
    private val repository: UserRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
