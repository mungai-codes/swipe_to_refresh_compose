package com.mungaicodes.swiperefresh

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(items = List(size = 20) { RowItem() }) }
            delay(2000)
            _uiState.update { it.copy(items = generatedItems(), isLoading = false) }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isRefreshing = true)
            }
            //simulating an api call
            delay(2000)
            _uiState.update {
                it.copy(
                    currentTime = Instant.now(),
                    items = generatedItems(),
                    isRefreshing = false
                )
            }
        }
    }

    private fun generatedItems(): List<RowItem> {
        val list = mutableListOf<RowItem>()

        for (i in 1 until 20) {
            list.add(
                RowItem(
                    rowImage = randomImage(),
                    number = Random.nextInt(1, 1000)
                )
            )
        }

        return list
    }

    private fun randomImage(
        seed: Int = (0..100000).random(),
        width: Int = 300,
        height: Int = width,
    ): String {
        return "https://picsum.photos/seed/$seed/$width/$height"
    }
}
