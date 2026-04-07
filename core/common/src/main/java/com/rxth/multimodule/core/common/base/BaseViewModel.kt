package com.rxth.multimodule.core.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.reduce

abstract class BaseViewModel<State, Event, Effect> : ViewModel() {

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState())
    val uiState: MutableStateFlow<State> = _uiState

    private val _intent: MutableStateFlow<Event?> = MutableStateFlow(null)
    val intent: MutableStateFlow<Event?> = _intent

    private val _effect: Channel<Effect> = Channel()
    val effect: Channel<Effect> = _effect


    abstract fun initialState(): State


    protected fun updateState(reducer: State.() -> State) {
        _uiState.value = _uiState.value.reducer()
    }

    protected fun sendEvent(event: Event) {
        _intent.value = event
    }

    protected fun sendEffect(effect: Effect) {
        _effect.trySend(effect)
    }
}