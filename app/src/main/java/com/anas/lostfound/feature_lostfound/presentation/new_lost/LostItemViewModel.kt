package com.anas.lostfound.feature_lostfound.presentation.new_lost

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.anas.lostfound.feature_lostfound.data.di.IoDispatcher
import androidx.lifecycle.viewModelScope
import com.anas.lostfound.core.util.NewUpdateStrings
import com.anas.lostfound.feature_lostfound.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LostItemViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(LostItemState())
    val state: StateFlow<LostItemState> = _state

    private var errorHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }

    //
    sealed class UiEvent {
        // all events that gonna happen when we need to screen to display something and pass data back to the screen
        data class showSnackbar(val message: String) : UiEvent()
        object SaveItem : UiEvent()
        object Back : UiEvent()
    }


    var currentitemId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        savedStateHandle.get<Int>("itemId")?.let { id ->
            if (id != -1) {
                viewModelScope.launch(dispatcher + errorHandler) {
                    useCases.getItemById(id)?.also { item ->
                        currentitemId = id
                        _state.value = _state.value.copy(
                            item = item,
                            isLoading = false,

                            )
                    }
                }
            } // if id = -1
            else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    item = _state.value.item
                )
            }

        }
    }

    // we will handel anything that happens from this event
    fun onEvent(event: LostItemEvent) {
        when (event) {
            LostItemEvent.Back -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    _eventFlow.emit(UiEvent.Back)
                }
            }

            LostItemEvent.Delete -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    if (currentitemId != null) {
                        useCases.deleteItem((_state.value.item))
                    }
                    _eventFlow.emit(UiEvent.Back)
                }
            }

            is LostItemEvent.EnteredDescription -> {
                _state.value = _state.value.copy(
                    item = _state.value.item.copy(
                        description = event.value
                    )
                )
            }

            is LostItemEvent.EnteredTitle -> {
                _state.value = _state.value.copy(
                    item = _state.value.item.copy(
                        title = event.value
                    )
                )
            }

            is LostItemEvent.EnteredContact -> {
                _state.value = _state.value.copy(
                    item = _state.value.item.copy(
                        contact = event.value
                    )
                )
            }

            is LostItemEvent.EnteredEmail -> {
                _state.value = _state.value.copy(
                    item = _state.value.item.copy(
                        email = event.value
                    )
                )
            }

            is LostItemEvent.EnteredLocation -> {
                _state.value = _state.value.copy(
                    item = _state.value.item.copy(
                        location = event.value
                    )
                )
            }

            is LostItemEvent.SelectedImage -> {
                _state.value = _state.value.copy(
                    item = _state.value.item.copy(
                        imagePath = event.value
                    )
                )
            }

            is LostItemEvent.SelectedCategory -> {
                _state.value = _state.value.copy(
                    item = _state.value.item.copy(
                        category = event.value
                    )
                )
            }

            is LostItemEvent.UpdateCoordinates -> {
                _state.value = _state.value.copy(
                    item = _state.value.item.copy(
                        latitude = event.latitude,
                        longitude = event.longitude,
                        location = event.address
                    )
                )
            }

            LostItemEvent.SaveItem -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    try {
                        _state.value = _state.value.copy(isLoading = true)
                        if (currentitemId != null) {
                            useCases.updateItem(
                                _state.value.item.copy(
                                    timestamp = System.currentTimeMillis()
                                )
                            )
                        } else {
                            useCases.addItem(
                                _state.value.item.copy(
                                    timestamp = System.currentTimeMillis(),
                                    id = null,
                                )
                            )

                        }
                        _state.value = _state.value.copy(isLoading = false)
                        _eventFlow.emit(UiEvent.SaveItem) // I emit it into the screen then
                        // in the screen we handle it and we go back to the list

                    } catch (e: Exception) {
                        _state.value = _state.value.copy(
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UiEvent.showSnackbar(
                                message = e.message ?: NewUpdateStrings.SAVE_ERROR
                            )
                        )
                    }
                }
            }


        }

    }


}