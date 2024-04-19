package com.khanhduy.movieappandroid.screen.main.newMovie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khanhduy.movieappandroid.data.api.ApiService
import com.khanhduy.movieappandroid.models.NewMovieModel
import com.khanhduy.movieappandroid.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewMovieViewModel @Inject constructor (private val apiRepository: ApiRepository) : ViewModel(){

    private val _uiState = MutableStateFlow<NewMovieState>(NewMovieState.Innit)
    val uiState : StateFlow<NewMovieState> = _uiState.asStateFlow()

    fun onEvent(event: NewMovieEvent){
        viewModelScope.launch {
            when(event){
                is NewMovieEvent.Init -> getNewMovie(1)
                is NewMovieEvent.GetNewMovie -> getNewMovie(event.page)
            }
        }
    }

    private suspend fun getNewMovie(page : Int){
        _uiState.value = NewMovieState.Loading
        val response = apiRepository.getNewMovie(page)
        if(response.data != null){
            _uiState.value = NewMovieState.Success(response.data)
        }else{
            _uiState.value = NewMovieState.Error(response.message!!)
        }
    }
}

sealed class NewMovieState(){
    object Innit : NewMovieState();
    object Loading : NewMovieState();
    data class Success(val newMovieModel: NewMovieModel) : NewMovieState();
    data class Error(val message: String) : NewMovieState()
}

sealed class NewMovieEvent(){
    object Init : NewMovieEvent()
    data class GetNewMovie(val page : Int) : NewMovieEvent()
}