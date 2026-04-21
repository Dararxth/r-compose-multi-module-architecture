package com.rxth.multimodule.core.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Event, Effect> : ViewModel() {

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState())
    val uiState: MutableStateFlow<State> = _uiState

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel(Channel.BUFFERED)
    val effect: Channel<Effect> = _effect


    abstract fun initialState(): State


    protected fun updateState(reducer: State.() -> State) {
        _uiState.value = _uiState.value.reducer()
    }

    fun sendEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    protected fun sendEffect(effect: Effect) {
        _effect.trySend(effect)
    }
}
