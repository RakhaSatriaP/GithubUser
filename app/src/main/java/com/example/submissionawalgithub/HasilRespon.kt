package com.example.submissionawalgithub

sealed class HasilRespon {
    data class Sukses<out S>(val data: S) : HasilRespon()
    data class Loading(val L: Boolean) : HasilRespon()
    data class Error(val E: Throwable) : HasilRespon()
}
