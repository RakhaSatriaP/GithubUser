package com.example.submissionawalgithub

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

open class ViewModel(private val preferences: SettingPreferences) : ViewModel() {

    class Factory(private val preferences: SettingPreferences):ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = ViewModel(preferences) as T
    }

    fun getTheme() = preferences.getThemeSetting().asLiveData()

    val hasilRespon = MutableLiveData<HasilRespon>()

    fun getUser() {
        viewModelScope.launch {
            flow {
                val response = Api.service.getUser()
                emit(response)

            }.onStart {
                hasilRespon.value = HasilRespon.Loading(true)

            }.onCompletion {
                hasilRespon.value = HasilRespon.Loading(false)

            }.catch {
                hasilRespon.value = HasilRespon.Error(it)
                it.printStackTrace()

            }.collect {
                hasilRespon.value = HasilRespon.Sukses(it)
            }
        }
    }

    fun getUser(username: String) {
        viewModelScope.launch {
            flow {
                val response = Api.service.getSearchUser(mapOf("q" to username, "per_page" to 150))
                emit(response)

            }.onStart {
                hasilRespon.value = HasilRespon.Loading(true)

            }.onCompletion {
                hasilRespon.value = HasilRespon.Loading(false)

            }.catch {
                hasilRespon.value = HasilRespon.Error(it)
                it.printStackTrace()

            }.collect {
                hasilRespon.value = HasilRespon.Sukses(it.items)
            }
        }
    }
}