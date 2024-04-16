package com.khanhduy.movieappandroid.screen.main.newMovie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khanhduy.movieappandroid.data.api.ApiService
import com.khanhduy.movieappandroid.models.NewMovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewMovieViewModel @Inject constructor (private val apiService: ApiService) : ViewModel(){

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

    suspend fun getNewMovie(page : Int){
        _uiState.value = NewMovieState.Innit
        try {
            val response = apiService.getNewMovie(page);
            if (response.isSuccessful){
                _uiState.value = NewMovieState.Success(newMovieModel = response.body()!!)
            }else{
                _uiState.value = NewMovieState.Error
            }
        }catch (error : Exception){
            _uiState.value = NewMovieState.Error
        }

    }
}

sealed class NewMovieState(){
    object Innit : NewMovieState();
    data class Success(val newMovieModel: NewMovieModel) : NewMovieState();
    object Error : NewMovieState()
}

sealed class NewMovieEvent(){
    object Init : NewMovieEvent()
    data class GetNewMovie(val page : Int) : NewMovieEvent()
}