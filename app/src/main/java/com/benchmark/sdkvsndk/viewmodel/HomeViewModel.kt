package com.benchmark.sdkvsndk.viewmodel

import androidx.lifecycle.ViewModel
import com.benchmark.sdkvsndk.R

class HomeViewModel : ViewModel() {

    data class CardData(
        val titleRes: Int, val iconRes: Int, val contentRes: Int
    )

    val cards = listOf(
        CardData(
            titleRes = R.string.no_ndk_usages_title,
            iconRes = R.drawable.traffic_jam_24px,
            contentRes = R.string.no_ndk_usages_content,
        ),
    )
}