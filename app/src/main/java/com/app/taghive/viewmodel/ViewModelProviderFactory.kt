package com.app.taghive.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.taghive.repository.AppRepository

class ViewModelProviderFactory(
    val app: Application,
    private val appRepository: AppRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CryptoViewModel::class.java)) {
            return CryptoViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(GetCryptoViewModel::class.java)) {
            return GetCryptoViewModel(app, appRepository) as T
        }


        throw IllegalArgumentException("Unknown class name")
    }

}