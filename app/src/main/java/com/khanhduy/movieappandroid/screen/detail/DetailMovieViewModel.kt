package com.khanhduy.movieappandroid.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khanhduy.movieappandroid.models.DetailMovieModel
import com.khanhduy.movieappandroid.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel(){
    private val _detailState = MutableStateFlow<DetailMovieState>(DetailMovieState.Init)
    val detailState : StateFlow<DetailMovieState> = _detailState.asStateFlow()

    fun onEvent(event : DetailMovieEvent){
        viewModelScope.launch {
            when(event){
                is DetailMovieEvent.Init -> getDetailMovie(event.slug)
            }
        }

    }

    private suspend fun getDetailMovie(slug: String){
        _detailState.value = DetailMovieState.Loading
        val response = apiRepository.getDetailMovie(slug)
        if(response.data != null){
            _detailState.value = DetailMovieState.Success(response.data)
        }else{
            _detailState.value = DetailMovieState.Error(response.message!!)
        }
    }
}

sealed class DetailMovieState {
    object Init : DetailMovieState()
    object Loading : DetailMovieState()
    data class Success(val detailMovieModel: DetailMovieModel) : DetailMovieState()
    data class Error(val message: String) : DetailMovieState()
}

sealed class DetailMovieEvent {
    data class Init(val slug : String) : DetailMovieEvent()
}