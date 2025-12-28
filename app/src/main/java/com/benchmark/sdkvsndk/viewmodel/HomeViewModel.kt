package com.benchmark.sdkvsndk.viewmodel

import androidx.lifecycle.ViewModel
import com.benchmark.sdkvsndk.R
import com.benchmark.sdkvsndk.model.CardData

class HomeViewModel : ViewModel() {

    val cards = listOf(
        // TEACHING-CARD-EXTENSION-POINT
        CardData(
            titleRes = R.string.teaching_shocking_truth_title,
            iconRes = R.drawable.bolt,
            contentRes = R.string.teaching_shocking_truth_content,
        ), CardData(
            titleRes = R.string.teaching_binary_search_title,
            iconRes = R.drawable.timer_off,
            contentRes = R.string.teaching_binary_search_content,
        ), CardData(
            titleRes = R.string.teaching_when_native_fails_title,
            iconRes = R.drawable.broken_image,
            contentRes = R.string.teaching_when_native_fails_content,
        ), CardData(
            titleRes = R.string.teaching_what_wins_title,
            iconRes = R.drawable.psychology,
            contentRes = R.string.teaching_what_wins_content,
        )
    )
}