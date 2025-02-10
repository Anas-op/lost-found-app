package com.anas.lostfound.feature_lostfound.presentation.lost_found_items.sections.found

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anas.lostfound.core.util.ListStrings
import com.anas.lostfound.feature_lostfound.data.di.IoDispatcher
import com.anas.lostfound.feature_lostfound.domain.model.Item
import com.anas.lostfound.feature_lostfound.domain.use_case.UseCaseResult
import com.anas.lostfound.feature_lostfound.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FoundItemListViewModel @Inject constructor(
    private val useCases: UseCases,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {



    // we want to ui to be able only to view the state so we use a private
    // we giving ui a read only access to private state
    private val _state = mutableStateOf(FoundListState())
    val state: State<FoundListState> = _state
    private var getFoundItemsJob: Job? = null
    private var undoFoundItem: Item? = null
    private var errorHandler = CoroutineExceptionHandler {_ ,e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            // when we have and error we display this message
            error = e.message,
            isLoading = false
        )
    }

    fun onEvent(event: FoundListEvent){

        when(event){
            is FoundListEvent.Delete -> {

                viewModelScope.launch(dispatcher + errorHandler){
                    useCases.deleteItem(event.item)
                    // update the  list in the screen

                    getFoundItems()
                    // assign it to the undo item so we can undo the delete
                    undoFoundItem = event.item
                }
            }

            FoundListEvent.UndoDelete -> {
                viewModelScope.launch(dispatcher + errorHandler){
                    useCases.addItem(undoFoundItem?: return@launch)
                    undoFoundItem = null
                    getFoundItems()
                }
            }
        }
    }



    fun getFoundItems() {
        getFoundItemsJob?.cancel()

        getFoundItemsJob = viewModelScope.launch(dispatcher + errorHandler){
            val result = useCases.getItems()
            when (result) {
                is UseCaseResult.Success -> {
                    result.items.collect{
                        _state.value = _state.value.copy(
                            foundItems = it,
                            isLoading = false
                        )
                    }
                }

                is UseCaseResult.Error -> {
                    _state.value = _state.value.copy(
                        error = ListStrings.CANT_GET_ITEMS,
                        isLoading = false
                    )
                }

            }
        }


    }
}