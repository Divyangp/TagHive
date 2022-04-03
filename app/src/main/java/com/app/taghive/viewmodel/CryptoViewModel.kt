package com.app.taghive.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.taghive.R
import com.app.taghive.util.Resource
import com.app.taghive.repository.AppRepository
import com.app.taghive.app.MyApplication
import com.app.taghive.model.CryptoResponse
import com.app.taghive.util.Event
import com.app.taghive.util.Utils
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class CryptoViewModel(app: Application, private val appRepository: AppRepository) : AndroidViewModel(app) {

    private val _cryptoGetResponse = MutableLiveData<Event<Resource<CryptoResponse>>>()
    val cryptoGetResponse:LiveData<Event<Resource<CryptoResponse>>> = _cryptoGetResponse


    fun getCryptolist()= viewModelScope.launch {
        getCrypto()
    }

    private suspend fun getCrypto() {
        _cryptoGetResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getCryptos()
                _cryptoGetResponse.postValue(handleCryptoResponse(response))
            } else {
                _cryptoGetResponse.postValue(Event(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection))))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _cryptoGetResponse.postValue(
                        Event(
                            Resource.Error(
                                getApplication<MyApplication>().getString(
                                    R.string.network_failure
                                )
                            ))
                    )
                }
                else -> {
                    _cryptoGetResponse.postValue(
                        Event(
                            Resource.Error(
                                getApplication<MyApplication>().getString(
                                    R.string.conversion_error
                                )
                            ))
                    )
                }
            }
        }
    }

    private fun handleCryptoResponse(response: Response<CryptoResponse>): Event<Resource<CryptoResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }

}