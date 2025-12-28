package com.benchmark.sdkvsndk.model

import android.os.Parcelable
import com.benchmark.sdkvsndk.benchmark.BenchmarkAlgorithm
import kotlinx.parcelize.Parcelize

@Parcelize
data class BenchmarkResults(
    val sdkTimes: List<Long>,
    val ndkTimes: List<Long>,
    val algorithm: BenchmarkAlgorithm,
    val inputSize: Int,
    val iterations: Int
) : Parcelable {

    val sdkAverage: Double get() = sdkTimes.average()
    val ndkAverage: Double get() = ndkTimes.average()
    val speedup: Double get() = sdkAverage / ndkAverage

    val sdkWinPercentage: Int
        get() = ((sdkTimes.zip(ndkTimes)
            .count { it.first < it.second } / iterations.toDouble()) * 100).toInt()

    val ndkWinPercentage: Int get() = 100 - sdkWinPercentage
}