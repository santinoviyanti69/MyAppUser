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

    //nge getdetailuser dengan parameter username
    fun getDetailUser(username: String?) {
        viewModelScope.launch {

            //variabel detailuser dengan repo dari data getUser dengan parameter username
            val detailUser = repo.getUser(username)
            setUser(detailUser)

        }
    }


    //menginisiasi setuser dengan parameter user
    fun setUser(user: User){
        viewModelScope.launch {

            //nge get favortite untuk ngambil user menggunakan parameter id
            repo.getFavorite(user.id).collect{

                //nge get value user di post menggunakan postvalue dengan parameter User
                if(it == null){
                    _detailUser.postValue(user)

                //nge get value menggunakan parameter it dengan type data user
                }else{
                    _detailUser.postValue(it)
                }
            }
        }
    }

    //untuk menampilkan favorite di detailuser
    fun setFavorite() {
        viewModelScope.launch {

            //sebagai kondisi jika valuenya tidak null dia menghapus favorite dan jika null dia menambahkan favorite
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
