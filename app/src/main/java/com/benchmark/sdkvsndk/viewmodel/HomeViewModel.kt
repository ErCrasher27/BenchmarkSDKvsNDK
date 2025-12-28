package com.benchmark.sdkvsndk.viewmodel

import androidx.lifecycle.ViewModel
import com.benchmark.sdkvsndk.R
import com.benchmark.sdkvsndk.model.CardData

class HomeViewModel : ViewModel() {

    val cards = listOf(
        // TEACHING-CARD-EXTENSION-POINT
        CardData(
            titleRes = R.string.teaching_when_not_use_ndk_title,
            iconRes = R.drawable.traffic_jam_24px,
            contentRes = R.string.teaching_when_not_use_ndk_content,
        ),
        CardData(
            titleRes = R.string.teaching_when_use_ndk_title,
            iconRes = R.drawable.traffic_jam_24px,
            contentRes = R.string.teaching_when_use_ndk_content,
        ),
    )
}