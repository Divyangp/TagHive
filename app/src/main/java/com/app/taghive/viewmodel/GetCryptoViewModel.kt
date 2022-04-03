package com.app.taghive.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.taghive.R
import com.app.taghive.util.Resource
import com.app.taghive.repository.AppRepository
import com.app.taghive.app.MyApplication
import com.app.taghive.model.GetCryptoResponse
import com.app.taghive.util.Event
import com.app.taghive.util.Utils
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class GetCryptoViewModel(app: Application, private val appRepository: AppRepository) : AndroidViewModel(app) {

    private val _cryptoGetDetailResponse = MutableLiveData<Event<Resource<GetCryptoResponse>>>()
    val cryptoGetDetailResponse:LiveData<Event<Resource<GetCryptoResponse>>> = _cryptoGetDetailResponse


    fun getCryptoDetaillist(symbol:String)= viewModelScope.launch {
        getCryptoDetails(symbol)
    }

    private suspend fun getCryptoDetails(symbol: String) {
        _cryptoGetDetailResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getCryptoDetails(symbol)
                _cryptoGetDetailResponse.postValue(handlegetCryptoResponse(response))
            } else {
                _cryptoGetDetailResponse.postValue(Event(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection))))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _cryptoGetDetailResponse.postValue(
                        Event(
                            Resource.Error(
                                getApplication<MyApplication>().getString(
                                    R.string.network_failure
                                )
                            ))
                    )
                }
                else -> {
                    _cryptoGetDetailResponse.postValue(
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

    private fun handlegetCryptoResponse(response: Response<GetCryptoResponse>): Event<Resource<GetCryptoResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}