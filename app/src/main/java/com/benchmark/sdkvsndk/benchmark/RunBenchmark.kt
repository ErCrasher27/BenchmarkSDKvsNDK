package com.benchmark.sdkvsndk.benchmark

import com.benchmark.sdkvsndk.viewmodel.BenchmarkViewModel.BenchmarkResult
import com.benchmark.sdkvsndk.viewmodel.BenchmarkViewModel.InputSize
import kotlin.random.Random
import kotlin.system.measureNanoTime

/**
 * Runs the merge sort benchmark in a pure Kotlin (SDK) and native (NDK) version.
 *
 * The method:
 *  1. Generates a random IntArray of the requested size.
 *  2. Copies it and runs the Kotlin merge sort implementation, measuring time in ns.
 *  3. Copies it again and passes it to the native C++ implementation, which
 *     returns the elapsed time in ms.
 *  4. Wraps both timings into a BenchmarkResult.
 */
fun runMergeSortBenchmark(inputSize: InputSize): BenchmarkResult {
    // Generate random input data.
    val data = IntArray(inputSize.n) { Random.nextInt() }

    // Measure Kotlin (SDK) implementation.
    val sdkData = data.copyOf()
    val sdkTimeNs = measureNanoTime {
        kotlinMergeSort(sdkData, 0, sdkData.lastIndex)
    }
    val sdkTimeMs = sdkTimeNs / 1_000_000

    // Measure C++ (NDK) implementation.
    val ndkData = data.copyOf()
    val ndkTimeMs = NativeBenchmark.mergeSort(ndkData)

    return BenchmarkResult(
        algorithm = BenchmarkAlgorithm.MERGE_SORT,
        inputSize = inputSize,
        sdkTimeMs = sdkTimeMs,
        ndkTimeMs = ndkTimeMs
    )
}

/**
 * Runs the binary search benchmark in Kotlin and C++.
 *
 * The method:
 *  1. Creates a sorted IntArray [0, 1, 2, ..., n-1].
 *  2. Picks a random target from the array.
 *  3. Times the Kotlin binary search implementation.
 *  4. Calls the native C++ binary search and uses its reported time.
 */
fun runBinarySearchBenchmark(inputSize: InputSize): BenchmarkResult {
    // Sorted data [0, 1, 2, ...].
    val sortedData = IntArray(inputSize.n) { it }
    val target = sortedData.random()

    val sdkTimeNs = measureNanoTime {
        kotlinBinarySearch(sortedData, target)
    }
    val sdkTimeMs = sdkTimeNs / 1_000_000

    val ndkTimeMs = NativeBenchmark.binarySearch(sortedData, target)

    return BenchmarkResult(
        algorithm = BenchmarkAlgorithm.BINARY_SEARCH,
        inputSize = inputSize,
        sdkTimeMs = sdkTimeMs,
        ndkTimeMs = ndkTimeMs
    )
}
