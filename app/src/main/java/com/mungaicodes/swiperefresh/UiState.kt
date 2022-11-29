package com.mungaicodes.swiperefresh

import java.time.Instant

data class UiState(
    val isRefreshing: Boolean = false,
    val currentTime: Instant = Instant.now(),
    val items: List<RowItem> = emptyList(),
    val isLoading: Boolean = true
)
