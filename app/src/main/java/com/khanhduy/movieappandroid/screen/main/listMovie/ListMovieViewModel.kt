package com.khanhduy.movieappandroid.screen.main.listMovie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khanhduy.movieappandroid.data.api.ApiService
import com.khanhduy.movieappandroid.models.ListMovieModel
import com.khanhduy.movieappandroid.models.NewMovieModel
import com.khanhduy.movieappandroid.repository.ApiRepository
import com.khanhduy.movieappandroid.screen.main.newMovie.NewMovieEvent
import com.khanhduy.movieappandroid.screen.main.newMovie.NewMovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMovieViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel(){
    private val _uiState = MutableStateFlow<ListMovieState>(ListMovieState.Innit)
    val uiState : StateFlow<ListMovieState> = _uiState.asStateFlow()

    fun onEvent(event: ListMovieEvent){
        viewModelScope.launch {
            when(event){
                ListMovieEvent.InitCartoonMovie -> getCartoonMovie(1)
                ListMovieEvent.InitSeriesMovie -> getSeriesMovie(1)
                ListMovieEvent.InitSingleMovie -> getSingleMovie(1)
                is ListMovieEvent.GetSeriesMovie -> getSeriesMovie(event.page)
                is ListMovieEvent.GetCartoonMovie -> getCartoonMovie(event.page)
                is ListMovieEvent.GetSingleMovie -> getSingleMovie(event.page)
            }
        }
    }

    private suspend fun getSeriesMovie(page : Int){
        _uiState.value = ListMovieState.Loading
        val response = apiRepository.getSeriwsMovie(page)
        if (response.data != null){
            _uiState.value = ListMovieState.Success(response.data)
        }else{
            _uiState.value = ListMovieState.Error(response.message!!)
        }
    }

    private suspend fun getSingleMovie(page : Int){
        _uiState.value = ListMovieState.Loading
        val response = apiRepository.getSingleMovie(page)
        if (response.data != null){
            _uiState.value = ListMovieState.Success(response.data)
        }else{
            _uiState.value = ListMovieState.Error(response.message!!)
        }
    }

    private suspend fun getCartoonMovie(page : Int){
        _uiState.value = ListMovieState.Loading
        val response = apiRepository.getCartoonMovie(page)
        if (response.data != null){
            _uiState.value = ListMovieState.Success(response.data)
        }else{
            _uiState.value = ListMovieState.Error(response.message!!)
        }
    }
}

sealed class ListMovieState(){
    object Innit : ListMovieState();
    object Loading : ListMovieState();
    data class Success(val listMovieModel: ListMovieModel) : ListMovieState();
    data class Error (val message: String): ListMovieState()
}

sealed class ListMovieEvent(){
    object InitSeriesMovie : ListMovieEvent()
    object InitSingleMovie : ListMovieEvent()
    object InitCartoonMovie : ListMovieEvent()
    data class GetSeriesMovie(val page : Int) : ListMovieEvent()
    data class GetSingleMovie(val page : Int) : ListMovieEvent()
    data class GetCartoonMovie(val page : Int) : ListMovieEvent()
}