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
            "NDK ${String.format("%.2f", speedup)}x più veloce"
        } else {
            "SDK ${String.format("%.2f", 1.0 / speedup)}x più veloce"
        }
    }

    fun getWinnerLabel(): String {
        val results = results ?: return "N/A"
        return if (results.ndkWinPercentage > results.sdkWinPercentage) {
            "NDK vince ${results.ndkWinPercentage}% delle volte"
        } else {
            "SDK vince ${results.sdkWinPercentage}% delle volte"
        }
    }
}
