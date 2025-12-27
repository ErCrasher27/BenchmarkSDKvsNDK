package com.benchmark.sdkvsndk.viewmodel

import androidx.lifecycle.ViewModel
import com.benchmark.sdkvsndk.R
import com.benchmark.sdkvsndk.model.CardData

class HomeViewModel : ViewModel() {

    val cards = listOf(
        CardData(
            titleRes = R.string.card_when_not_use_ndk_title,
            iconRes = R.drawable.traffic_jam_24px,
            contentRes = R.string.card_when_not_use_ndk_content,
        ),
        CardData(
            titleRes = R.string.card_when_use_ndk_title,
            iconRes = R.drawable.traffic_jam_24px,
            contentRes = R.string.card_when_use_ndk_content,
        ),
    )
}