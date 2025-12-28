package com.benchmark.sdkvsndk.viewmodel

import androidx.lifecycle.ViewModel
import com.benchmark.sdkvsndk.model.BenchmarkResults

class StatisticsViewModel : ViewModel() {

    private var _results: BenchmarkResults? = null
    val results: BenchmarkResults? get() = _results

    fun setResults(benchmarkResults: BenchmarkResults) {
        _results = benchmarkResults
    }

    fun getSpeedupLabel(): String {
        val speedup = results?.speedup ?: return "N/A"
        return if (speedup > 1.0) {
            "NDK ${String.format("%.2f", speedup)}x faster"
        } else {
            "SDK ${String.format("%.2f", 1.0 / speedup)}x faster"
        }
    }

    fun getWinnerLabel(): String {
        val results = results ?: return "N/A"
        return if (results.ndkWinPercentage > results.sdkWinPercentage) {
            "NDK wins ${results.ndkWinPercentage}% of the time"
        } else {
            "SDK wins ${results.sdkWinPercentage}% of the time"
        }
    }
}
