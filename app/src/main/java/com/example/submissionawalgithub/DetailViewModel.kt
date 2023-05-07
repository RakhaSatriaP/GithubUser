package com.example.submissionawalgithub

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel

class DetailViewModel(private val db: DatabaseModule) : ViewModel() {
    val detailUser = MutableLiveData<HasilRespon>()
    val followersUser = MutableLiveData<HasilRespon>()
    val followingUser = MutableLiveData<HasilRespon>()
    val resultSuksesfav = MutableLiveData<Boolean>()
    val resultDeleteFav = MutableLiveData<Boolean>()

    private var isFav = false

    fun favUser(item: Response.Item?) {
        viewModelScope.launch {
            item?.let {
                if (isFav) {
                    db.dao.delete(item)
                    resultDeleteFav.value = true
                } else {
                    db.dao.insert(item)
                    resultSuksesfav.value = true
                }
            }
            isFav = !isFav
        }
    }

    fun findfav(id: Int, listenFav: () -> Unit) {
        viewModelScope.launch {
            val user = db.dao.findById(id)
            if (user != null) {
                listenFav()
                isFav = true
            }
        }
    }

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            flow {
                val response = Api.service.getDetailUser(username)
                emit(response)

            }.onStart {
                detailUser.value = HasilRespon.Loading(true)

            }.onCompletion {
                detailUser.value = HasilRespon.Loading(false)

            }.catch {
                detailUser.value = HasilRespon.Error(it)
                it.printStackTrace()

            }.collect {
                detailUser.value = HasilRespon.Sukses(it)
            }
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            flow {
                val response = Api.service.getFollowersUser(username)
                emit(response)

            }.onStart {
                followersUser.value = HasilRespon.Loading(true)

            }.onCompletion {
                followersUser.value = HasilRespon.Loading(false)

            }.catch {
                followersUser.value = HasilRespon.Error(it)
                it.printStackTrace()

            }.collect {
                followersUser.value = HasilRespon.Sukses(it)
            }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            flow {
                val response = Api.service.getFollowingsUser(username)
                emit(response)

            }.onStart {
                followingUser.value = HasilRespon.Loading(true)

            }.onCompletion {
                followingUser.value = HasilRespon.Loading(false)

            }.catch {
                followingUser.value = HasilRespon.Error(it)
                it.printStackTrace()

            }.collect {
                followingUser.value = HasilRespon.Sukses(it)
            }
        }
    }

    class Factory(private val db: DatabaseModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailViewModel(db) as T
    }
}