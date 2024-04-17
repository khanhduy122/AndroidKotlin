package com.khanhduy.movieappandroid.screen.main.listMovie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khanhduy.movieappandroid.data.api.ApiService
import com.khanhduy.movieappandroid.models.ListMovieModel
import com.khanhduy.movieappandroid.models.NewMovieModel
import com.khanhduy.movieappandroid.screen.main.newMovie.NewMovieEvent
import com.khanhduy.movieappandroid.screen.main.newMovie.NewMovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMovieViewModel @Inject constructor(private val apiService: ApiService) : ViewModel(){
    private val _uiState = MutableStateFlow<ListMovieState>(ListMovieState.Innit)
    val uiState : StateFlow<ListMovieState> = _uiState.asStateFlow()

    fun onEvent(event: NewMovieEvent){
        viewModelScope.launch {
            when(event){
                is NewMovieEvent.Init -> getSeriesMovie(1)
                is NewMovieEvent.GetNewMovie -> getSeriesMovie(event.page)
            }
        }
    }

    suspend fun getSeriesMovie(page : Int){
        _uiState.value = ListMovieState.Innit
        try {
            val response = apiService.getSeriesMovie(page);
            if (response.isSuccessful){
                Log.e("bbb", "getSeriesMovie: ${response.body()!!}", )
                _uiState.value = ListMovieState.Success(listMovieModel = response.body()!!)
            }else{
                _uiState.value = ListMovieState.Error
            }
        }catch (error : Exception){
            _uiState.value = ListMovieState.Error
        }
    }

    suspend fun getSingleMovie(page : Int){
        _uiState.value = ListMovieState.Innit
        try {
            val response = apiService.getSingleMovie(page);
            if (response.isSuccessful){
                Log.e("bbb", "getSeriesMovie: ${response.body()!!}", )
                _uiState.value = ListMovieState.Success(listMovieModel = response.body()!!)
            }else{
                _uiState.value = ListMovieState.Error
            }
        }catch (error : Exception){
            _uiState.value = ListMovieState.Error
        }
    }

    suspend fun getCartoonMovie(page : Int){
        _uiState.value = ListMovieState.Innit
        try {
            val response = apiService.getCartoonMovie(page);
            if (response.isSuccessful){
                Log.e("bbb", "getSeriesMovie: ${response.body()!!}", )
                _uiState.value = ListMovieState.Success(listMovieModel = response.body()!!)
            }else{
                _uiState.value = ListMovieState.Error
            }
        }catch (error : Exception){
            _uiState.value = ListMovieState.Error
        }
    }
}

sealed class ListMovieState(){
    object Innit : ListMovieState();
    data class Success(val listMovieModel: ListMovieModel) : ListMovieState();
    object Error : ListMovieState()
}

sealed class ListMovieEvent(){
    object Init : ListMovieEvent()
    data class GetNewMovie(val page : Int) : ListMovieEvent()
}