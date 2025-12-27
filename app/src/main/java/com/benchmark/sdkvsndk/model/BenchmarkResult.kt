package com.benchmark.sdkvsndk.model

import com.benchmark.sdkvsndk.benchmark.BenchmarkAlgorithm

data class BenchmarkResult(
    val algorithm: BenchmarkAlgorithm,
    val inputSize: InputSize,
    val sdkTimeMs: Long,
    val ndkTimeMs: Long
) {
    val speedup: Double
        get() = if (ndkTimeMs == 0L) 0.0 else sdkTimeMs.toDouble() / ndkTimeMs.toDouble()
}