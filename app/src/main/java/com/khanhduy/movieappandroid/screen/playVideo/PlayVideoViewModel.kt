package com.khanhduy.movieappandroid.screen.playVideo

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//@HiltViewModel
//class PlayVideoViewModel : ViewModel(){
//    private val _playVideoState = MutableStateFlow<PlayVideoState>(PlayVideoState.Init)
//    val playVideoState : StateFlow<PlayVideoState> = _playVideoState.asStateFlow()
//}
//
//sealed class PlayVideoState(){
//    object Init : PlayVideoState()
//    object Loading : PlayVideoState()
//    object Success : PlayVideoState()
//    object Error : PlayVideoState()
//}